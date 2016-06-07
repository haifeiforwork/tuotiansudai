package com.tuotiansudai.membership.service;

import com.tuotiansudai.membership.repository.model.MembershipModel;

import java.util.List;

public interface UserMembershipService {

    MembershipModel getMembershipByLevel(int level);

    int getProgressBarPercent(String loginName);

    List<String> getPrivilege(String loginName);

    String[] showDisable(String loginName);

}
