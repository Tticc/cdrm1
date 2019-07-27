package com.spc.cdrm1.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.spc.cdrm1.config.mutidatasource.DynamicDataSourceContextHolder;
import com.spc.cdrm1.util.CustomizeDataSource;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class DynamicDataSourceAspect {

	/**
	 * 根据注解切换数据源
	 * @author Wen, Changying
	 * @param point
	 * @param customizeDataSource
	 * @date 2019年7月23日
	 */
	@Before("@annotation(customizeDataSource))")
	@Order(-1)
	public void switchDataSource(JoinPoint point, CustomizeDataSource customizeDataSource) {

		if(DynamicDataSourceContextHolder.containDataSourceKey(customizeDataSource.value())) {
			DynamicDataSourceContextHolder.setDataSourceKey(customizeDataSource.value());
			System.out.println("set datasource to:"+DynamicDataSourceContextHolder.getDataSourceKey());
			return;
		}
		log.error("DataSource [{}] doesn't exist, use default DataSource [{}]", customizeDataSource.value());
	}
	
	/**
	 * 重置为默认数据源
	 * @author Wen, Changying
	 * @param point
	 * @param customizeDataSource
	 * @date 2019年7月23日
	 */
	@After("@annotation(customizeDataSource))")
	public void restoreDataSource(JoinPoint point, CustomizeDataSource customizeDataSource) {

		DynamicDataSourceContextHolder.clearDataSourceKey();
		log.info("Restore DataSource to [{}] in Method [{}]",
                DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
	}
	
}
