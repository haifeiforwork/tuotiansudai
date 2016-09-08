package com.tuotiansudai.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.LoginResponseDataDto;
import com.tuotiansudai.api.dto.v1_0.ReturnMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

@Component
public class MobileAppAccessDeniedHandler extends AccessDeniedHandlerImpl {

    static Logger log = Logger.getLogger(MobileAppAccessDeniedHandler.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        LoginResponseDataDto loginResponseDataDto = new LoginResponseDataDto();
        BaseResponseDto<LoginResponseDataDto> dto = new BaseResponseDto<>(ReturnMessage.UNAUTHORIZED);
        dto.setData(loginResponseDataDto);
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.print(objectMapper.writeValueAsString(dto));
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}