package com.chenrj.zhihu.controller;

import com.chenrj.zhihu.async.EventModel;
import com.chenrj.zhihu.async.EventProducer;
import com.chenrj.zhihu.async.EventType;
import com.chenrj.zhihu.model.EntityType;
import com.chenrj.zhihu.model.HostHolder;
import com.chenrj.zhihu.model.Question;
import com.chenrj.zhihu.service.FollowServeice;
import com.chenrj.zhihu.service.QuestionService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author rjchen
 * @date 2020/10/13
 */

public class FollowController {

    @Autowired
    FollowServeice followService;

    @Autowired
    QuestionService questionService;

    @Autowired
    EventProducer eventProducer;

    @Resource
    HostHolder currentUser;

    @RequestMapping(path = {"/followUser"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String followUser(@RequestParam("userId") int userId) {
        boolean ret = followService.follow(EntityType.USER.getCode(), userId);
        // 返回关注的人数
        return ret ? "0" : String.valueOf(followService.getFolloweeCount(currentUser.getUser().getId(), EntityType.USER.getCode()));
    }

    @RequestMapping(path = {"/unfollowUser"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String unfollowUser(@RequestParam("userId") int userId) {
        boolean ret = followService.unfollow(EntityType.USER.getCode(), userId);
        // 返回关注的人数
        if (ret) {
            EventModel eventModel = new EventModel();
            eventModel.setEventType(EventType.FOLLOW);
            eventModel.setActionId(currentUser.getUser().getId());
            eventModel.setEntityType(EntityType.USER.getCode());
            eventModel.setEntityId(userId);
            eventProducer.fireEvent(eventModel);
        }
        return ret ? "0" : String.valueOf(followService.getFolloweeCount(currentUser.getUser().getId(), EntityType.USER.getCode()));
    }

    @RequestMapping(path = "/question/follow", method = RequestMethod.POST)
    @ResponseBody
    public String followQuestion(@RequestParam("questionId") int questionId) {
        Question question = questionService.getQuestionDatil(questionId);
        if (Objects.isNull(question)) {
            return "没有此问题";
        }
        boolean hasFollowed = followService.follow(EntityType.QUESTION.getCode(), questionId);

        if (hasFollowed) {
            EventModel eventModel = new EventModel();
            eventModel.setEventType(EventType.FOLLOW);
            eventModel.setActionId(currentUser.getUser().getId());
            eventModel.setEntityType(EntityType.QUESTION.getCode());
            eventModel.setEntityId(questionId);
            eventModel.setEntityOwnerId(question.getUserId());
            eventProducer.fireEvent(eventModel);
        }
        return hasFollowed ? String.valueOf(0) : String.valueOf(followService.getFolloweeCount(currentUser.getUser().getId(), EntityType.QUESTION.getCode()));
    }

    @RequestMapping(path = {"/unfollowQuestion"}, method = {RequestMethod.POST})
    @ResponseBody
    public String unfollowQuestion(@RequestParam("questionId") int questionId) {

        Question q = questionService.getQuestionDatil(questionId);
        if (q == null) {
            return "问题不存在";
        }

        boolean ret = followService.unfollow(EntityType.QUESTION.getCode(), questionId);

        if (ret) {
            EventModel eventModel = new EventModel();
            eventModel.setEventType(EventType.UNFOLLOW);
            eventModel.setActionId(currentUser.getUser().getId());
            eventModel.setEntityType(EntityType.QUESTION.getCode());
            eventModel.setEntityId(questionId);
            eventModel.setEntityOwnerId(q.getUserId());
            eventProducer.fireEvent(eventModel);
        }

        Map<String, Object> info = new HashMap<>();
        info.put("id", currentUser.getUser().getId());
        info.put("count", followService.getFollowerCount(EntityType.QUESTION.getCode(), questionId));
        return new Gson().toJson(info);
    }


}
