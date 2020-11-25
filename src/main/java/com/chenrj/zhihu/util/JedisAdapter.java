package com.chenrj.zhihu.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

/**
 * @author rjchen
 * @date 2020/10/8
 */

@Component
@Slf4j
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

    public List<String> lrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public List<String> brpop(int timeout, String key) {
        Jedis jedis = jedisPool.getResource();
        return jedis.brpop(timeout, key);
    }

    public long zadd(String key, double score, String value) {
        Jedis jedis = jedisPool.getResource();
        return jedis.zadd(key, score, value);

    }

    public long zrem(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        return jedis.zrem(key, value);
    }

    public long zcard(String key) {
        Jedis jedis = jedisPool.getResource();
        return jedis.zcard(key);
    }

    public Double zscore(String key, String member) {
        Jedis jedis = jedisPool.getResource();
        return jedis.zscore(key, member);
    }

    /**
     * 返回有序集中指定区间内的成员，通过索引，分数从高到底
     */
    public Set<String> zrevrange(String key, int start, int end) {
        Jedis jedis = jedisPool.getResource();
        return jedis.zrevrange(key, start, end);
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public Transaction multi(Jedis jedis) {
        try {
            return jedis.multi();
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
        }
        return null;
    }

    public List<Object> exec(Transaction tx, Jedis jedis) {
        try {
            return tx.exec();
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
            tx.discard();
        } finally {
            if (tx != null) {
                tx.close();
            }
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }
}
