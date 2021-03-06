package com.tuotiansudai.api.service.v1_0;

import com.tuotiansudai.api.dto.v1_0.AdvertisementResponseDataDto;
import com.tuotiansudai.api.dto.v1_0.BaseParamDto;
import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;

public interface MobileAppAdvertisementService {
    BaseResponseDto<AdvertisementResponseDataDto> generateAdvertisement(BaseParamDto requestDto);
}
