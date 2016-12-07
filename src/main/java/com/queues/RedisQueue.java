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
public class RedisQueue {
	private String host = "127.0.0.1";
	private int port = 6379;
	private String password = "";
	private String quueName = "pixiv:pic:url:";
	private JedisPool wpool = null;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public void refresh() {
		if (wpool != null) {
			this.wpool.destroy();
			this.wpool = null;
		}
	}

	public JedisPool getWritePool() {
		if (wpool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(500);
			config.setMaxIdle(200);
			config.setMinIdle(100);
			config.setMaxWaitMillis(1000 * 100);
			if (StringUtils.isNotBlank(password)) {
				wpool = new JedisPool(config, this.host, this.port, 0, password);
			} else {
				wpool = new JedisPool(config, this.host, this.port, 0);
			}
		}
		return wpool;
	}

	public Jedis getWClient() {
		return getWritePool().getResource();
	}

	public Request bPop() {
		Jedis jedis = null;
		Request request = null;
		try {
			jedis = getWClient();
			List<String> res = jedis.brpop(0, quueName);
			request = JSON.parseObject(res.get(1), Request.class);
		} catch (Exception e) {
			logger.warn(e.getMessage());
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return request;
	}

	public boolean push(Request req) {
		Jedis jedis = null;
		boolean res = false;
		try {
			jedis = getWClient();
			res = jedis.lpush(quueName,
					JSON.toJSONString(req)) > 0;
		} catch (Exception e) {
			logger.warn(e.getMessage());
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return res;
	}

	public long len() {
		long len = 0;
		Jedis jedis = null;
		try {
			jedis = getWClient();
			len = jedis.llen(quueName);
		} catch (Exception e) {
			logger.warn(e.getMessage());
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return len;
	}

	//判断是否已抓取过
	public boolean isProcessed(Request req) {
		return false;
	}

	//记录处理过的请求
	public void addProcessed(Request req) {
		logger.info("{}", req);
	}

	//已抓取的总数量
	public long totalCrawled() {
		return -1;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getQuueName() {
		return quueName;
	}

	public void setQuueName(String quueName) {
		this.quueName = quueName;
	}
}
