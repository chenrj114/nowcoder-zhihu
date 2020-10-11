package com.chenrj.zhihu.service;

import com.chenrj.zhihu.util.JedisAdapter;
import com.chenrj.zhihu.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rjchen
 * @date 2020/10/8
 */

/**
 *  点赞功能：存在redis中， 键为被点赞的客体，值为进行点赞的主体(用户)
 *  所以选择的存储结构应该为链表。
 */
@Service
public class LikeService {

    @Autowired
    JedisAdapter jedisAdapter;

    /**
     *  获取某个东西被点赞的数量
     * @param entityType
     * @param entityId
     * @return
     */
    public long getLikeCount(int entityType, int entityId) {
        String key = RedisKeyUtil.getLikeKey(entityType, entityId);
        return jedisAdapter.scard(key);
    }

    /**
     *  点赞功能  如果点过踩 应该把踩去掉
     * @param userId 进行 点赞 操作的用户
     * @param entityType  点赞操作的客体
     * @param entityId    点赞操作的客体
     * @return 点赞的总数量 因为此时可能有多个人同时点赞， 不能简单的加1
     */
    public long like(int userId, int entityType, int entityId) {
        String key = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.sadd(key, String.valueOf(userId));
        // 赞点完 要把踩 去掉
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.srem(disLikeKey, String.valueOf(userId));
        return jedisAdapter.scard(key);
    }

    /**
     *  点赞功能
     * @param userId 进行 点赞 操作的用户
     * @param entityType  点赞操作的客体
     * @param entityId    点赞操作的客体
     * @return 点赞的总数量 因为此时可能有多个人同时点赞， 不能简单的加1
     */
    public long disLike(int userId, int entityType, int entityId) {
        String key = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.sadd(key, String.valueOf(userId));
        // 踩点完 要把赞 去掉
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.srem(likeKey, String.valueOf(userId));
        return jedisAdapter.scard(key);
    }
}
