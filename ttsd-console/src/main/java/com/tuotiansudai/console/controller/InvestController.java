package com.tuotiansudai.console.controller;

import com.google.common.collect.Lists;
import com.tuotiansudai.dto.*;
import com.tuotiansudai.repository.model.*;
import com.tuotiansudai.service.InvestService;
import com.tuotiansudai.service.LoanService;
import com.tuotiansudai.service.RepayService;
import com.tuotiansudai.util.CsvHeaderType;
import com.tuotiansudai.util.ExportCsvUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/finance-manage")
public class InvestController {

    @Autowired
    private InvestService investService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private RepayService repayService;

    @RequestMapping(value = "/invests", method = RequestMethod.GET)
    public ModelAndView getInvestList(@RequestParam(name = "loanId", required = false) Long loanId,
                                      @RequestParam(name = "mobile", required = false) String investorMobile,
                                      @RequestParam(name = "channel", required = false) String channel,
                                      @RequestParam(name = "source", required = false) Source source,
                                      @RequestParam(name = "role", required = false) String role,
                                      @RequestParam(name = "investStatus", required = false) InvestStatus investStatus,
                                      @Min(value = 1) @RequestParam(name = "index", defaultValue = "1", required = false) int index,
                                      @Min(value = 1) @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
                                      @RequestParam(name = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                      @RequestParam(name = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                      @RequestParam(value = "export", required = false) String export,
                                      HttpServletResponse response) throws IOException {

        if (export != null && !export.equals("")) {
            response.setCharacterEncoding("UTF-8");
            try {
                response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode("用户投资记录.csv", "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/csv");
            long count = investService.findCountInvestPagination(loanId, investorMobile, channel, source, role, startTime, endTime, investStatus, null);
            InvestPaginationDataDto dataDto = investService.getInvestPagination(loanId, investorMobile, channel, source, role, 1, (int) count, startTime, endTime, investStatus, null);
            List<List<String>> data = Lists.newArrayList();
            List<InvestPaginationItemDataDto> investPaginationItemDataDtos = dataDto.getRecords();
            for (int i = 0; i < investPaginationItemDataDtos.size(); i++) {
                List<String> dataModel = Lists.newArrayList();
                InvestPaginationItemDataDto itemDataDto = investPaginationItemDataDtos.get(i);
                dataModel.add(String.valueOf(itemDataDto.getLoanId()));
                dataModel.add(itemDataDto.getLoanName());
                dataModel.add(String.valueOf(itemDataDto.getLoanPeriods()));
                dataModel.add(itemDataDto.getInvestorLoginName());
                dataModel.add(itemDataDto.isStaff() ? "是" : "否");
                dataModel.add(itemDataDto.getInvestorUserName());
                dataModel.add(itemDataDto.getInvestorMobile());
                dataModel.add(itemDataDto.getBirthday());
                dataModel.add(itemDataDto.getProvince());
                dataModel.add(itemDataDto.getCity());
                dataModel.add(itemDataDto.getReferrerLoginName());
                dataModel.add(itemDataDto.getReferrerLoginName() != null ? itemDataDto.isReferrerStaff() ? "是" : "否" : "");
                dataModel.add(itemDataDto.getReferrerUserName());
                dataModel.add(itemDataDto.getReferrerMobile());
                dataModel.add(itemDataDto.getChannel());
                dataModel.add(itemDataDto.getSource());
                dataModel.add(new DateTime(itemDataDto.getCreatedTime()).toString("yyyy-MM-dd HH:mm:ss"));
                dataModel.add(itemDataDto.isAutoInvest() ? "是" : "否");
                dataModel.add(itemDataDto.getAmount());
                dataModel.add(itemDataDto.getStatus());
                dataModel.add(itemDataDto.getRate());
                dataModel.add(itemDataDto.getExpectedFee());
                dataModel.add(itemDataDto.getActualFee());
                data.add(dataModel);
            }
            ExportCsvUtil.createCsvOutputStream(CsvHeaderType.ConsoleInvests, data, response.getOutputStream());
            return null;
        } else {
            InvestPaginationDataDto dataDto = investService.getInvestPagination(loanId, investorMobile, channel, source, role, index, pageSize, startTime, endTime, investStatus, null);
            List<String> channelList = investService.findAllChannel();

            ModelAndView mv = new ModelAndView("/invest-list");
            mv.addObject("data", dataDto);
            mv.addObject("mobile", investorMobile);
            mv.addObject("channel", channel);
            mv.addObject("loanId", loanId);
            mv.addObject("source", source);
            mv.addObject("role", role);
            mv.addObject("startTime", startTime);
            mv.addObject("endTime", endTime);
            mv.addObject("investStatus", investStatus);
            mv.addObject("investStatusList", InvestStatus.values());
            mv.addObject("channelList", channelList);
            mv.addObject("sourceList", Source.values());
            mv.addObject("roleList", Role.values());
            return mv;
        }
    }

    @RequestMapping(value = "/invest-repay/{investId:^\\d+$}", method = RequestMethod.GET)
    public ModelAndView getInvestRepayList(@PathVariable long investId) {
        InvestModel investModel = investService.findById(investId);
        LoanModel loanModel = loanService.findLoanById(investModel.getLoanId());

        BaseDto<InvestRepayDataDto> investRepayDto = repayService.findInvestorInvestRepay(investModel.getLoginName(), investModel.getId());
        List<InvestRepayDataItemDto> repayDataItems = investRepayDto.getData().getRecords();
        if (repayDataItems == null) {
            repayDataItems = new ArrayList<>(0);
        }

        ModelAndView mv = new ModelAndView("invest-repay-list");
        mv.addObject("repayList", repayDataItems);
        mv.addObject("invest", investModel);
        mv.addObject("loan", loanModel);
        return mv;
    }
}
