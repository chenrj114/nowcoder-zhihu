package com.chenrj.zhihu.controller;

import com.chenrj.zhihu.async.EventModel;
import com.chenrj.zhihu.async.EventProducer;
import com.chenrj.zhihu.model.HostHolder;
import com.chenrj.zhihu.service.CommentService;
import com.chenrj.zhihu.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author rjchen
 * @date 2020/10/11
 */
@Slf4j
public class LikeController {

    @Resource
    HostHolder currentUser;

    @Autowired
    LikeService likeService;

    @Autowired
    CommentService commentService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = "/like/", method = RequestMethod.POST)
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        int actionId = currentUser.getUser().getId();
        EventModel eventModel = new EventModel();
        eventModel.setActionId(actionId);
        eventModel.setEntityId(commentId);
        eventModel.setEntityType(2);
        eventModel.setEntityOwnerId(commentService.getCommentByCommentId(commentId).getUserId());
        boolean hasFired = eventProducer.fireEvent(eventModel);
        if (hasFired) {
            log.info("点赞成功");
        } else {
            log.info("点赞失败, 请重新点赞");
        }
        long likeCount = likeService.like(eventModel.getActionId(), eventModel.getEntityType(), eventModel.getEntityId());
        return String.valueOf(likeCount);
    }
}
