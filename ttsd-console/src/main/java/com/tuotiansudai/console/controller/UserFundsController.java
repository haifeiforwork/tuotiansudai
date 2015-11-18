package com.tuotiansudai.console.controller;

import com.google.common.collect.Lists;
import com.tuotiansudai.repository.model.UserBillBusinessType;
import com.tuotiansudai.repository.model.UserBillModel;
import com.tuotiansudai.repository.model.UserBillOperationType;
import com.tuotiansudai.service.UserBillService;
import com.tuotiansudai.util.CsvHeaderType;
import com.tuotiansudai.util.ExportCsvUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
public class UserFundsController {

    @Autowired
    private UserBillService userBillService;

    @RequestMapping(value = "/userFunds", method = RequestMethod.GET)
    public ModelAndView userFunds(@RequestParam(value = "userBillBusinessType", required = false) UserBillBusinessType userBillBusinessType,
                                  @RequestParam(value = "userBillOperationType", required = false) UserBillOperationType userBillOperationType,
                                  @RequestParam(value = "loginName", required = false) String loginName,
                                  @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                  @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                  @RequestParam(value = "currentPageNo", defaultValue = "1", required = false) int currentPageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                  @RequestParam(value = "export", required = false) String export,
                                  HttpServletResponse response) throws IOException{
        if (export != null && !export.equals("")) {
            response.setCharacterEncoding("UTF-8");
            try {
                response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode("用户资金查询.csv", "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/csv");
            int userFundsCount = userBillService.findUserFundsCount(userBillBusinessType, userBillOperationType, loginName, startTime, endTime);
            List<UserBillModel> userBillModels = userBillService.findUserFunds(userBillBusinessType, userBillOperationType, loginName, startTime, endTime, 1, userFundsCount);
            List<List<String>> data = Lists.newArrayList();
            for (UserBillModel userBillModel : userBillModels) {
                List<String> dataModel = Lists.newArrayList();
                DateTime dateTime = new DateTime(userBillModel.getCreatedTime());
                dataModel.add(dateTime != null ? dateTime.toString("yyyy-MM-dd") : "");
                dataModel.add(String.valueOf(userBillModel.getId()));
                dataModel.add(userBillModel.getLoginName());
                dataModel.add(userBillModel.getOperationType().getDescription());
                dataModel.add(userBillModel.getBusinessType().getDescription());
                dataModel.add(String.valueOf(new BigDecimal(userBillModel.getAmount()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN).doubleValue()));
                dataModel.add(String.valueOf(new BigDecimal(userBillModel.getBalance()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN).doubleValue()));
                dataModel.add(String.valueOf(new BigDecimal(userBillModel.getFreeze()).divide(new BigDecimal(100),2,BigDecimal.ROUND_DOWN).doubleValue()));
                data.add(dataModel);
            }
            ExportCsvUtil.createCsvOutputStream(CsvHeaderType.ConsoleUserFundsCsvHeader, data, response.getOutputStream());
            return null;
        } else {
            ModelAndView modelAndView = new ModelAndView("/user-funds");
            List<UserBillModel> userBillModels = userBillService.findUserFunds(userBillBusinessType, userBillOperationType, loginName, startTime, endTime, currentPageNo, pageSize);
            int userFundsCount = userBillService.findUserFundsCount(userBillBusinessType, userBillOperationType, loginName, startTime, endTime);
            modelAndView.addObject("loginName", loginName);
            modelAndView.addObject("startTime", startTime);
            modelAndView.addObject("endTime", endTime);
            modelAndView.addObject("userBillBusinessType", userBillBusinessType);
            modelAndView.addObject("userBillOperationType", userBillOperationType);
            modelAndView.addObject("currentPageNo", currentPageNo);
            modelAndView.addObject("pageSize", pageSize);
            modelAndView.addObject("userBillModels", userBillModels);
            modelAndView.addObject("userFundsCount", userFundsCount);
            modelAndView.addObject("businessTypeList", UserBillBusinessType.values());
            modelAndView.addObject("operationTypeList", UserBillOperationType.values());
            long totalPages = userFundsCount / pageSize + (userFundsCount % pageSize > 0 ? 1 : 0);
            boolean hasPreviousPage = currentPageNo > 1 && currentPageNo <= totalPages;
            boolean hasNextPage = currentPageNo < totalPages;
            modelAndView.addObject("hasPreviousPage", hasPreviousPage);
            modelAndView.addObject("hasNextPage", hasNextPage);
            return modelAndView;
        }
    }

}
