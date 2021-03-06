package com.tuotiansudai.repository.mapper;

import com.tuotiansudai.repository.model.PayrollModel;
import com.tuotiansudai.repository.model.PayrollStatusType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PayrollMapper {

    void create(PayrollModel payrollModel);

    void update(PayrollModel payrollModel);

    List<PayrollModel> findPayroll(@Param(value = "createStartTime") Date createStartTime,
                                   @Param(value = "createEndTime") Date createEndTime,
                                   @Param(value = "sendStartTime") Date sendStartTime,
                                   @Param(value = "sendEndTime") Date sendEndTime,
                                   @Param(value = "amountMin") Long amountMin,
                                   @Param(value = "amountMax") Long amountMax,
                                   @Param(value = "payrollStatusType") PayrollStatusType payrollStatusType,
                                   @Param(value = "title") String title);

    PayrollModel findById(@Param(value = "id") long id);

    int updateStatus(
            @Param(value = "id") long id,
            @Param(value = "status") PayrollStatusType status);

}
