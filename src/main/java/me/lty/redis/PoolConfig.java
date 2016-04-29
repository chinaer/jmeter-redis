package me.lty.redis;

public interface PoolConfig {
    int MAX_TOTAL = 100 ;   
    int MAX_IDLE = 50;    
    int MIN_IDLE = 10;    
    int MAX_WAIT = 2000;  
    boolean TEST_ON_BORROW = true;
    boolean TEST_ON_RETURN = true;
}
