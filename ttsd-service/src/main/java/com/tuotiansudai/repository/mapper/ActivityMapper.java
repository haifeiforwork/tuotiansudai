package com.tuotiansudai.repository.mapper;

import com.tuotiansudai.repository.model.ActivityModel;
import com.tuotiansudai.repository.model.ActivityStatus;
import com.tuotiansudai.repository.model.Source;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ActivityMapper {
    void create(ActivityModel activityModel);

    void update(ActivityModel activityModel);

    ActivityModel findById(long id);

    List<ActivityModel> findActivities(@Param(value = "activatedStartTime") Date activatedStartTime,
                                       @Param(value = "activatedEndTime") Date activatedEndTime,
                                       @Param(value = "activityStatus") ActivityStatus activityStatus,
                                       @Param(value = "source") Source source);
}
