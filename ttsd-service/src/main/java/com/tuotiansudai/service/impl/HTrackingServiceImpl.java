package com.tuotiansudai.service.impl;

import com.google.common.base.Strings;
import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.service.HTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HTrackingServiceImpl implements HTrackingService {

    @Autowired
    private HTrackingUserMapper hTrackingUserMapper;

    private final static String HTRACKING_CHANNEL = "htracking";

    @Override
    public BaseDto save(String mobile, String deviceId) {
        if (Strings.isNullOrEmpty(mobile) || Strings.isNullOrEmpty(deviceId)) {
            return new BaseDto(false);
        }

        if (hTrackingUserMapper.findByMobileAndDeviceId(mobile, deviceId) == null) {
            hTrackingUserMapper.create(new HTrackingUserModel(mobile, deviceId));
        }

        return new BaseDto();
    }
}
