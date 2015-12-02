package com.tuotiansudai.service;

import com.tuotiansudai.dto.BasePaginationDataDto;
import com.tuotiansudai.repository.model.ReferrerManageView;
import com.tuotiansudai.repository.model.Role;
import com.tuotiansudai.repository.model.Source;

import java.util.Date;
import java.util.List;

public interface ReferrerManageService {

    List<ReferrerManageView> findReferrerManage(String referrerLoginName,String investLoginName,Date investStartTime,Date investEndTime,Integer level,Date rewardStartTime,Date rewardEndTime,Role role,Source source,int currentPageNo,int pageSize);

    int findReferrerManageCount(String referrerLoginName,String investLoginName,Date investStartTime,Date investEndTime,Integer level,Date rewardStartTime,Date rewardEndTime,Role role,Source source);

    BasePaginationDataDto findReferrerRelationList(String referrerLoginName, String loginName, Date startTime, Date endTime, int index, int pageSize);

    BasePaginationDataDto findReferInvestList(String referrerLoginName, String loginName, Date startTime, Date endTime, int index, int pageSize);

    String getUserRewardDisplayLevel(String loginName);

    String findReferInvestTotalAmount(String referrerLoginName, String loginName, Date startTime, Date endTime);
}
