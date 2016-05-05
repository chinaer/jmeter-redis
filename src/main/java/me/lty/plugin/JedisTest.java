package me.lty.plugin;

import me.lty.redis.StringOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class JedisTest {
	private static Logger log=LoggerFactory.getLogger(JedisTest.class);

	public static void main(String[] args) {
		
		Jedis pool = new Jedis("192.168.1.154",19000);
		for(int i=0;i<100;i++){
			StringOperator.set(pool, "ge11","ge123");
		}
		log.info(StringOperator.get("ge111461907360150", pool));
	}
}
