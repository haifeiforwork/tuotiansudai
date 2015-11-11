package com.ttsd.api.service;

import com.ttsd.api.dto.BaseParam;

public interface MobileAppChannelService {
    boolean recordDeviceId(String type, String data, String channel, String subChannel);

    String obtainChannelBySource(BaseParam param);

    void sendInstallNotify(BaseParam param);
}
