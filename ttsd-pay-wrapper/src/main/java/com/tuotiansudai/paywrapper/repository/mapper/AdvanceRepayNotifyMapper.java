package com.tuotiansudai.paywrapper.repository.mapper;

import com.tuotiansudai.paywrapper.repository.model.NotifyProcessStatus;
import com.tuotiansudai.paywrapper.repository.model.async.callback.AdvanceRepayNotifyRequestModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvanceRepayNotifyMapper extends BaseCallbackMapper {

    AdvanceRepayNotifyRequestModel findById(@Param(value = "id") long id);

    void updateStatus(@Param(value = "id") Long id, @Param(value = "status") NotifyProcessStatus status);
}
