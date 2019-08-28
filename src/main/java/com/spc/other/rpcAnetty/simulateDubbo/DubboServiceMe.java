package com.spc.other.rpcAnetty.simulateDubbo;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * 这个注解应该放到ServiceImpl class上面和dubboService方法上面。<br/>
 * 放到class上面代表这个class是一个DubboService class，放到方法上意味着这个方法是具体的dubboService。<br/>
 * dubboService方法必须要放在被这个注解注释的class里面，否则无法被扫描到。
 * @author Wen, Changying
 * 2019年8月28日
 */
@Retention(RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface DubboServiceMe {

}
