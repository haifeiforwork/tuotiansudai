package com.tuotiansudai.api.service.v1_0.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.tuotiansudai.api.dto.v1_0.*;
import com.tuotiansudai.api.service.v1_0.MobileAppHelpCenterSearchService;
import com.tuotiansudai.repository.mapper.HelpCenterMapper;
import com.tuotiansudai.repository.model.HelpCenterModel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MobileAppHelpCenterSearchServiceImpl implements MobileAppHelpCenterSearchService {

    @Autowired
    private HelpCenterMapper helpCenterMapper;

    @Override
    public BaseResponseDto<HelpCenterSearchListResponseDataDto> getHelpCenterSearchResult(HelpCenterSearchRequestDto requestDto) {




        List<HelpCenterModel>  helpCenterModels =  helpCenterMapper.findAllHelpCenterByTitleOrCategoryOrHot(requestDto.getKeywords(), requestDto.getCategory() != null ? requestDto.getCategory().toUpperCase() : null, requestDto.getHot());

        List<HelpCenterSearchResponseDataDto> helpCenterSearchResponseDataDtoList = Lists.newArrayList();

        if(CollectionUtils.isNotEmpty(helpCenterModels)){
            helpCenterSearchResponseDataDtoList = Lists.transform(helpCenterModels, new Function<HelpCenterModel, HelpCenterSearchResponseDataDto>() {
                @Override
                public HelpCenterSearchResponseDataDto apply(HelpCenterModel helpCenterModel) {
                    return new HelpCenterSearchResponseDataDto(helpCenterModel);
                }
            });
        }

        HelpCenterSearchListResponseDataDto dataDto = new HelpCenterSearchListResponseDataDto();
        dataDto.setSearchList(helpCenterSearchResponseDataDtoList);
        BaseResponseDto<HelpCenterSearchListResponseDataDto> dto = new BaseResponseDto<>();
        dto.setCode(ReturnMessage.SUCCESS.getCode());
        dto.setMessage(ReturnMessage.SUCCESS.getMsg());
        dto.setData(dataDto);
        return dto;
    }
}
