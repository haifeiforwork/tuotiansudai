package com.tuotiansudai.console.activity.service;

import com.tuotiansudai.activity.repository.mapper.ActivityInvestMapper;
import com.tuotiansudai.activity.repository.mapper.SuperScholarRewardMapper;
import com.tuotiansudai.activity.repository.model.ActivityCategory;
import com.tuotiansudai.activity.repository.model.ActivityInvestModel;
import com.tuotiansudai.activity.repository.model.SuperScholarRewardModel;
import com.tuotiansudai.activity.repository.model.SuperScholarRewardView;
import com.tuotiansudai.dto.BasePaginationDataDto;
import com.tuotiansudai.util.AmountConverter;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityConsoleSuperScholarService {

    @Autowired
    private ActivityInvestMapper activityInvestMapper;

    @Autowired
    private SuperScholarRewardMapper superScholarRewardMapper;

    @Value(value = "#{new java.text.SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").parse(\"${activity.super.scholar.startTime}\")}")
    private Date activityStartTime;

    @Value(value = "#{new java.text.SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").parse(\"${activity.super.scholar.endTime}\")}")
    private Date activityEndTime;

    public BasePaginationDataDto<SuperScholarRewardView> list(String keyWord, int index, int pageSize) {
        List<ActivityInvestModel> activityInvestModels = activityInvestMapper.findByUserNameOrMobile(keyWord,
                ActivityCategory.SUPER_SCHOLAR_ACTIVITY.name(),
                activityStartTime,
                new Date().after(activityEndTime) ? activityEndTime : DateTime.now().withTimeAtStartOfDay().minusMillis(1).toDate());
        List<SuperScholarRewardView> list = activityInvestModels.stream()
                .filter(model -> superScholarRewardMapper.findByLoginNameAndAnswerTime(model.getLoginName(), model.getCreatedTime()) != null)
                .map(model -> {
                    SuperScholarRewardModel superScholarRewardModel = superScholarRewardMapper.findByLoginNameAndAnswerTime(model.getLoginName(), model.getCreatedTime());
                    double rewardRate = superScholarRewardModel.getRewardRate();
                    return new SuperScholarRewardView(
                            model.getUserName(),
                            model.getMobile(),
                            AmountConverter.convertCentToString(model.getInvestAmount()),
                            AmountConverter.convertCentToString(model.getAnnualizedAmount()),
                            String.format("%.1f", rewardRate * 100) + "%",
                            AmountConverter.convertCentToString((long) (model.getAnnualizedAmount() * rewardRate)),
                            model.getCreatedTime());

                }).collect(Collectors.toList());
        int count = list.size();
        int endIndex = pageSize * index;
        int startIndex = (index - 1) * pageSize;
        if (count <= endIndex) {
            endIndex = count;
        }
        if (count < startIndex) {
            startIndex = count;
        }
        return new BasePaginationDataDto(index, pageSize, count, list.subList(startIndex, endIndex));
    }
}
