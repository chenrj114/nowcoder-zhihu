package com.chenrj.zhihu.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author rjchen
 * @date 2020/10/8
 */

@Component
public class JedisAdapter {

    private final JedisPool jedisPool;

    public JedisAdapter() {
        jedisPool = new JedisPool();
    }

    public void sadd(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        jedis.sadd(key, value);
    }

    public void srem(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        jedis.srem(key, value);
    }

    public Long scard(String key) {
        Jedis jedis = jedisPool.getResource();
        return jedis.scard(key);
    }

    public void lpush(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        jedis.lpush(key, value);
    }

    public List<String> brpop(int timeout, String key) {
        Jedis jedis = jedisPool.getResource();
        return jedis.brpop(timeout, key);
    }
}
