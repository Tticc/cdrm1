package com.spc.cdrm1.util.redisUtil;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil_Value {

	@Resource
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	
	/**
     * 前缀
     */
    public static final String KEY_PREFIX_VALUE = "cdrm::redis::first::";
    
    /**
     * 存入缓存，默认不过期
     * @author Wen, Changying
     * @param k
     * @param v
     * @return
     * @date 2019年7月19日
     */
    public boolean setValue(String k, Serializable v) {
    	return setValue(k,v,-1);
    }
    /**
     * 存入缓存
     * @author Wen, Changying
     * @param k
     * @param v
     * @param time
     * @return
     * @date 2019年7月19日
     */
    public boolean setValue(String k, Serializable v, long time) {
    	return setValue(k,v,time,TimeUnit.SECONDS);
    }
    
    /**
     * 存入缓存
     * @author Wen, Changying
     * @param k
     * @param v
     * @param time
     * @param unit
     * @return
     * @date 2019年7月19日
     */
    public boolean setValue(String k, Serializable v, long time, TimeUnit unit) {
    	String key = KEY_PREFIX_VALUE + k;
    	try {
    		ValueOperations<Serializable, Serializable> valueOps = redisTemplate.opsForValue();
    		valueOps.set(key, v);
    		if (time > 0) redisTemplate.expire(key, time, unit);
            return true;
    	}catch (Throwable t) {
    		System.out.println("缓存[{"+key+"}]失败, value[{"+v+"}]"+t);
            //logger.error("缓存[{}]失败, value[{}]",key,v,t);
        }
        return false;
    }
    
    /**
     * 判断缓存是否存在
     * @author Wen, Changying
     * @param k
     * @return
     * @date 2019年7月19日
     */
    public boolean containsValueKey(String k) {
    	String key = KEY_PREFIX_VALUE + k;
    	try {
    		return redisTemplate.hasKey(key);
    	}catch (Throwable t) {
    		System.out.println("判断缓存[{"+key+"}]存在失败。"+t);
            //logger.error("判断缓存存在失败key[" + key + ", error[" + t + "]");
    	}
        return false;
    }
    
    /**
     * 获取缓存
     * @author Wen, Changying
     * @param k
     * @return
     * @date 2019年7月19日
     */
    public Serializable getValue(String k) {
    	String key = KEY_PREFIX_VALUE + k;
    	try {
    		ValueOperations<Serializable, Serializable> valueOps = redisTemplate.opsForValue();
    		return valueOps.get(key);
    	}catch (Throwable t) {
    		System.out.println("获取缓存[{"+key+"}]失败。"+t);
            //logger.error("获取缓存失败key[" + KEY_PREFIX_VALUE + k + ", error[" + t + "]");
    	}
        return null;
    }
    
    /**
     * 删除缓存
     * @author Wen, Changying
     * @param k
     * @return
     * @date 2019年7月19日
     */
    public boolean deleteValue(String k) {
    	String key = KEY_PREFIX_VALUE + k;
    	try {
    		redisTemplate.delete(key);
            return true;
    	}catch(Throwable t) {
    		System.out.println("删除缓存[{"+key+"}]失败。"+t);
            //logger.error("删除缓存失败key[" + key + ", error[" + t + "]");
    	}
        return false;
    }
    
}
