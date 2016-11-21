package com.tuotiansudai.api.service.v1_0.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.tuotiansudai.api.dto.v1_0.*;
import com.tuotiansudai.api.service.v1_0.MobileAppPointService;
import com.tuotiansudai.api.util.PageValidUtils;
import com.tuotiansudai.point.dto.SignInPointDto;
import com.tuotiansudai.point.repository.mapper.PointBillMapper;
import com.tuotiansudai.point.repository.mapper.PointTaskMapper;
import com.tuotiansudai.point.repository.mapper.UserPointTaskMapper;
import com.tuotiansudai.point.repository.model.PointBillModel;
import com.tuotiansudai.point.repository.model.PointTask;
import com.tuotiansudai.point.repository.model.PointTaskModel;
import com.tuotiansudai.point.repository.model.UserPointTaskModel;
import com.tuotiansudai.point.service.SignInService;
import com.tuotiansudai.repository.mapper.AccountMapper;
import com.tuotiansudai.repository.model.AccountModel;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class MobileAppPointServiceImpl implements MobileAppPointService {

    static Logger logger = Logger.getLogger(MobileAppPointServiceImpl.class);

    private final static List<PointTask> NEWBIE_TASKS = Lists.newArrayList(PointTask.REGISTER, PointTask.BIND_BANK_CARD, PointTask.FIRST_RECHARGE, PointTask.FIRST_INVEST);

    @Autowired
    private SignInService signInService;

    @Autowired
    private PointBillMapper pointBillMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PointTaskMapper pointTaskMapper;

    @Autowired
    private PageValidUtils pageValidUtils;

    @Autowired
    private UserPointTaskMapper userPointTaskMapper;
    public BaseResponseDto signIn(BaseParamDto baseParamDto) {
        String loginName = baseParamDto.getBaseParam().getUserId();
        AccountModel accountModel = accountMapper.findByLoginName(loginName);
        if(accountModel == null){
            return new BaseResponseDto(ReturnMessage.USER_IS_NOT_CERTIFICATED.getCode(),ReturnMessage.USER_IS_NOT_CERTIFICATED.getMsg());
        }
        SignInPointDto signInPointDto = signInService.signIn(loginName);

        SignInResponseDataDto dataDto = new SignInResponseDataDto();
        dataDto.setPoint(signInPointDto.getSignInPoint());
        dataDto.setSignInTimes(signInPointDto.getSignInCount());
        dataDto.setNextSignInPoint(signInPointDto == null ? 0 : signInPointDto.getNextSignInPoint());

        BaseResponseDto dto = new BaseResponseDto();
        if (signInPointDto.getStatus()) {
            dto.setCode(ReturnMessage.SUCCESS.getCode());
            dto.setMessage(ReturnMessage.SUCCESS.getMsg());
        } else {
            dto.setCode(ReturnMessage.MULTIPLE_SIGN_IN.getCode());
            dto.setMessage(ReturnMessage.MULTIPLE_SIGN_IN.getMsg());
        }
        dto.setData(dataDto);

        return dto;
    }

    public BaseResponseDto getLastSignInTime(BaseParamDto baseParamDto) {
        String loginName = baseParamDto.getBaseParam().getUserId();
        AccountModel accountModel = accountMapper.findByLoginName(loginName);
        if(accountModel == null){
            return new BaseResponseDto(ReturnMessage.USER_IS_NOT_CERTIFICATED.getCode(),ReturnMessage.USER_IS_NOT_CERTIFICATED.getMsg());
        }
        SignInPointDto lastSignInPointDto = signInService.getLastSignIn(loginName);
        LastSignInTimeResponseDataDto dataDto = new LastSignInTimeResponseDataDto();
        DateTime today = new DateTime().withTimeAtStartOfDay();
        int signInCount = 0;
        if (lastSignInPointDto != null && (Days.daysBetween(new DateTime(lastSignInPointDto.getSignInDate()), today) == Days.ONE
                        || Days.daysBetween(new DateTime(lastSignInPointDto.getSignInDate()), today) == Days.ZERO)) {
            signInCount = lastSignInPointDto.getSignInCount();
        }
        dataDto.setSignIn(signInService.signInIsSuccess(loginName));
        dataDto.setSignInTimes(signInCount);
        dataDto.setNextSignInPoint(signInService.getNextSignInPoint(loginName));

        BaseResponseDto dto = new BaseResponseDto();
        dto.setCode(ReturnMessage.SUCCESS.getCode());
        dto.setMessage(ReturnMessage.SUCCESS.getMsg());
        dto.setData(dataDto);
        return dto;
    }

    @Override
    public BaseResponseDto queryPointBillList(PointBillRequestDto pointBillRequestDto) {

        String loginName = pointBillRequestDto.getBaseParam().getUserId();
        AccountModel accountModel = accountMapper.findByLoginName(loginName);
        if(accountModel == null){
            return new BaseResponseDto(ReturnMessage.USER_IS_NOT_CERTIFICATED.getCode(),ReturnMessage.USER_IS_NOT_CERTIFICATED.getMsg());
        }
        BaseResponseDto dto = new BaseResponseDto();
        Integer index = pointBillRequestDto.getIndex();
        Integer pageSize = pointBillRequestDto.getPageSize();
        if (index == null || index <= 0) {
            index = 1;
        }
        pageSize = pageValidUtils.validPageSizeLimit(pageSize);

        dto.setCode(ReturnMessage.SUCCESS.getCode());
        dto.setMessage(ReturnMessage.SUCCESS.getMsg());
        PointBillResponseDataDto pointBillResponseDataDto = new PointBillResponseDataDto();
        pointBillResponseDataDto.setIndex(index);
        pointBillResponseDataDto.setPageSize(pageSize);
        pointBillResponseDataDto.setPointBills(convertPointBillRecordDto(pointBillMapper.findPointBillPagination(loginName, (index - 1) * pageSize, pageSize, null, null, null)));
        pointBillResponseDataDto.setTotalCount(pointBillMapper.findCountPointBillPagination(loginName, null, null, null));
        dto.setData(pointBillResponseDataDto);
        return dto;
    }
    private List<PointBillRecordResponseDataDto> convertPointBillRecordDto(List<PointBillModel> userBillList) {

        return Lists.transform(userBillList, new Function<PointBillModel, PointBillRecordResponseDataDto>() {
            @Override
            public PointBillRecordResponseDataDto apply(PointBillModel input) {
                PointBillRecordResponseDataDto pointBillRecordResponseDataDto = new PointBillRecordResponseDataDto();
                pointBillRecordResponseDataDto.setBusinessType(input.getBusinessType().name());
                pointBillRecordResponseDataDto.setBusinessTypeName(input.getBusinessType().getDescription());
                pointBillRecordResponseDataDto.setPointBillId(String.valueOf(input.getId()));
                pointBillRecordResponseDataDto.setPoint(String.valueOf(input.getPoint()));
                pointBillRecordResponseDataDto.setCreatedDate(new DateTime(input.getCreatedTime()).toString("yyyy-MM-dd HH:mm:ss"));
                return pointBillRecordResponseDataDto;
            }
        });
    }

    @Override
    public BaseResponseDto queryPoint(BaseParamDto baseParamDto) {
        String loginName = baseParamDto.getBaseParam().getUserId();
        AccountModel accountModel = accountMapper.findByLoginName(loginName);
        if(accountModel == null){
            return new BaseResponseDto(ReturnMessage.USER_IS_NOT_CERTIFICATED.getCode(),ReturnMessage.USER_IS_NOT_CERTIFICATED.getMsg());
        }

        PointResponseDataDto dataDto = new PointResponseDataDto();
        dataDto.setPoint(accountModel.getPoint());

        BaseResponseDto dto = new BaseResponseDto();
        dto.setCode(ReturnMessage.SUCCESS.getCode());
        dto.setMessage(ReturnMessage.SUCCESS.getMsg());
        dto.setData(dataDto);

        return dto;
    }
}
