package com.tuotiansudai.coupon.util;

import com.tuotiansudai.coupon.repository.model.UserGroup;
import com.tuotiansudai.repository.model.CouponType;

import java.util.List;

public interface InvestAchievementUserCollector {

    List<String> collect(long couponId);

    boolean contains(long loanId, String loginName, UserGroup userGroup, CouponType couponType);
}
