package me.lty.redis;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;

import java.util.List;

public class ListOperator {

    public static long length (RedisExecPool pool,String key){
        Jedis jedis = pool.getJedis();
        long length = jedis.llen(key);
        pool.returnJedis(jedis);
        return length;
    }

    public static String set(RedisExecPool pool,String key,int index,String value){
        Jedis jedis = pool.getJedis();
        String result = jedis.lset(key,index,value);
        pool.returnJedis(jedis);
        return result;
    }

    public static long insert(RedisExecPool pool, String key,LIST_POSITION where,String pivot,String value){
        Jedis jedis = pool.getJedis();
        long status = jedis.linsert(key, where, pivot, value);
        pool.returnJedis(jedis);
        return status;
    }

    public static String get(RedisExecPool pool,String key,int index){
        Jedis jedis = pool.getJedis();
        String result = jedis.lindex(key,index);
        pool.returnJedis(jedis);
        return result;
    }

    public static String lpop(RedisExecPool pool,String key){
        Jedis jedis = pool.getJedis();
        String result = jedis.lpop(key);
        pool.returnJedis(jedis);
        return result;
    }

    public static String rpop(RedisExecPool pool,String key){
        Jedis jedis = pool.getJedis();
        String result = jedis.rpop(key);
        pool.returnJedis(jedis);
        return result;
    }

    public static long lpush(RedisExecPool pool,String key,String... value){
        Jedis jedis = pool.getJedis();
        long result = jedis.lpush(key,value);
        pool.returnJedis(jedis);
        return  result;
    }

    public static long rpush(RedisExecPool pool,String key,String... value){
        Jedis jedis = pool.getJedis();
        long result = jedis.rpush(key,value);
        pool.returnJedis(jedis);
        return result;
    }

    public static String clear(RedisExecPool pool,String key){
        Jedis jedis = pool.getJedis();
        String result = jedis.ltrim(key,0,0);
        jedis.lpop(key);
        pool.returnJedis(jedis);
        return result;
    }

    public static List<String> lrange(RedisExecPool pool,String key,long start,long end){
        Jedis jedis = pool.getJedis();
        List<String> result = jedis.lrange(key,start,end);
        pool.returnJedis(jedis);
        return result;
    }
}
