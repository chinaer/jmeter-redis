package me.lty.redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class SetOperator {

    public static long add(RedisExecPool pool,String key,String... members){
        Jedis jedis = pool.getJedis();
        long status = jedis.sadd(key,members);
        pool.returnJedis(jedis);
        return status;
    }

    public static long length(RedisExecPool pool,String key){
        Jedis jedis = pool.getJedis();
        long length = jedis.scard(key);
        pool.returnJedis(jedis);
        return length;
    }

    public static boolean exists(RedisExecPool pool,String key,String member){
        Jedis jedis = pool.getJedis();
        boolean result = jedis.sismember(key,member);
        pool.returnJedis(jedis);
        return result;
    }

    public static Set<String> getMembers(RedisExecPool pool,String key){
        Jedis jedis = pool.getJedis();
        Set<String> result = jedis.smembers(key);
        pool.returnJedis(jedis);
        return result;
    }

    public static long delete(RedisExecPool pool,String key,String... member){
        Jedis jedis = pool.getJedis();
        long status = jedis.srem(key,member);
        pool.returnJedis(jedis);
        return status;
    }
}
