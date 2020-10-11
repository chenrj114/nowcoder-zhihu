package com.chenrj.zhihu.async.handler;

import com.chenrj.zhihu.async.EventHandler;
import com.chenrj.zhihu.async.EventModel;
import com.chenrj.zhihu.async.EventType;
import com.chenrj.zhihu.service.LikeService;
import com.chenrj.zhihu.service.MessageService;
import com.chenrj.zhihu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author rjchen
 * @date 2020/10/11
 */
@Service
@Slf4j
public class Likehandler implements EventHandler {

    @Autowired
    LikeService likeService;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel eventModel) {
        String name = userService.getUser(eventModel.getActionId()).getName();
        String notifyContent = String.format("用户(%1$s) 在 %2$tF %2$tT, 赞了您", name, new Date());
        messageService.sendMessage(eventModel.getActionId(), eventModel.getEntityOwnerId(), notifyContent);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Collections.singletonList(EventType.LIKE);
    }
}
