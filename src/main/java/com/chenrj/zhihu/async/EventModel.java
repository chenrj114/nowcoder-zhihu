package com.chenrj.zhihu.async;

/**
 * @author rjchen
 * @date 2020/10/11
 */

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 *  事件(Event)的载体
 */
@Getter
@Setter
public class EventModel {

    /**
     *  事件类型
     */
    private EventType eventType;

    /**
     *  触发者ID, 也就是触发时间发生的主体
     *  例如某个用户对某个评论点了一个赞, 主体是用户
     */
    private int actionId;

    /**
     *  事件的客体
     *  客体通常是多种多样的, 可能是用户, 评论, 问题 等等
     */
    private int entityType;
    private int entityId;

    /**
     *  事件客体的拥有者
     *  比如 某个用户A 对评论C 点了一个赞 而此条评论是 B 评论的
     *  那么这里的 entityOwnerId 就是指B
     */
    private int entityOwnerId;

    /**
     *  拓展字段
     */
    private Map<String, String> extentds = new HashMap<>();
}
