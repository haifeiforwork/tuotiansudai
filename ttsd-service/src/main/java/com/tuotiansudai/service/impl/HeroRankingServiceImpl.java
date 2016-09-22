package com.tuotiansudai.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.tuotiansudai.client.RedisWrapperClient;
import com.tuotiansudai.dto.BasePaginationDataDto;
import com.tuotiansudai.dto.MysteriousPrizeDto;
import com.tuotiansudai.membership.repository.mapper.MembershipMapper;
import com.tuotiansudai.membership.repository.mapper.UserMembershipMapper;
import com.tuotiansudai.membership.repository.model.MembershipLevel;
import com.tuotiansudai.membership.repository.model.UserMembershipModel;
import com.tuotiansudai.membership.repository.model.UserMembershipType;
import com.tuotiansudai.repository.mapper.AccountMapper;
import com.tuotiansudai.repository.mapper.InvestMapper;
import com.tuotiansudai.repository.model.GivenMembership;
import com.tuotiansudai.repository.model.HeroRankingView;
import com.tuotiansudai.service.HeroRankingService;
import com.tuotiansudai.transfer.repository.mapper.TransferApplicationMapper;
import com.tuotiansudai.util.RandomUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HeroRankingServiceImpl implements HeroRankingService {

    static Logger logger = Logger.getLogger(HeroRankingServiceImpl.class);

    @Autowired
    private InvestMapper investMapper;

    @Autowired
    private TransferApplicationMapper transferApplicationMapper;

    @Autowired
    private RandomUtils randomUtils;

    @Autowired
    private RedisWrapperClient redisWrapperClient;

    private final static String MYSTERIOUSREDISKEY = "console:mysteriousPrize";

    @Value("#{'${web.heroRanking.activity.period}'.split('\\~')}")
    private List<String> heroRankingActivityPeriod = Lists.newArrayList();

    @Autowired
    private MembershipMapper membershipMapper;

    @Autowired
    private UserMembershipMapper userMembershipMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<HeroRankingView> obtainHeroRankingReferrer(Date tradingTime) {
        return investMapper.findHeroRankingByReferrer(tradingTime, heroRankingActivityPeriod.get(0), heroRankingActivityPeriod.get(1), 0, 10);
    }

    @Override
    public void saveMysteriousPrize(MysteriousPrizeDto mysteriousPrizeDto) {
        String prizeDate = new DateTime(mysteriousPrizeDto.getPrizeDate()).withTimeAtStartOfDay().toString("yyyy-MM-dd");
        redisWrapperClient.hsetSeri(MYSTERIOUSREDISKEY, prizeDate, mysteriousPrizeDto);
    }

    @Override
    public GivenMembership receiveMembership(String loginName) {
        if (DateTime.parse(heroRankingActivityPeriod.get(0), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate().after(DateTime.now().toDate())) {
            return GivenMembership.NO_TIME;
        }

        if (DateTime.parse(heroRankingActivityPeriod.get(1), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate().before(DateTime.now().toDate())) {
            return GivenMembership.END_TIME;
        }

        if (loginName == null || loginName.equals("")) {
            return GivenMembership.NO_LOGIN;
        }

        if (accountMapper.findByLoginName(loginName) == null) {
            return GivenMembership.NO_REGISTER;
        }

        if (userMembershipMapper.findByLoginNameByType(loginName, UserMembershipType.GIVEN) != null) {
            return GivenMembership.ALREADY_RECEIVED;
        }

        long investAmount = investMapper.sumSuccessInvestAmountByLoginName(null, loginName);
        Date registerTime = accountMapper.findByLoginName(loginName).getRegisterTime();
        if (registerTime != null && DateTime.parse(heroRankingActivityPeriod.get(0), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate().after(registerTime) && investAmount < 100000) {
            return GivenMembership.ALREADY_REGISTER_NOT_INVEST_1000;
        }

        if (registerTime != null && DateTime.parse(heroRankingActivityPeriod.get(0), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate().after(registerTime) && investAmount >= 100000) {
            createUserMembershipModel(loginName, MembershipLevel.V5.getLevel());
            return GivenMembership.ALREADY_REGISTER_ALREADY_INVEST_1000;
        }

        createUserMembershipModel(loginName, MembershipLevel.V5.getLevel());
        return GivenMembership.AFTER_START_ACTIVITY_REGISTER;
    }

    private void createUserMembershipModel(String loginName, int level) {
        UserMembershipModel userMembershipModel = new UserMembershipModel(loginName,
                membershipMapper.findByLevel(level).getId(),
                DateTime.now().plusMonths(1).toDate(),
                new Date(),
                UserMembershipType.GIVEN);
        userMembershipMapper.create(userMembershipModel);
    }

}
