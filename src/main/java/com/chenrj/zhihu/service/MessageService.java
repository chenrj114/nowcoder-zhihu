package com.chenrj.zhihu.service;

import com.chenrj.zhihu.dao.MessageMapper;
import com.chenrj.zhihu.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author rjchen
 * @date 2020/9/21
 */
@Slf4j
@Service
public class MessageService {

    @Autowired
    MessageMapper messageMapper;

    public List<Message> getMessageByUserId(int userId) {
        log.info("userId = {}", userId);
        return messageMapper.queryConversationByUserId(userId);
    }

    public void sendMessage(int fromId, int toId, String text) {
        Message message = new Message();
        message.setFromId(fromId);
        message.setToId(toId);
        message.setContent(text);
        message.setCreatedate(new Date());
        message.setConversationId(generateConversationId(fromId, toId));
        message.setHasRead(false);
        messageMapper.insert(message);
    }

    public String generateConversationId(int fromId, int toId) {
        return fromId < toId ? fromId + "-" + toId : toId + "-" + fromId;
    }
}
