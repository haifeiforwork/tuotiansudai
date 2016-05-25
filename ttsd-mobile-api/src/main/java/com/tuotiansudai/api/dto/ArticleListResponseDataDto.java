package com.tuotiansudai.api.dto;

import java.util.List;

public class ArticleListResponseDataDto extends BaseResponseDataDto {
    private Integer index;
    private Integer pageSize;
    private Integer totalCount;
    private List<ArticleResponseDataDto> articleList;

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

    public List<ArticleResponseDataDto> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<ArticleResponseDataDto> articleList) {
        this.articleList = articleList;
    }
}

