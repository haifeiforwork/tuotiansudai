package com.tuotiansudai.api.controller;

import com.tuotiansudai.api.dto.RetrievePasswordRequestDto;
import com.tuotiansudai.api.service.MobileAppRetrievePasswordService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.bind.annotation.RequestBody;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class MobileAppRetrievePasswordControllerTest extends ControllerTestBase {

    @InjectMocks
    private MobileAppRetrievePasswordController controller;

    @Mock
    private MobileAppRetrievePasswordService service;

    @Override
    protected Object getControllerObject() {
        return controller;
    }

    @Test
    public void retrievePassword() throws Exception {
        when(service.retrievePassword(any(RetrievePasswordRequestDto.class))).thenReturn(successResponseDto);
        doRequestWithServiceMockedTest("/retrievepassword",
                new RetrievePasswordRequestDto());
    }

    @Test
    public void validateAuthCode() throws Exception {
        when(service.validateAuthCode(any(RetrievePasswordRequestDto.class))).thenReturn(successResponseDto);
        doRequestWithServiceMockedTest("/validatecaptcha",
                new RetrievePasswordRequestDto());
    }

    @Test
    public void sendSMS() throws Exception {
        when(service.sendSMS(any(RetrievePasswordRequestDto.class), anyString())).thenReturn(successResponseDto);
        doRequestWithServiceMockedTest("/retrievepassword/sendsms",
                new RetrievePasswordRequestDto());
    }
}
