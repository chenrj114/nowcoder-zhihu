package com.chenrj.zhihu.async.handler;

import com.chenrj.zhihu.async.EventHandler;
import com.chenrj.zhihu.async.EventModel;
import com.chenrj.zhihu.async.EventType;
import com.chenrj.zhihu.model.EntityType;
import com.chenrj.zhihu.model.Feed;
import com.chenrj.zhihu.model.Question;
import com.chenrj.zhihu.model.User;
import com.chenrj.zhihu.service.FeedService;
import com.chenrj.zhihu.service.QuestionService;
import com.chenrj.zhihu.service.UserService;
import com.chenrj.zhihu.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author rjchen
 * @date 2020/11/25
 */

public class FeedHandler implements EventHandler {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    FeedService feedService;

    @Override
    public void doHandle(EventModel eventModel) {
        Feed feed = new Feed().setCreatedDate(new Date())
                            .setType(eventModel.getEventType().getValue())
                            .setUserId(eventModel.getActionId());
        String data = buildData(eventModel);
        feed.setData(data);
        try {
            feedService.addFeed(feed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  支持类型： 关注某个问题, 评论某个问题
     */
    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.COMMENT, EventType.FOLLOW);
    }

    private String buildData(EventModel eventModel) {
        Map<String, String> map = new HashMap<>();
        User actor = userService.getUser(eventModel.getActionId());
        if (actor == null) {
            return null;
        }
        map.put("userId", String.valueOf(actor.getId()));
        map.put("userHead", actor.getHeadUrl());
        map.put("userName", actor.getName());

        if (eventModel.getEventType() == EventType.COMMENT ||
                    (eventModel.getEventType() == EventType.FOLLOW
                             && eventModel.getEntityType() == EntityType.QUESTION.getCode())) {
            Question question = questionService.getQuestionDatil(eventModel.getEntityId());
            if (question == null) {
                return null;
            }
            map.put("questionId", String.valueOf(question.getId()));
            map.put("questionTitle", question.getTitle());
            return JsonUtil.toJson(map);
        }
        return null;
    }
}
