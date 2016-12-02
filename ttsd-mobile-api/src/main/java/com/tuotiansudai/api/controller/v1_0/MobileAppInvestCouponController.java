package com.tuotiansudai.api.controller.v1_0;

import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.InvestRequestDto;
import com.tuotiansudai.api.dto.v1_0.UserCouponListResponseDataDto;
import com.tuotiansudai.api.service.v1_0.MobileAppInvestCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(description = "投资优惠券列表")
public class MobileAppInvestCouponController extends MobileAppBaseController {

    @Autowired
    private MobileAppInvestCouponService mobileAppInvestCouponService;

    @RequestMapping(value = "/get/investCoupons", method = RequestMethod.POST)
    @ApiOperation("投资优惠券列表")
    public BaseResponseDto<UserCouponListResponseDataDto> getCoupons( @RequestBody InvestRequestDto dto) {
        dto.getBaseParam().setUserId(getLoginName());
        return mobileAppInvestCouponService.getInvestCoupons(dto);
    }
}
