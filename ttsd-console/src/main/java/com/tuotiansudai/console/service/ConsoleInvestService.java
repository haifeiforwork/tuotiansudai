package com.tuotiansudai.console.service;

import com.google.common.base.*;
import com.google.common.collect.Lists;
import com.tuotiansudai.coupon.repository.mapper.CouponMapper;
import com.tuotiansudai.coupon.repository.mapper.CouponRepayMapper;
import com.tuotiansudai.coupon.repository.mapper.UserCouponMapper;
import com.tuotiansudai.coupon.repository.model.CouponModel;
import com.tuotiansudai.coupon.repository.model.CouponRepayModel;
import com.tuotiansudai.coupon.repository.model.UserCouponModel;
import com.tuotiansudai.dto.*;
import com.tuotiansudai.enums.CouponType;
import com.tuotiansudai.repository.mapper.*;
import com.tuotiansudai.repository.model.*;
import com.tuotiansudai.util.*;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class ConsoleInvestService {

    static Logger logger = Logger.getLogger(ConsoleInvestService.class);

    @Value(value = "${web.coupon.lock.seconds}")
    private int couponLockSeconds;

    @Autowired
    private InvestMapper investMapper;

    @Value(value = "${web.newbie.invest.limit}")
    private int newbieInvestLimit;

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponRepayMapper couponRepayMapper;

    @Value(value = "${pay.interest.fee}")
    private double defaultFee;

    @Autowired
    private UserMapper userMapper;

    public InvestPaginationDataDto getInvestPagination(Long loanId, String investorMobile, String channel, Source source,
                                                       Role role, Date startTime, Date endTime, InvestStatus investStatus,
                                                       PreferenceType preferenceType, int index, int pageSize) {
        List<InvestPaginationItemView> items = Lists.newArrayList();

        String investorLoginName = null;
        if (!StringUtils.isEmpty(investorMobile)) {
            UserModel userModel = userMapper.findByMobile(investorMobile);
            if (null != userModel) {
                investorLoginName = userMapper.findByMobile(investorMobile).getLoginName();
            } else {
                investorLoginName = investorMobile;
            }
        }

        Date queryEndTime = new DateTime(endTime).plusDays(1).withTimeAtStartOfDay().plusSeconds(-1).toDate();
        final long count = investMapper.findCountInvestPagination(loanId, investorLoginName, channel, source, role, startTime, queryEndTime, investStatus, preferenceType);
        final long investAmountSum = investMapper.sumInvestAmountConsole(loanId, investorLoginName, channel, source, role, startTime, queryEndTime, investStatus, preferenceType);
        if (count > 0) {
            int totalPages = PaginationUtil.calculateMaxPage(count, pageSize);
            index = index > totalPages ? totalPages : index;
            items = investMapper.findInvestPagination(loanId, investorLoginName, channel, source, role, startTime, endTime, investStatus, preferenceType, (index - 1) * pageSize, pageSize);
        }

        List<InvestPaginationItemDataDto> records = Lists.transform(items, new Function<InvestPaginationItemView, InvestPaginationItemDataDto>() {
            @Override
            public InvestPaginationItemDataDto apply(InvestPaginationItemView view) {
                InvestPaginationItemDataDto investPaginationItemDataDto = new InvestPaginationItemDataDto(view);
                CouponModel couponModel = couponMapper.findById(view.getCouponId());
                if (null != couponModel) {
                    long couponActualInterest = 0;
                    if (couponModel.getCouponType().equals(CouponType.RED_ENVELOPE)) {
                        List<UserCouponModel> userCouponModels = userCouponMapper.findUserCouponSuccessByInvestId(view.getInvestId());
                        for (UserCouponModel userCouponModel : userCouponModels) {
                            couponActualInterest += userCouponModel.getActualInterest();
                        }
                    } else {
                        List<CouponRepayModel> couponRepayModels = couponRepayMapper.findByUserCouponByInvestId(view.getInvestId());
                        for (CouponRepayModel couponRepayModel : couponRepayModels) {
                            couponActualInterest += couponRepayModel.getActualInterest();
                        }
                    }
                    investPaginationItemDataDto.setCouponActualInterest(couponActualInterest);
                    investPaginationItemDataDto.setCouponDetail(couponModel);
                }
                return investPaginationItemDataDto;
            }
        });

        InvestPaginationDataDto dto = new InvestPaginationDataDto(index, pageSize, count, records);

        dto.setSumAmount(investAmountSum);

        dto.setStatus(true);

        return dto;
    }

    public List<String> findAllChannel() {
        return investMapper.findAllChannels();
    }

    public List<String> findAllInvestChannels() {
        return investMapper.findAllInvestChannels();
    }
}
