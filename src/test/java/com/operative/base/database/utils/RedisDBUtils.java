/**
 * 
 */
package com.operative.base.database.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.operative.base.utils.Logger;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author upratap
 *
 */
public class RedisDBUtils {
	// connect to redis database
		public Jedis connectRedisDB(String dburl) {
			final Jedis jedis = new Jedis(dburl, 6379, 200000);
			jedis.connect();
			return jedis;
		}
		
		public JedisCluster getRedisCluster(String dburl){
		    Set<HostAndPort> jedisClusterNode = new HashSet<>();
		    jedisClusterNode.add(new HostAndPort(dburl, 6379));
		    JedisPoolConfig cfg = new JedisPoolConfig();
		    JedisCluster jc = new JedisCluster(jedisClusterNode, 200000);
		    return jc;
		}
		

		// Get UE Count From DB
		public Integer getUniverseEstimateCount(String uRL, String tennant, String key) {
			final int ueCount = Integer.parseInt(connectRedisDB(uRL).get(tennant + "_" + key));
			Logger.log("Universe Estimate Latest Count Is:" + ueCount);
			return ueCount;
		}

		public List<String> getDBListValues(String dburl, String listName) {
			final List<String> db = connectRedisDB(dburl).lrange(listName, 0, 100000);
			return db;
		}

		
}
