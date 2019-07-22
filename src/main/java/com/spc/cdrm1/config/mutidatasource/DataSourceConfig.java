package com.spc.cdrm1.config.mutidatasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {
	
	/**
	 * 没有注明的情况下默认使用mysql
	 * @author Wen, Changying
	 * @return
	 * @date 2019年7月22日
	 */
	@Bean(name = "mysqlDataSource")
	@Qualifier("mysqlDataSource")
	@ConfigurationProperties(prefix="spring.datasource.mysql")
	public DataSource mysqlDataSource() {
		return DataSourceBuilder.create().build();
	}

    

	@Primary
	@Bean(name = "oracleDataSource")
	@Qualifier("oracleDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.oracle")
	public DataSource oracleDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	/*@Bean(name = "mysqlJdbcTemplate")
	public JdbcTemplate mysqlJdbcTemplate(@Qualifier("mysqlDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	@Bean(name = "oracleJdbcTemplate")
	public JdbcTemplate oracleJdbcTemplate(@Qualifier("oracleDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}*/
}
