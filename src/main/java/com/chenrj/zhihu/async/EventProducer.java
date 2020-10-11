package com.chenrj.zhihu.async;

import com.chenrj.zhihu.util.JedisAdapter;
import com.chenrj.zhihu.util.RedisKeyUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rjchen
 * @date 2020/10/11
 */
@Service
@Slf4j
public class EventProducer {

    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel) {
        try {
            String eventModelString = new Gson().toJson(eventModel);
            String eventKey = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(eventKey, eventModelString);
            return true;

         /**
         *  此处需要把异常Catch , 否则, 一旦某个发送失败, 异常会一直向上抛
          *  此处应是捕捉异常的最佳位置
         */
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return false;
        }

    }
}
