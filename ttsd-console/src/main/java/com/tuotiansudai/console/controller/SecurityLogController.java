package com.tuotiansudai.console.controller;

import com.google.common.collect.Lists;
import com.tuotiansudai.dto.AuditLogPaginationItemDataDto;
import com.tuotiansudai.dto.BasePaginationDataDto;
import com.tuotiansudai.dto.LoginLogPaginationItemDataDto;
import com.tuotiansudai.service.AuditLogService;
import com.tuotiansudai.service.LoginLogService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Min;
import java.util.Date;

@Controller
@RequestMapping(path = "/security-log")
public class SecurityLogController {

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private AuditLogService auditLogService;

    @RequestMapping(path = "/login-log", method = RequestMethod.GET)
    public ModelAndView loginLog(@RequestParam(name = "loginName", required = false) String loginName,
                                 @RequestParam(name = "selectedYear", required = false) Integer selectedYear,
                                 @RequestParam(name = "selectedMonth", required = false) Integer selectedMonth,
                                 @Min(value = 1) @RequestParam(name = "index", defaultValue = "1", required = false) int index,
                                 @Min(value = 1) @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
                                 @RequestParam(name = "success", required = false) Boolean success) {

        DateTime now = new DateTime().withTimeAtStartOfDay();
        if (selectedYear == null) {
            selectedYear = now.getYear();
        }
        if (selectedMonth == null) {
            selectedMonth = now.getMonthOfYear();
        }

        if (new DateTime().withDate(selectedYear, selectedMonth, now.dayOfMonth().getMaximumValue()).isAfterNow() ||
                new DateTime().withDate(selectedYear, selectedMonth, now.dayOfMonth().getMaximumValue()).isBefore(new DateTime(2015, 11, 1, 0, 0, 0))) {
            selectedYear = now.getYear();
            selectedMonth = now.getMonthOfYear();
        }

        BasePaginationDataDto<LoginLogPaginationItemDataDto> data = loginLogService.getLoginLogPaginationData(loginName, success, index, pageSize, selectedYear, selectedMonth);

        ModelAndView modelAndView = new ModelAndView("/login-log");

        modelAndView.addObject("data", data);
        modelAndView.addObject("years", Lists.newArrayList("2015", "2016", "2017", "2018", "2019", "2020"));
        modelAndView.addObject("loginName", loginName);
        modelAndView.addObject("selectedYear", String.valueOf(selectedYear));
        modelAndView.addObject("selectedMonth", String.valueOf(selectedMonth));
        modelAndView.addObject("success", success);

        return modelAndView;
    }

    @RequestMapping(path = "/audit-log", method = RequestMethod.GET)
    public ModelAndView auditLog(@RequestParam(name = "loginName", required = false) String loginName,
                                 @RequestParam(name = "operatorLoginName", required = false) String operatorLoginName,
                                 @RequestParam(name = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                 @RequestParam(name = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                 @Min(value = 1) @RequestParam(name = "index", defaultValue = "1", required = false) int index,
                                 @Min(value = 1) @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {


        BasePaginationDataDto<AuditLogPaginationItemDataDto> data = auditLogService.getAuditLogPaginationData(loginName, operatorLoginName, startTime, endTime, index, pageSize);

        ModelAndView modelAndView = new ModelAndView("/audit-log");

        modelAndView.addObject("data", data);
        modelAndView.addObject("loginName", loginName);
        modelAndView.addObject("operatorLoginName", operatorLoginName);
        modelAndView.addObject("startTime", startTime);
        modelAndView.addObject("endTime", endTime);

        return modelAndView;
    }
}
