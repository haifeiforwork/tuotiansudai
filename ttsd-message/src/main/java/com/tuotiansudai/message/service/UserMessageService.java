package com.tuotiansudai.message.service;


import com.tuotiansudai.dto.BasePaginationDataDto;
import com.tuotiansudai.message.dto.UserMessagePaginationItemDto;
import com.tuotiansudai.message.repository.model.MessageChannel;
import com.tuotiansudai.message.repository.model.UserMessageModel;

public interface UserMessageService {

    long getUnreadMessageCount(String loginName, MessageChannel messageChannel);

    BasePaginationDataDto<UserMessagePaginationItemDto> getUserMessages(String loginName, int index, int pageSize);

    UserMessageModel readMessage(long userMessageId);

    boolean readAll(String loginName, MessageChannel messageChannel);

    void generateUserMessages(String loginName, MessageChannel messageChannel);
}
