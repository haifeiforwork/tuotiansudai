package com.tuotiansudai.api.service;

import com.tuotiansudai.api.dto.BaseResponseDto;
import com.tuotiansudai.api.dto.LoanListRequestDto;
import com.tuotiansudai.api.dto.LoanResponseDataDto;
import com.tuotiansudai.repository.model.LoanModel;

import java.util.List;

public interface MobileAppLoanListService {
    BaseResponseDto generateLoanList(LoanListRequestDto investListRequestDt);

    List<LoanResponseDataDto> convertLoanDto(List<LoanModel> loanList);
}
