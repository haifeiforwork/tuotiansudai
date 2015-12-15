package com.tuotiansudai.coupon.repository.mapper;

import com.tuotiansudai.coupon.repository.model.UserCouponModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCouponMapper {

    void create(UserCouponModel userCouponModel);

    void update(UserCouponModel userCouponModel);

    List<UserCouponModel> findByLoginName(@Param("loginName") String loginName);

    UserCouponModel findByCouponId(@Param("couponId") long couponId);

    void updateUserCoupon(UserCouponModel userCouponModel);

    List<UserCouponModel> findByLoanId(@Param("loanId") long loanId);
}
