package me.lty.redis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

public class HashOperator {

    public static boolean exists(RedisExecPool pool,String key,String field){
        Jedis jedis = pool.getJedis();
        boolean result = jedis.hexists(key,field);
        pool.returnJedis(jedis);
        return result;
    }

    public static List<String> get(RedisExecPool pool, String key, String... field){
        Jedis jedis = pool.getJedis();
        List<String> result = jedis.hmget(key,field);
        pool.returnJedis(jedis);
        return result;
    }

    public static Map<String,String> getAll(RedisExecPool pool,String key){
        Jedis jedis = pool.getJedis();
        Map<String,String> result = jedis.hgetAll(key);
        pool.returnJedis(jedis);
        return result;
    }

    public static long set(RedisExecPool pool,String key,String field,String value){
        Jedis jedis = pool.getJedis();
        long status = jedis.hset(key, field, value);
        pool.returnJedis(jedis);
        return status;
    }

    public static String setAll(RedisExecPool pool,String key,Map<String,String> map){
        Jedis jedis = pool.getJedis();
        String result = jedis.hmset(key,map);
        pool.returnJedis(jedis);
        return result;
    }

    public static long length(RedisExecPool pool,String key){
        Jedis jedis = pool.getJedis();
        long length = jedis.hlen(key);
        pool.returnJedis(jedis);
        return length;
    }

    public static long delete(RedisExecPool pool,String key,String... field){
        Jedis jedis = pool.getJedis();
        long status = jedis.hdel(key, field);
        pool.returnJedis(jedis);
        return status;
    }
}
