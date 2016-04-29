package me.lty.redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class KeyOperator {

    public static String flushAll(RedisExecPool pool){
        Jedis jedis = pool.getJedis();
        String data = jedis.flushAll();
        pool.returnJedis(jedis);
        return data;
    }

    public static long expired(RedisExecPool pool,String key,int seconds){
        Jedis jedis = pool.getJedis();
        long count = jedis.expire(key,seconds);
        pool.returnJedis(jedis);
        return count;
    }

    public static boolean exists(RedisExecPool pool,String key){
        Jedis jedis = pool.getJedis();
        boolean exists = jedis.exists(key);
        pool.returnJedis(jedis);
        return exists;
    }

    public static String type(RedisExecPool pool,String key){
        Jedis jedis = pool.getJedis();
        String type = jedis.type(key);
        pool.returnJedis(jedis);
        return type;
    }

    public static long delete(RedisExecPool pool,String key){
        Jedis jedis = pool.getJedis();
        long status = jedis.del(key);
        pool.returnJedis(jedis);
        return status;
    }

    public static Set<String> keys(RedisExecPool pool,String pattern){
        Jedis jedis = pool.getJedis();
        Set<String> set = jedis.keys(pattern);
        pool.returnJedis(jedis);
        return set;
    }
}
