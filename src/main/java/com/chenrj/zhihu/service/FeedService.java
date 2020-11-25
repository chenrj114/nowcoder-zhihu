package com.chenrj.zhihu.service;

import com.chenrj.zhihu.dao.FeedMapper;
import com.chenrj.zhihu.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rjchen
 * @date 2020/11/25
 */
@Service
public class FeedService {

    @Autowired
    FeedMapper feedMapper;

    /**
     *  拉模式
     */
    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count) {
        return feedMapper.selectUserFeeds(userIds, maxId, count);
    }

    public boolean addFeed(Feed feed) {
        feedMapper.insert(feed);
        return feed.getId() > 0;
    }

    /**
     *  推模式
     */
    public Feed getById(int id) {
        return feedMapper.selectByPrimaryKey(id);
    }
}
