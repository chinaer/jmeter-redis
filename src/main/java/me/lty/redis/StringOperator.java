package me.lty.redis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class StringOperator {
	
	private static Logger log =LoggerFactory.getLogger(StringOperator.class);

    public static String get(String key,Jedis jedis){
        String value = jedis.get(key);
        return value;
    }

    public static long setNotExists(Jedis jedis,String key,String value){
        long status = jedis.setnx(key,value);
        return status;
    }

    public static String set(Jedis jedis,String key,String value){
         key=key+System.currentTimeMillis();
        String status = jedis.set(key,value); //保证key 不一样System.currentTimeMillis()
        return status;
    }

    public static String set(Jedis jedis,String... keysValues){
        String status = jedis.mset(keysValues);
        return status;
    }

    public static List<String> get(Jedis jedis,String... keys){
        List<String> result = jedis.mget(keys);
        return result;
    }
    
    public static void hh(String... keys){
    	System.out.println(keys.length);
    }
    
    public static void main(String[] args) {
		hh("asfd","asd");
	}
}
