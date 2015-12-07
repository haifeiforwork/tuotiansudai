package com.tuotiansudai.service.impl;

import com.tuotiansudai.dto.Granularity;
import com.tuotiansudai.repository.mapper.BusinessIntelligenceMapper;
import com.tuotiansudai.repository.model.KeyValueModel;
import com.tuotiansudai.service.BusinessIntelligenceService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BusinessIntelligenceServiceImpl implements BusinessIntelligenceService {

    @Autowired
    private BusinessIntelligenceMapper businessIntelligenceMapper;

    @Override
    public List<KeyValueModel> queryUserRegisterTrend(Granularity granularity, Date startTime, Date endTime) {
        Date queryStartTime = new DateTime(startTime).withTimeAtStartOfDay().toDate();
        Date queryEndTime = new DateTime(endTime).plusDays(1).withTimeAtStartOfDay().toDate();
        switch (granularity) {
            case Daily:
                return businessIntelligenceMapper.queryUserRegisterTrendDaily(queryStartTime, queryEndTime);
            case Weekly:
                return businessIntelligenceMapper.queryUserRegisterTrendWeekly(queryStartTime, queryEndTime);
            case Monthly:
                return businessIntelligenceMapper.queryUserRegisterTrendMonthly(queryStartTime, queryEndTime);
        }
        return new ArrayList<>();
    }

    @Override
    public List<KeyValueModel> queryUserRechargeTrend(Granularity granularity, Date startTime, Date endTime, String province) {
        Date queryStartTime = new DateTime(startTime).withTimeAtStartOfDay().toDate();
        Date queryEndTime = new DateTime(endTime).plusDays(1).withTimeAtStartOfDay().toDate();
        return businessIntelligenceMapper.queryUserRechargeTrend(queryStartTime, queryEndTime, granularity, province);
    }

    @Override
    public List<KeyValueModel> queryUserWithdrawTrend(Granularity granularity, Date startTime, Date endTime, String province) {
        Date queryStartTime = new DateTime(startTime).withTimeAtStartOfDay().toDate();
        Date queryEndTime = new DateTime(endTime).plusDays(1).withTimeAtStartOfDay().toDate();
        return businessIntelligenceMapper.queryUserWithdrawTrend(queryStartTime, queryEndTime, granularity, province);
    }

    @Override
    public List<KeyValueModel> queryUserAccountTrend(Granularity granularity, Date startTime, Date endTime, String province) {
        Date queryStartTime = new DateTime(startTime).withTimeAtStartOfDay().toDate();
        Date queryEndTime = new DateTime(endTime).plusDays(1).withTimeAtStartOfDay().toDate();
        return businessIntelligenceMapper.queryUserAccountTrend(queryStartTime, queryEndTime, granularity, province);
    }

}
