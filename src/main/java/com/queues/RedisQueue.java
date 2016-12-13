package com.queues;

import com.alibaba.fastjson.JSON;
import com.entity.Request;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

/**
 * 
 * @ClassName RedisQueue
 * @Description TODO 用于存储待爬取的图片url
 * @author TURUI
 * @Date 2016年12月7日 上午9:42:27
 * @version 1.0.0
 */
@SuppressWarnings("deprecation")
public class RedisQueue {
    private static final String host = "127.0.0.1";
    private static final int port = 6379;
    private static final String password = "";
    private static final String quueName = "pixiv:url:todo:list";
    private static JedisPool wpool;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    //初始化redis连接
    public static void connect() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(500);
        config.setMaxIdle(200);
        config.setMinIdle(100);
        config.setMaxWaitMillis(1000 * 100);
        if (StringUtils.isNotBlank(password)) {
            wpool = new JedisPool(config, host, port, 0, password);
        } else {
            wpool = new JedisPool(config, host, port, 0);
        }
    }
    
    public void destroy() {
        if (wpool != null) {
            wpool.destroy();
            wpool = null;
        }
    }
    
    //获取jedis对象
    private synchronized Jedis getJedis() {
        return wpool.getResource();
    }
    
    //归还jedis对象
    private synchronized void returnJedis(JedisPool pool,Jedis jedis){
        pool.returnResource(jedis);
    }
    

    public Request bPop() {
        Jedis jedis = null;
        Request request = null;
        jedis = getJedis();
        if (jedis != null) {
            try {
                List<String> res = jedis.brpop(0, quueName);
                request = JSON.parseObject(res.get(1), Request.class);
            } catch (Exception e) {
                LOGGER.warn(e.getMessage());
            } finally {
                if (jedis != null) {
                    returnJedis(wpool,jedis);
                }
            }
        }
        return request;
    }

    public boolean push(Request req) {
        Jedis jedis = null;
        boolean res = false;
        jedis = getJedis();
        if (jedis != null) {
            try {
                res = jedis.lpush(quueName, JSON.toJSONString(req)) > 0;
            } catch (Exception e) {
                LOGGER.warn(e.getMessage());
            } finally {
                if (jedis != null) {
                    returnJedis(wpool,jedis);
                    
                }
            }
        }
        return res;
    }

    public long len() {
        long len = 0;
        Jedis jedis = null;
        jedis = getJedis();
        if (jedis != null) {
            try {
                len = jedis.llen(quueName);
            } catch (Exception e) {
                LOGGER.warn(e.getMessage());
            } finally {
                if (jedis != null) {
                    returnJedis(wpool,jedis);
                }
            }
        }
        return len;
    }

    // 判断是否已抓取过
    public boolean isProcessed(Request req) {
        return false;
    }

    // 记录处理过的请求
    public void addProcessed(Request req) {
        LOGGER.info("{}", req);
    }

    // 已抓取的总数量
    public long totalCrawled() {
        return -1;
    }

}
