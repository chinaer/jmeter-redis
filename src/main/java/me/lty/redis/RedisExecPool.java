package me.lty.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisExecPool {

    private static JedisPool jedisPool = null;

    private static  RedisExecPool redisExecPool = null;

    private RedisExecPool(String ip, int port, String password){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(PoolConfig.MAX_TOTAL);
        config.setMaxIdle(PoolConfig.MAX_IDLE);
        config.setMinIdle(PoolConfig.MIN_IDLE);
        config.setMaxWaitMillis(PoolConfig.MAX_WAIT);
        config.setTestOnBorrow(PoolConfig.TEST_ON_BORROW);
        config.setTestOnReturn(PoolConfig.TEST_ON_RETURN);
        if(password==null || "".equals(password.trim())){
            jedisPool = new JedisPool(config,ip,port);
        }else{
            jedisPool = new JedisPool(config,ip,port,10000,password);
        }
    }

    public static synchronized RedisExecPool getInstance(String ip, int port, String password){
        if(redisExecPool == null){
                if(redisExecPool == null){
                    redisExecPool = new RedisExecPool(ip,port,password);
                }
        }
        return redisExecPool;
    }

    public void reConneciont(){
    	jedisPool=null;
    }
    public JedisPool getJedisPool(){
        return jedisPool;
    }

    public Jedis getJedis(){
        return jedisPool.getResource();
    }

    public void returnJedis(Jedis jedis){
        jedis.close();
    }
}
