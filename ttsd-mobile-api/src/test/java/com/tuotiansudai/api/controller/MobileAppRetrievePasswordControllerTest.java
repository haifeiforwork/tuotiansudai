package com.tuotiansudai.api.controller;

import com.tuotiansudai.api.controller.v1_0.MobileAppRetrievePasswordController;
import com.tuotiansudai.api.dto.v1_0.RetrievePasswordRequestDto;
import com.tuotiansudai.api.service.v1_0.MobileAppRetrievePasswordService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
    public void retrievePasswordIsOk() throws Exception {
        RetrievePasswordRequestDto retrievePasswordRequestDto = new RetrievePasswordRequestDto();
        retrievePasswordRequestDto.setPassword("123abc");
        retrievePasswordRequestDto.setValidateCode("123456");
        retrievePasswordRequestDto.setPhoneNum("12345678900");
        when(service.retrievePassword(any(RetrievePasswordRequestDto.class))).thenReturn(successResponseDto);
        doRequestWithServiceMockedTest("/retrievepassword",
                retrievePasswordRequestDto);
    }

    @Test
    public void retrievePasswordIsValid() throws Exception {
        RetrievePasswordRequestDto retrievePasswordRequestDto = new RetrievePasswordRequestDto();
        retrievePasswordRequestDto.setPhoneNum("13800138000");
        retrievePasswordRequestDto.setValidateCode("123456");
        retrievePasswordRequestDto.setPassword("000000");
        when(service.retrievePassword(any(RetrievePasswordRequestDto.class))).thenReturn(successResponseDto);
        doRequestWithServiceIsOkMockedTest("/retrievepassword",
                retrievePasswordRequestDto)
                .andExpect(jsonPath("$.code").value("0012"));
    }

}
