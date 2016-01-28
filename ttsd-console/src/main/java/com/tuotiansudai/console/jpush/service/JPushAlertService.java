package com.tuotiansudai.console.jpush.service;


import com.tuotiansudai.console.jpush.dto.JPushAlertDto;
import com.tuotiansudai.console.jpush.repository.model.JPushAlertModel;
import com.tuotiansudai.console.jpush.repository.model.PushStatus;
import com.tuotiansudai.console.jpush.repository.model.PushType;

import java.util.List;

public interface JPushAlertService {
    void buildJPushAlert(String loginName,JPushAlertDto jPushAlertDto);

    int findPushTypeCount(PushType pushType);

    int findPushAlertCount(String name,boolean isAutomatic);

    List<JPushAlertModel> findPushAlerts(int index,int pageSize,String name,boolean isAutomatic);

    JPushAlertModel findJPushAlertModelById(long id);

    void send(String loginName,long id);

    void changeJPushAlertStatus(long id,PushStatus status,String loginName);
}
