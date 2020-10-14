package com.chenrj.zhihu.async.handler;

import com.chenrj.zhihu.async.EventHandler;
import com.chenrj.zhihu.async.EventModel;
import com.chenrj.zhihu.async.EventType;
import com.chenrj.zhihu.model.MailDO;
import com.chenrj.zhihu.service.UserService;
import com.chenrj.zhihu.service.mail.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * @author rjchen
 * @date 2020/10/14
 */

public class FollowHandler implements EventHandler {

    @Autowired
    UserService userService;

    @Autowired
    MailServiceImpl mailService;

    @Override
    public void doHandle(EventModel eventModel) {
        MailDO mail = new MailDO().setEmail("332713110@qq.com").setTitle("关注通知");
        int userId = eventModel.getActionId();
        String username = userService.getUser(userId).getName();

        int entityOwnerId = eventModel.getEntityOwnerId();
        String entityOwnerUsername = userService.getUser(entityOwnerId).getName();
        mail.addAttachment("follower", username);
        mail.addAttachment("followee", entityOwnerUsername);
        mailService.sendTemplateMail("mail/follow", mail);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Collections.singletonList(EventType.FOLLOW);
    }
}
