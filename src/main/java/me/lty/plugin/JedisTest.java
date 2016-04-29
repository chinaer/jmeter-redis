package me.lty.plugin;

import me.lty.redis.RedisExecPool;
import me.lty.redis.StringOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JedisTest {
	private static Logger log=LoggerFactory.getLogger(JedisTest.class);

	public static void main(String[] args) {
		
		RedisExecPool pool = RedisExecPool.getInstance("192.168.1.154",19000,
				null);
		for(int i=0;i<100;i++){
			StringOperator.set(pool, "ge11","ge123");
		}
		log.info(StringOperator.get("ge111461907360150", pool));
	}
}
