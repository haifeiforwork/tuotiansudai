package com.tuotiansudai.api.dto.v1_0;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ReferrerListResponseDataDto extends BaseResponseDataDto{
    private Integer index;
    private Integer pageSize;
    private Integer totalCount;

    @ApiModelProperty(value = "充值记录列表", example = "list")
    private List<ReferrerResponseDataDto> referrerList;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<ReferrerResponseDataDto> getReferrerList() {
        return referrerList;
    }

    public void setReferrerList(List<ReferrerResponseDataDto> referrerList) {
        this.referrerList = referrerList;
    }
}
