package com.tuotiansudai.service;

import com.tuotiansudai.dto.OperationDataDto;
import com.tuotiansudai.repository.model.InvestDataView;

import java.util.Date;
import java.util.List;

/**
 * Created by huoxuanbo on 16/5/9.
 */
public interface OperationDataService {
    OperationDataDto getOperationDataFromRedis(Date endDate);

    List<InvestDataView> getInvestDetail(Date endDate);
}