package com.chenrj.zhihu.async.handler;

import com.chenrj.zhihu.async.EventHandler;
import com.chenrj.zhihu.async.EventModel;
import com.chenrj.zhihu.async.EventType;
import com.chenrj.zhihu.model.MailDO;
import com.chenrj.zhihu.service.UserService;
import com.chenrj.zhihu.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author rjchen
 * @date 2020/10/13
 */
@Service
public class LoginExceptionHandler implements EventHandler {

    @Autowired
    MailService mailService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel eventModel) {
        MailDO mail = new MailDO().setEmail("332713110@qq.com").setTitle("异常登录通知");
        int userId = eventModel.getActionId();
        String username = userService.getUser(userId).getName();
        mail.addAttachment("username", username);
        mailService.sendTemplateMail(mail);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Collections.singletonList(EventType.LOGIN);
    }
}
