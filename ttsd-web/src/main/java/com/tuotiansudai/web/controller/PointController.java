package com.tuotiansudai.web.controller;

import com.tuotiansudai.coupon.service.CouponService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuotiansudai.dto.BaseDataDto;
import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.point.dto.PointTaskDto;
import com.tuotiansudai.point.repository.mapper.PointBillMapper;
import com.tuotiansudai.point.repository.model.PointBillModel;

import com.tuotiansudai.point.service.PointService;
import com.tuotiansudai.point.service.PointTaskService;
import com.tuotiansudai.point.service.SignInService;
import com.tuotiansudai.web.util.LoginUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping(path = "/point")
public class PointController {
    @Autowired
    private SignInService signInService;
    @Autowired
    private PointService pointService;
    @Autowired
    private PointBillMapper pointBillMapper;
    @Autowired
    private PointTaskService pointTaskService;

    @Autowired
    private CouponService couponService;

    @RequestMapping(path = "/sign-in", method = RequestMethod.POST)
    @ResponseBody
    public BaseDto<BaseDataDto> signIn() {
        String loginName = LoginUserInfo.getLoginName();
        BaseDto<BaseDataDto> baseDto = new BaseDto<>();
        BaseDataDto baseDataDto = signInService.signIn(loginName);
        baseDataDto.setStatus(true);
        baseDto.setData(baseDataDto);
        baseDto.setSuccess(true);
        return baseDto;
    }
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView myPoint(){
        String loginName = LoginUserInfo.getLoginName();
        ModelAndView modelAndView = new ModelAndView("/point");
        long myPoint = pointService.getAvailablePoint(loginName);
        List<PointBillModel> pointBillModels = pointBillMapper.findByLoginName(loginName);
        if(pointBillModels != null){
            pointBillModels =  pointBillModels.size() > 3 ?pointBillModels.subList(0,3):pointBillModels;
            List<Map<String,Date>> obtainedPoints = Lists.newArrayList();
            for(PointBillModel pointBillModel : pointBillModels){
                obtainedPoints.add(Maps.newHashMap(ImmutableMap.<String,Date>builder().put("" + pointBillModel.getPoint(),pointBillModel.getCreatedTime()).build()));
            }
            modelAndView.addObject("obtainedPoints",obtainedPoints);
        }
        boolean signedIn = signInService.signInIsSuccess(loginName);
        List<PointTaskDto> pointTaskDtos = pointTaskService.displayPointTask(1,10,loginName);
        modelAndView.addObject("pointTaskDtos",pointTaskDtos);
        modelAndView.addObject("signedIn",signedIn);
        modelAndView.addObject("myPoint",myPoint);
        return modelAndView;
    }

    @RequestMapping(value = "/exchange_coupon_list", method = RequestMethod.GET)
    public ModelAndView exchangeCouponList(){
        ModelAndView modelAndView = new ModelAndView("/point_exchange_list");
        return modelAndView;

    }


}
