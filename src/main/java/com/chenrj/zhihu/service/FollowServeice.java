package com.chenrj.zhihu.service;

import com.chenrj.zhihu.model.HostHolder;
import com.chenrj.zhihu.util.JedisAdapter;
import com.chenrj.zhihu.util.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author rjchen
 * @date 2020/10/13
 */
@Service
@Slf4j
public class FollowServeice {

    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    HostHolder currentUser;

    /**
     * 用户关注了某个实体,可以关注问题,关注用户,关注评论等任何实体
     * 在用户的关注列表上加上实体的类型和ID
     * 在实体的被关注列表上加上用户
     * 就是这样两件事情，并且这两件事情是一个事务必须两个同时发生，用redis.multi可将两个动作包装为一个事务，然后同时执行
     * @param entityType
     * @param entityId
     * @return
     */
    public boolean follow(int entityType, int entityId) {
        int userId = currentUser.getUser().getId();
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        String followeeValue = entityType + "+" + entityId;
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        try {
            Transaction transaction = jedisAdapter.multi(jedisAdapter.getJedis());
            Date date = new Date();
            transaction.zadd(followeeKey, date.getTime(), followeeKey);
            transaction.zadd(followerKey, date.getTime(), String.valueOf(userId));
            List<Object> execList = jedisAdapter.exec(transaction, jedisAdapter.getJedis());
            return execList.size() == 2 && (Long)execList.get(0) > 0 && (Long)execList.get(1) > 0;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            log.error("关注失败");
            return false;
        }
    }

    /**
     * 取消关注
     * @param entityType
     * @param entityId
     * @return
     */
    public boolean unfollow(int entityType, int entityId) {
        int userId = currentUser.getUser().getId();
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        Date date = new Date();
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);
        // 实体的粉丝增加当前用户
        tx.zrem(followerKey, String.valueOf(userId));
        // 当前用户对这类实体关注-1
        tx.zrem(followeeKey, String.valueOf(entityId));
        List<Object> ret = jedisAdapter.exec(tx, jedis);
        return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
    }

    /**
     * 返回被关注的 score值最大的N个用户
     */
    public List<Integer> getFollowers(int entityType, int entityId, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey, 0, count));
    }

    /**
     * 返回被关注的 score值最大的N个用户(翻页)
     */
    public List<Integer> getFollowers(int entityType, int entityId, int offset, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey, offset, offset+count));
    }

    /**
     *  返回某个用户关注的N个列表
     */
    public List<Integer> getFollowees(int userId, int entityType, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, 0, count));
    }

    /**
     *  返回某个用户关注的N个列表 (翻页)
     */
    public List<Integer> getFollowees(int userId, int entityType, int offset, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, offset, offset+count));
    }

    /**
     *  返回关注这个实体的人的总数
     */
    public long getFollowerCount(int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return jedisAdapter.zcard(followerKey);
    }

    /**
     *  返回某个用户关注的实体的总数
     */
    public long getFolloweeCount(int userId, int entityTpye) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityTpye);
        return jedisAdapter.zcard(followeeKey);
    }

    private List<Integer> getIdsFromSet(Set<String> idset) {
        List<Integer> ids = new ArrayList<>();
        for (String str : idset) {
            ids.add(Integer.parseInt(str));
        }
        return ids;
    }

    /**
     *  判断用户是否关注了某个实体
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public boolean isFollower(int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return jedisAdapter.zscore(followerKey, String.valueOf(userId)) != null;
    }

}
