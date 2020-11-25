package com.chenrj.zhihu.util;

/**
 * @author rjchen
 * @date 2020/10/8
 */

public class RedisKeyUtil {

    private static final String SPLIT = "-";
    private static final String BIZ_LIKE = "LIKE";
    private static final String BIZ_DISLIKE = "DISLIKE";
    // 粉丝
    private static final String BIZ_FOLLOWER = "FOLLOWER";
    // 关注的人
    private static final String BIZ_FOLLOWEE = "FOLLOWEE";

    private static final String BIZ_EVENT_QUEUE = "BIZ_EVENT_QUEUE";

    private static final String BIZ_TIME_LINE = "TIMELINE";

    public static String getLikeKey(int entityType, int entityId) {
        return BIZ_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getDisLikeKey(int entityType, int entityId) {
        return BIZ_DISLIKE + SPLIT + entityType + SPLIT + entityId;
    }

    /**
     * 获取某个用户 所有的粉丝 的业务Key
     * @param entityId 某个实体的ID （别人关注了他）
     * @return key
     */
    public static String getFollowerKey(int entityType, int entityId) {
        return BIZ_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }

    /**
     * 获取某个用户 所有的关注的人 的业务Key
     * @param userId 某个用户的ID （他去关注别人）
     * @return key
     */
    public static String getFolloweeKey(int userId, int entityType) {
        return BIZ_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }


    public static String getEventQueueKey() {
        return BIZ_EVENT_QUEUE;
    }

    public static String getTimeLineKey(int userId) {
        return BIZ_TIME_LINE + SPLIT + userId;
    }

}
