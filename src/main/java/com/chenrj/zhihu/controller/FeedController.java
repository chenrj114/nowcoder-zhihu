package com.chenrj.zhihu.controller;

import com.chenrj.zhihu.model.EntityType;
import com.chenrj.zhihu.model.Feed;
import com.chenrj.zhihu.model.HostHolder;
import com.chenrj.zhihu.service.FeedService;
import com.chenrj.zhihu.service.FollowServeice;
import com.chenrj.zhihu.util.JedisAdapter;
import com.chenrj.zhihu.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rjchen
 * @date 2020/11/25
 */

@Controller
public class FeedController {

    @Resource
    HostHolder currentUser;

    @Autowired
    FeedService feedService;

    @Autowired
    FollowServeice followServeice;

    @Autowired
    JedisAdapter jedisAdapter;

    @RequestMapping(path = "/pullfeeds", method = RequestMethod.GET)
    private String getPullFeed(Model model) {
        int localUserId = currentUser.getUser().getId();
        List<Integer> followees = new ArrayList<>();
        if (localUserId == 0) {

        } else {
            followees = followServeice.getFollowees(localUserId, EntityType.USER.getCode(), 10);
        }
        List<Feed> feeds = feedService.getUserFeeds(Integer.MAX_VALUE, followees, 10);
        model.addAttribute("feeds", feeds);
        return "feeds";
    }

    @RequestMapping(path = "/pushfeeds", method = RequestMethod.GET)
    private String getPushFeed(Model model) {
        int localUserId = currentUser.getUser().getId();
        List<Integer> followees = new ArrayList<>();
        if (localUserId == 0) {

        }
        List<String> feedIds = jedisAdapter.lrange(RedisKeyUtil.getTimeLineKey(localUserId), 0, 10);
        List<Feed> feeds = new ArrayList<>();
        for (String feedId : feedIds) {
            feeds.add(feedService.getById(Integer.parseInt(feedId)));
        }
        model.addAttribute("feeds", feeds);
        return "feeds";
    }
}
