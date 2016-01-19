package com.tuotiansudai.console.controller;

import com.google.common.collect.Lists;
import com.tuotiansudai.client.RedisWrapperClient;
import com.tuotiansudai.console.util.LoginUserInfo;
import com.tuotiansudai.coupon.dto.CouponDto;
import com.tuotiansudai.coupon.repository.model.CouponModel;
import com.tuotiansudai.coupon.repository.model.UserCouponModel;
import com.tuotiansudai.coupon.repository.model.UserGroup;
import com.tuotiansudai.coupon.service.CouponActivationService;
import com.tuotiansudai.coupon.service.CouponService;
import com.tuotiansudai.dto.BaseDataDto;
import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.exception.CreateCouponException;
import com.tuotiansudai.repository.mapper.UserMapper;
import com.tuotiansudai.repository.model.CouponType;
import com.tuotiansudai.repository.model.ProductType;
import com.tuotiansudai.repository.model.UserModel;
import com.tuotiansudai.util.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/activity-manage")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponActivationService couponActivationService;

    @Autowired
    private RedisWrapperClient redisWrapperClient;

    @Autowired
    private UserMapper userMapper;

    private static String redisKeyTemplate = "console:{0}:importcouponuser";

    @RequestMapping(value = "/coupon",method = RequestMethod.GET)
    public ModelAndView coupon(){
        ModelAndView modelAndView = new  ModelAndView("/coupon");
        modelAndView.addObject("couponTypes", Lists.newArrayList(CouponType.values()));
        modelAndView.addObject("productTypes", Lists.newArrayList(ProductType.values()));
        modelAndView.addObject("userGroups", Lists.newArrayList(UserGroup.values()));
        return modelAndView;
    }

    @RequestMapping(value = "/coupon",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView createCoupon(@Valid @ModelAttribute CouponDto couponDto,RedirectAttributes redirectAttributes){
        String loginName = LoginUserInfo.getLoginName();
        ModelAndView modelAndView = new ModelAndView();
        Long id = couponDto.getId();
        try {
            if(id != null){
                couponService.editCoupon(loginName, couponDto);
            }else{
                couponService.createCoupon(loginName, couponDto);
            }
            if (couponDto.getCouponType() == CouponType.INTEREST_COUPON) {
                modelAndView.setViewName("redirect:/activity-manage/interest-coupons");
            } else {
                modelAndView.setViewName("redirect:/activity-manage/coupons");
            }
            return modelAndView;
        } catch (CreateCouponException e) {
            if(id != null){
                if (couponDto.getCouponType() == CouponType.INTEREST_COUPON) {
                    modelAndView.setViewName("redirect:/activity-manage/interest-coupon/"+id+"/edit");
                } else {
                    modelAndView.setViewName("redirect:/activity-manage/coupon/" + id + "/edit");
                }
            }else{
                if (couponDto.getCouponType() == CouponType.INTEREST_COUPON) {
                    modelAndView.setViewName("redirect:/activity-manage/interest-coupon");
                } else {
                    modelAndView.setViewName("redirect:/activity-manage/coupon");
                }
            }
            redirectAttributes.addFlashAttribute("coupon", couponDto);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return modelAndView;
        }

    }

    @RequestMapping(value = "/interest-coupon", method = RequestMethod.GET)
    public ModelAndView interestCoupon() {
        ModelAndView modelAndView = new  ModelAndView("/interest-coupon");
        modelAndView.addObject("productTypes", Lists.newArrayList(ProductType.values()));
        modelAndView.addObject("userGroups", Lists.newArrayList(UserGroup.values()));
        return modelAndView;
    }

    @RequestMapping(value = "/coupon/{id:^\\d+$}/edit",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView edit(@PathVariable long id,Model model){
        ModelAndView modelAndView = new ModelAndView("/coupon-edit");

        CouponModel couponModel = couponService.findCouponById(id);
        if (!model.containsAttribute("coupon")) {
            CouponDto couponDto = new CouponDto(couponModel);
            modelAndView.addObject("coupon", couponDto);
        }else{
            Map modelMap = model.asMap();
            for(Object modelKey : modelMap.keySet()){
                modelAndView.addObject(modelKey.toString(),modelMap.get(modelKey));
            }
        }

        modelAndView.addObject("productTypes", Lists.newArrayList(ProductType.values()));
        modelAndView.addObject("userGroups", Lists.newArrayList(UserGroup.values()));
        return modelAndView;

    }

    @RequestMapping(value = "/coupon/{couponId:^\\d+$}/active",method = RequestMethod.POST)
    @ResponseBody
    public BaseDto<BaseDataDto> activeCoupon(@PathVariable long couponId){
        String loginName = LoginUserInfo.getLoginName();
        couponActivationService.active(loginName, couponId);

        BaseDataDto dataDto = new BaseDataDto();
        dataDto.setStatus(true);
        BaseDto<BaseDataDto> baseDto = new BaseDto<>();
        baseDto.setData(dataDto);

        return baseDto;
    }

    @RequestMapping(value = "/coupon/{couponId:^\\d+$}/inactive", method = RequestMethod.POST)
    @ResponseBody
    public BaseDto<BaseDataDto> inactiveCoupon(@PathVariable long couponId) {
        String loginName = LoginUserInfo.getLoginName();
        couponActivationService.inactive(loginName, couponId);
        BaseDataDto dataDto = new BaseDataDto();
        dataDto.setStatus(true);
        BaseDto<BaseDataDto> baseDto = new BaseDto<>();
        baseDto.setData(dataDto);

        return baseDto;
    }

    @RequestMapping(value = "/coupon/user-group/{userGroup}/estimate",method = RequestMethod.GET)
    @ResponseBody
    public long findEstimatedCount(@PathVariable UserGroup userGroup){
        return couponService.findEstimatedCount(userGroup);
    }

    @RequestMapping(value = "/interest-coupons", method = RequestMethod.GET)
    public ModelAndView interestCoupons(@RequestParam(value = "index",required = false,defaultValue = "1") int index,
                                        @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("/interest-coupons");
        modelAndView.addObject("index", index);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("coupons", couponService.findInterestCoupons(index, pageSize));
        int couponsCount = couponService.findInterestCouponsCount();
        modelAndView.addObject("couponsCount", couponsCount);
        long totalPages = couponsCount / pageSize + (couponsCount % pageSize > 0 ? 1 : 0);
        boolean hasPreviousPage = index > 1 && index <= totalPages;
        boolean hasNextPage = index < totalPages;
        modelAndView.addObject("hasPreviousPage", hasPreviousPage);
        modelAndView.addObject("hasNextPage", hasNextPage);
        return modelAndView;
    }

    @RequestMapping(value = "/coupons",method = RequestMethod.GET)
    public ModelAndView coupons(@RequestParam(value = "index",required = false,defaultValue = "1") int index,
                                 @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("/coupons");
        modelAndView.addObject("index", index);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("coupons", couponService.findCoupons(index, pageSize));
        int couponsCount = couponService.findCouponsCount();
        modelAndView.addObject("couponsCount", couponsCount);
        long totalPages = couponsCount / pageSize + (couponsCount % pageSize > 0 ? 1 : 0);
        boolean hasPreviousPage = index > 1 && index <= totalPages;
        boolean hasNextPage = index < totalPages;
        modelAndView.addObject("hasPreviousPage", hasPreviousPage);
        modelAndView.addObject("hasNextPage", hasNextPage);
        return modelAndView;
    }

    @RequestMapping(value = "/coupon/{couponId:^\\d+$}/detail", method = RequestMethod.GET)
    public ModelAndView couponDetail(@PathVariable long couponId, @RequestParam(value = "isUsed",required = false) Boolean isUsed) {
        ModelAndView modelAndView = new ModelAndView("/coupon-detail");
        List<UserCouponModel> userCoupons = couponService.findCouponDetail(couponId, isUsed);
        modelAndView.addObject("userCoupons", userCoupons);
        modelAndView.addObject("isUsed", isUsed);
        modelAndView.addObject("couponId", couponId);
        return modelAndView;
    }

    @RequestMapping(value = "/coupon/{couponId:^\\d+$}", method = RequestMethod.DELETE)
    @ResponseBody
    public BaseDto<BaseDataDto> couponDelete(@PathVariable long couponId) {
        BaseDataDto dataDto = new BaseDataDto();
        dataDto.setStatus(true);
        BaseDto<BaseDataDto> baseDto = new BaseDto<>();
        baseDto.setData(dataDto);
        String loginName = LoginUserInfo.getLoginName();
        couponService.deleteCoupon(loginName, couponId);
        return baseDto;
    }

    @RequestMapping(value = "/import-excel", method = RequestMethod.POST)
    @ResponseBody
    public List<Object> importExcel(HttpServletRequest request) throws Exception{
        String uuid = UUIDGenerator.generate();
        String redisKey = MessageFormat.format(redisKeyTemplate, uuid);
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multiRequest.getFile("file");
        InputStream inputStream = multipartFile.getInputStream();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        List<String> listSuccess = new ArrayList<>();
        List<String> listFailed = new ArrayList<>();
        for (int rowNum = 0; rowNum < hssfSheet.getLastRowNum()+1; rowNum++) {
            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
            int firstCellNum = hssfRow.getFirstCellNum();
            HSSFCell hssfCell = hssfRow.getCell(firstCellNum);
            String loginName = getStringVal(hssfCell);
            UserModel userModel = userMapper.findByLoginNameOrMobile(loginName);
            if (userModel == null) {
                listFailed.add(loginName);
            } else {
                listSuccess.add(loginName);
            }
        }
        redisWrapperClient.hset(redisKey, "failed", StringUtils.join(listFailed, ","));
        redisWrapperClient.hset(redisKey, "success", StringUtils.join(listSuccess, ","));
        List<Object> list = new ArrayList<>();
        list.add(uuid);
        list.add(hssfSheet.getLastRowNum()+1);
        return list;
    }

    private String getStringVal(HSSFCell hssfCell) {
        switch (hssfCell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                hssfCell.setCellType(Cell.CELL_TYPE_STRING);
                return hssfCell.getStringCellValue();
            case Cell.CELL_TYPE_STRING:
                return hssfCell.getStringCellValue();
            default:
                return "";
        }
    }

    @RequestMapping(value = "/coupon/{couponId:^\\d+$}/redis", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getRedisImportExcel(@PathVariable long couponId) {
        List<String> list = new ArrayList<>();
        String redisKey = MessageFormat.format(redisKeyTemplate, String.valueOf(couponId));
        if (redisWrapperClient.hexists(redisKey, "failed")) {
            list.add(redisWrapperClient.hget(redisKey, "failed"));
        }
        if (redisWrapperClient.hexists(redisKey, "success")) {
            list.add(redisWrapperClient.hget(redisKey, "success"));
        }
        return list;
    }

}
