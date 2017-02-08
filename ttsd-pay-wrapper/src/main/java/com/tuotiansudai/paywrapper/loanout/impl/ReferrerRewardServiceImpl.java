package com.tuotiansudai.paywrapper.loanout.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.tuotiansudai.client.MQWrapperClient;
import com.tuotiansudai.enums.*;
import com.tuotiansudai.exception.AmountTransferException;
import com.tuotiansudai.message.EventMessage;
import com.tuotiansudai.message.PushMessage;
import com.tuotiansudai.message.TransferReferrerRewardCallbackMessage;
import com.tuotiansudai.mq.client.model.MessageQueue;
import com.tuotiansudai.paywrapper.client.PayAsyncClient;
import com.tuotiansudai.paywrapper.client.PaySyncClient;
import com.tuotiansudai.paywrapper.loanout.ReferrerRewardService;
import com.tuotiansudai.paywrapper.repository.mapper.ProjectTransferNotifyMapper;
import com.tuotiansudai.paywrapper.repository.mapper.TransferMapper;
import com.tuotiansudai.paywrapper.repository.model.async.callback.BaseCallbackRequestModel;
import com.tuotiansudai.paywrapper.repository.model.async.callback.ProjectTransferNotifyRequestModel;
import com.tuotiansudai.paywrapper.repository.model.sync.request.TransferRequestModel;
import com.tuotiansudai.paywrapper.repository.model.sync.response.TransferResponseModel;
import com.tuotiansudai.paywrapper.service.SystemBillService;
import com.tuotiansudai.repository.mapper.*;
import com.tuotiansudai.repository.model.*;
import com.tuotiansudai.util.AmountConverter;
import com.tuotiansudai.util.AmountTransfer;
import com.tuotiansudai.util.IdGenerator;
import com.tuotiansudai.util.InterestCalculator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@Service
public class ReferrerRewardServiceImpl implements ReferrerRewardService {

    static Logger logger = Logger.getLogger(ReferrerRewardServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PaySyncClient paySyncClient;

    @Autowired
    private AmountTransfer amountTransfer;

    @Autowired
    private SystemBillService systemBillService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private InvestMapper investMapper;

    @Autowired
    private ReferrerRelationMapper referrerRelationMapper;

    @Autowired
    private AgentLevelRateMapper agentLevelRateMapper;

    @Autowired
    private InvestReferrerRewardMapper investReferrerRewardMapper;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private MQWrapperClient mqWrapperClient;

    @Autowired
    private PayAsyncClient payAsyncClient;

    @Value("#{'${pay.user.reward}'.split('\\|')}")
    private List<Double> referrerUserRoleReward;

    @Value("#{'${pay.staff.reward}'.split('\\|')}")
    private List<Double> referrerStaffRoleReward;

    @Override
    public boolean rewardReferrer(long loanId) {
        boolean result = true;
        LoanModel loanModel = loanMapper.findById(loanId);
        List<InvestModel> successInvestList = investMapper.findSuccessInvestsByLoanId(loanId);

        int loanDuration = this.calculateLoanDuration(loanModel);

        for (InvestModel invest : successInvestList) {
            List<ReferrerRelationModel> referrerRelationList = referrerRelationMapper.findByLoginName(invest.getLoginName());
            for (ReferrerRelationModel referrerRelationModel : referrerRelationList) {
                String referrerLoginName = referrerRelationModel.getReferrerLoginName();
                if (investReferrerRewardMapper.findByInvestIdAndReferrer(invest.getId(), referrerLoginName) == null) {
                    try {
                        Role role = this.getReferrerPriorityRole(referrerLoginName);
                        if (role != null) {
                            long reward = this.calculateReferrerReward(invest.getAmount(), loanDuration, referrerRelationModel.getLevel(), role, referrerLoginName);
                            InvestReferrerRewardModel model = new InvestReferrerRewardModel(idGenerator.generate(), invest.getId(), reward, referrerLoginName, role);
                            investReferrerRewardMapper.create(model);
                            boolean status = this.transferReferrerReward(model);
                            if (status) {
                                this.sendMessage(invest.getLoginName(), referrerLoginName, reward, model.getId());
                            }
                        }
                    } catch (Exception e) {
                        logger.error(MessageFormat.format("Referrer reward is failed (investId={0} referrerLoginName={1})",
                                String.valueOf(invest.getId()),
                                referrerLoginName));
                        result = false;
                    }
                }
            }
        }
        return result;
    }

    private boolean transferReferrerReward(InvestReferrerRewardModel model) {
        String referrerLoginName = model.getReferrerLoginName();
        long orderId = model.getId();
        long amount = model.getAmount();

        AccountModel accountModel = accountMapper.findByLoginName(referrerLoginName);
        if (accountModel == null) {
            model.setStatus(ReferrerRewardStatus.NO_ACCOUNT);
            investReferrerRewardMapper.update(model);
            logger.error(MessageFormat.format("referrer has no account, investId={0} referrerLoginName={1} referrerRole={2} amount={3}",
                    String.valueOf(model.getInvestId()),
                    model.getReferrerLoginName(),
                    model.getReferrerRole().name(),
                    String.valueOf(model.getAmount())));
            return false;
        }

        if (amount == 0) {
            model.setStatus(ReferrerRewardStatus.SUCCESS);
            try {
                recordTransferReferrerReward(model);
            } catch (Exception ignored) {
                logger.error(ignored.getLocalizedMessage(), ignored);
            }
        }

        if (amount > 0) {
            try {
                TransferRequestModel requestModel = TransferRequestModel.newTransferReferrerRewardRequest(String.valueOf(orderId), accountModel.getPayUserId(), String.valueOf(amount));
                TransferResponseModel responseModel = paySyncClient.send(TransferMapper.class, requestModel, TransferResponseModel.class);
                logger.info(MessageFormat.format("[标的放款] pay sync transfer referrer reward, result:{0}, investReferrerRewardId:{1}, loginName:{2}", responseModel.isSuccess(),
                        String.valueOf(orderId), accountModel.getLoginName()));
            } catch (Exception e) {
                logger.error(MessageFormat.format("[标的放款] referrer reward is failed, investId={0} referrerLoginName={1} referrerRole={2} amount={3}",
                        String.valueOf(model.getInvestId()),
                        model.getReferrerLoginName(),
                        model.getReferrerRole().name(),
                        String.valueOf(model.getAmount())), e);
                return false;
            }
        }
        return true;
    }

    @Override
    public String transferReferrerRewardNotify(Map<String, String> paramsMap, String queryString) {
        logger.info("[标的放款] transfer referrer reward  call back begin.");
        BaseCallbackRequestModel callbackRequest = this.payAsyncClient.parseCallbackRequest(
                paramsMap,
                queryString,
                ProjectTransferNotifyMapper.class,
                ProjectTransferNotifyRequestModel.class);
        if (callbackRequest == null || Strings.isNullOrEmpty(callbackRequest.getOrderId())) {
            logger.error(MessageFormat.format("[标的放款] TransferReferrerRewardCallback payback callback parse is failed (queryString = {0})", queryString));
            return null;
        }

        if (!callbackRequest.isSuccess()) {
            return callbackRequest.getResponseData();
        }

        long investReferrerRewardId = Long.parseLong(callbackRequest.getOrderId());
        InvestReferrerRewardModel investReferrerRewardModel = investReferrerRewardMapper.findById(investReferrerRewardId);
        if (investReferrerRewardModel == null) {
            logger.error(MessageFormat.format("[标的放款] TransferReferrerRewardCallback payback callback failed, order id({0}) is not exist", callbackRequest.getOrderId()));
            return callbackRequest.getResponseData();
        }

        InvestModel investModel = investMapper.findById(investReferrerRewardModel.getInvestId());
        TransferReferrerRewardCallbackMessage transferReferrerRewardCallbackMessage = new TransferReferrerRewardCallbackMessage(investModel.getLoanId(),
                investReferrerRewardModel.getInvestId(), investModel.getLoginName(), investReferrerRewardModel.getReferrerLoginName(),
                investReferrerRewardId);

        logger.info(MessageFormat.format("[标的放款] send mq TransferReferrerRewardCallback, loanId:{0}, investId:{1}, loginName:{2}, referrer:{3}, queryString:{4}",
                transferReferrerRewardCallbackMessage.getLoanId(), transferReferrerRewardCallbackMessage.getInvestId(),
                transferReferrerRewardCallbackMessage.getLoginName(), transferReferrerRewardCallbackMessage.getReferrer(),
                queryString));
        mqWrapperClient.sendMessage(MessageQueue.TransferReferrerRewardCallback, transferReferrerRewardCallbackMessage);

        return callbackRequest.getResponseData();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transferReferrerCallBack(long investReferrerRewardId) throws AmountTransferException {
        logger.info("[标的放款] transfer referrer callBack start.");
        InvestReferrerRewardModel investReferrerRewardModel = investReferrerRewardMapper.findById(investReferrerRewardId);
        if (investReferrerRewardModel.getStatus().equals(ReferrerRewardStatus.SUCCESS)) {
            return true;
        }

        investReferrerRewardModel.setStatus(ReferrerRewardStatus.SUCCESS);
        return recordTransferReferrerReward(investReferrerRewardModel);
    }

    private boolean recordTransferReferrerReward(InvestReferrerRewardModel investReferrerRewardModel) throws AmountTransferException {
        String referrerLoginName = investReferrerRewardModel.getReferrerLoginName();
        long amount = investReferrerRewardModel.getAmount();
        long orderId = investReferrerRewardModel.getId();
        try {
            if (investReferrerRewardModel.getStatus() == ReferrerRewardStatus.SUCCESS) {
                investReferrerRewardMapper.update(investReferrerRewardModel);
                logger.info(MessageFormat.format("[标的放款]:发送推荐人奖励,推荐人:{0},投资ID:{1},推荐人奖励:{2}", referrerLoginName, orderId, amount));
                amountTransfer.transferInBalance(referrerLoginName, orderId, amount, UserBillBusinessType.REFERRER_REWARD, null, null);
                InvestModel investModel = investMapper.findById(investReferrerRewardModel.getInvestId());
                String detail = MessageFormat.format(SystemBillDetailTemplate.REFERRER_REWARD_DETAIL_TEMPLATE.getTemplate(), referrerLoginName, investModel.getLoginName(), String.valueOf(investReferrerRewardModel.getInvestId()));
                logger.info(MessageFormat.format("[标的放款]:记录系统奖励,投资ID:{0},推荐人奖励:{1},奖励类型:{2}", orderId, amount, SystemBillBusinessType.REFERRER_REWARD));
                systemBillService.transferOut(orderId, amount, SystemBillBusinessType.REFERRER_REWARD, detail);
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("referrer reward transfer in balance failed (investId = {0})", String.valueOf(investReferrerRewardModel.getInvestId())));
            throw e;
        }
        return true;
    }

    private long calculateReferrerReward(long amount, int loanDuration, int level, Role role, String referrerLoginName) {
        BigDecimal amountBigDecimal = new BigDecimal(amount);

        double rewardRate = this.getRewardRate(level, Role.STAFF == role, referrerLoginName);

        return amountBigDecimal
                .multiply(new BigDecimal(rewardRate))
                .multiply(new BigDecimal(loanDuration))
                .divide(new BigDecimal(InterestCalculator.DAYS_OF_YEAR), 0, BigDecimal.ROUND_DOWN)
                .longValue();
    }

    private int calculateLoanDuration(LoanModel loanModel) {
        if (loanModel.getType().getLoanPeriodUnit() == LoanPeriodUnit.DAY) {
            return loanModel.getPeriods();
        }

        int periods = loanModel.getPeriods();
        return periods * InterestCalculator.DAYS_OF_MONTH;
    }

    private Role getReferrerPriorityRole(String referrerLoginName) {
        List<UserRoleModel> userRoleModels = userRoleMapper.findByLoginName(referrerLoginName);

        if (CollectionUtils.isEmpty(userRoleModels)) {
            return null;
        }

        if (userRoleModels.stream().anyMatch(userRoleModel -> userRoleModel.getRole() == Role.STAFF)) {
            return Role.STAFF;
        }

        if (userRoleModels.stream().anyMatch(userRoleModel -> userRoleModel.getRole() == Role.INVESTOR)) {
            return Role.INVESTOR;
        }

        if (userRoleModels.stream().anyMatch(userRoleModel -> userRoleModel.getRole() == Role.USER)) {
            return Role.USER;
        }

        return null;
    }

    private double getRewardRate(int level, boolean isStaff, String referrerLoginName) {
        if (isStaff) {
            AgentLevelRateModel agentLevelRateModel = agentLevelRateMapper.findAgentLevelRateByLoginNameAndLevel(referrerLoginName, level);
            if (agentLevelRateModel != null) {
                double agentRewardRate = agentLevelRateModel.getRate();
                if (agentRewardRate > 0) {
                    return agentRewardRate;
                }
            }
            return level > this.referrerStaffRoleReward.size() ? 0 : this.referrerStaffRoleReward.get(level - 1);
        }

        return level > this.referrerUserRoleReward.size() ? 0 : this.referrerUserRoleReward.get(level - 1);
    }

    private void sendMessage(String loginName, String referrerLoginName, long reward, long businessId) {
        //Title:{0}元推荐奖励已存入您的账户，请查收！
        //Content:尊敬的用户，您推荐的好友{0}投资成功，您已获得{1}元现金奖励。
        String title = MessageFormat.format(MessageEventType.RECOMMEND_AWARD_SUCCESS.getTitleTemplate(), AmountConverter.convertCentToString(reward));
        String content = MessageFormat.format(MessageEventType.RECOMMEND_AWARD_SUCCESS.getContentTemplate(), userMapper.findByLoginName(loginName).getMobile(), AmountConverter.convertCentToString(reward));
        mqWrapperClient.sendMessage(MessageQueue.EventMessage, new EventMessage(MessageEventType.RECOMMEND_AWARD_SUCCESS,
                Lists.newArrayList(referrerLoginName), title, content, businessId));
        mqWrapperClient.sendMessage(MessageQueue.PushMessage, new PushMessage(Lists.newArrayList(referrerLoginName), PushSource.ALL, PushType.RECOMMEND_AWARD_SUCCESS, title));
    }
}
