package com.tuotiansudai.activity.controller;

import cn.jpush.api.utils.StringUtils;
import com.google.common.collect.Iterators;
import com.tuotiansudai.activity.repository.model.NewmanTyrantView;
import com.tuotiansudai.activity.service.MiddleAutumAndNationalDayService;
import com.tuotiansudai.activity.service.NewmanTyrantService;
import com.tuotiansudai.spring.LoginUserInfo;
import com.tuotiansudai.util.AmountConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qduljs2011 on 2018/9/7.
 */
@Controller
@RequestMapping(value = "/activity/middleautum-nationalday")
public class MiddleAutumAndNationalDayController {
    @Autowired
    private MiddleAutumAndNationalDayService middleAutumAndNationalDayService;
    @Autowired
    private NewmanTyrantService newmanTyrantService;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView newmanTyrant() {
        ModelAndView modelAndView = new ModelAndView("/activities/2018/mid-autumn-festival", "responsive", true);
        String loginName = LoginUserInfo.getLoginName();
        Date tradingTime = new Date();
        List<String> activityTime = middleAutumAndNationalDayService.getActivityTime();
        List<NewmanTyrantView> tyrantViews = middleAutumAndNationalDayService.obtainRecords(tradingTime);
        int investRanking = CollectionUtils.isNotEmpty(tyrantViews) ?
                Iterators.indexOf(tyrantViews.iterator(), input -> input.getLoginName().equalsIgnoreCase(loginName)) + 1 : 0;
        long investAmount = investRanking > 0 ? tyrantViews.get(investRanking - 1).getSumAmount() : 0;

        Date standardDate = new DateTime().withTime(22, 00, 0, 0).toDate();
        String prizeDtoKey = null;
        if (tradingTime.after(standardDate)) {
            prizeDtoKey = new DateTime().plusDays(1).toString("yyyy-MM-dd");
        } else {
            prizeDtoKey = new DateTime().toString("yyyy-MM-dd");
        }
        modelAndView.addObject("prizeDto", newmanTyrantService.obtainPrizeDto(prizeDtoKey));
        modelAndView.addObject("investRanking", investRanking);
        modelAndView.addObject("investAmount", investAmount);
        modelAndView.addObject("activityStartTime", activityTime.get(0));
        modelAndView.addObject("activityEndTime", activityTime.get(1));
        modelAndView.addObject("currentTime", prizeDtoKey);
        modelAndView.addObject("yesterdayTime", DateUtils.addDays(new DateTime().withTimeAtStartOfDay().toDate(), -1));
        return modelAndView;
    }

    @RequestMapping(value = "/records")
    @ResponseBody
    public Map obtainNewman(@RequestParam("tradingTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date tradingTime) {
        final String loginName = LoginUserInfo.getLoginName();
        List<NewmanTyrantView> newmanTyrantViews = middleAutumAndNationalDayService.obtainRecords(tradingTime);
        int investRanking = CollectionUtils.isNotEmpty(newmanTyrantViews) ?
                Iterators.indexOf(newmanTyrantViews.iterator(), input -> input.getLoginName().equalsIgnoreCase(loginName)) + 1 : 0;
        long investAmount = investRanking > 0 ? newmanTyrantViews.get(investRanking - 1).getSumAmount() : 0;
        newmanTyrantViews.stream().forEach(newmanTyrantView -> newmanTyrantView.setLoginName(newmanTyrantService.encryptMobileForWeb(loginName, newmanTyrantView.getLoginName(), newmanTyrantView.getMobile())));
        Map<String, Object> map = new HashMap<>();
        map.put("status", true);
        map.put("records", newmanTyrantViews);
        map.put("investRanking", StringUtils.isEmpty(loginName) ? "登录后查看" : (investRanking > 20 || investRanking == 0) ? "未上榜" : investRanking);
        map.put("investAmount", StringUtils.isEmpty(loginName) ? "登录后查看" : AmountConverter.convertCentToString(investAmount) + "元");
        return map;
    }
}
