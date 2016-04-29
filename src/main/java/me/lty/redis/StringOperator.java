package me.lty.redis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringOperator {
	
	private static Logger log =LoggerFactory.getLogger(StringOperator.class);

    public static String get(String key,RedisExecPool pool){
        Jedis jedis = pool.getJedis();
        String value = jedis.get(key);
        pool.returnJedis(jedis);
        return value;
    }

    public static long setNotExists(RedisExecPool pool,String key,String value){
        Jedis jedis = pool.getJedis();
        long status = jedis.setnx(key,value);
        pool.returnJedis(jedis);
        return status;
    }

    public static String set(RedisExecPool pool,String key,String value){
        Jedis jedis = pool.getJedis();
         key=key+System.currentTimeMillis();
        String status = jedis.set(key,value); //保证key 不一样System.currentTimeMillis()
        pool.returnJedis(jedis);
        return status;
    }

    public static String set(RedisExecPool pool,String... keysValues){
        Jedis jedis = pool.getJedis();
        String status = jedis.mset(keysValues);
        pool.returnJedis(jedis);
        return status;
    }

    public static List<String> get(RedisExecPool pool,String... keys){
        Jedis jedis = pool.getJedis();
        List<String> result = jedis.mget(keys);
        pool.returnJedis(jedis);
        return result;
    }
}
