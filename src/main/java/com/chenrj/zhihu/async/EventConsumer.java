package com.chenrj.zhihu.async;

import com.chenrj.zhihu.util.JedisAdapter;
import com.chenrj.zhihu.util.RedisKeyUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rjchen
 * @date 2020/10/11
 */
@Service
@Slf4j
public class EventConsumer implements
                // 实现InitializingBean, 可以在Bean初始化过后, 运行afterPropertiesSet()方法
                InitializingBean,
                // 实现ApplicationContextAware 可以在当前Bean 获取到 ApplicationContext 的引用
                ApplicationContextAware {


    // 根据每个EventType, 选择需要分发的Handler
    private final Map<EventType, List<EventHandler>> config = new HashMap<>();

    private ApplicationContext applicationContext;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        /**
         *  第一步 先建立 各种事件类型 和 相应的handle之间的映射关系
         */
        // 得到所有实现了EventHandle接口的引用
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        // 然后遍历他
        for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
            EventHandler handler = entry.getValue();
            List<EventType> supportEventType = handler.getSupportEventType();
            for (EventType eventType : supportEventType) {
                if (!config.containsKey(eventType)) {
                    config.put(eventType, new ArrayList<EventHandler>());
                }
                config.get(eventType).add(handler);
            }
        }

        new Thread(new ConsumerTask()).start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    class ConsumerTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                String key = RedisKeyUtil.getEventQueueKey();
                List<String> eventModelJson = jedisAdapter.brpop(0, key);
                for (String event : eventModelJson) {
                    if (event.equals(key)) {
                        continue;
                    }
                    EventModel eventModel = new Gson().fromJson(event, EventModel.class);
                    if (config.containsKey(eventModel.getEventType())) {
                        List<EventHandler> handlers = config.get(eventModel.getEventType());
                        for (EventHandler handler : handlers) {
                            handler.doHandle(eventModel);
                        }
                    } else {
                        log.info("EventType({}) is not illegal", eventModel.getEventType());
                    }
                }

            }
        }
    }
}
