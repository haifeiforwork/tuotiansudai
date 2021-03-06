package com.tuotiansudai.api.controller.v1_0;

import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.FeedbackRequestDto;
import com.tuotiansudai.api.dto.v1_0.ReturnMessage;
import com.tuotiansudai.repository.model.FeedbackType;
import com.tuotiansudai.repository.model.Source;
import com.tuotiansudai.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Locale;


@RestController
@Api(description = "意见反馈")
public class MobileAppFeedbackController extends MobileAppBaseController {
    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    @ApiOperation("意见反馈")
    public BaseResponseDto feedback(@Valid @RequestBody FeedbackRequestDto feedbackRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            String errorCode = fieldError.getDefaultMessage();
            String errorMessage = ReturnMessage.getErrorMsgByCode(errorCode);
            return new BaseResponseDto(errorCode, errorMessage);
        }

        Source source = Source.valueOf(feedbackRequestDto.getBaseParam().getPlatform().toUpperCase(Locale.ENGLISH));

        FeedbackType type;
        if (StringUtils.isEmpty(feedbackRequestDto.getType())) {
            type = FeedbackType.opinion;
        } else {
            type = FeedbackType.valueOf(feedbackRequestDto.getType());
        }

        feedbackService.create(getLoginName(), source, type, feedbackRequestDto.getContent(),feedbackRequestDto.getContact());
        return new BaseResponseDto(ReturnMessage.SUCCESS);
    }
}
