package com.chenrj.zhihu.async;

import java.util.List;

/**
 * @author rjchen
 * @date 2020/10/11
 */

public interface EventHandler {

    /**
     *  事件的处理过程
     */
    void doHandle(EventModel eventModel);

    /**
     *  用于注册自己, 看自己是处理哪一个Event的
     * @return
     */
    List<EventType> getSupportEventType();
}
