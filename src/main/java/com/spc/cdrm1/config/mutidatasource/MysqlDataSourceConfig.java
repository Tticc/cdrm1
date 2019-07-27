//package com.spc.cdrm1.config.mutidatasource;
//
//import java.util.Map;
//
//import javax.persistence.EntityManager;
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(entityManagerFactoryRef="entityManagerFactoryMysql",
//transactionManagerRef="transactionManagerMysql",
//basePackages= { "com.spc.cdrm1.oracledao" }) // 设置Repository所在位置。//不同的数据源配置不同的dao吗？
//public class MysqlDataSourceConfig {
//	@Autowired 
//	@Qualifier("mysqlDataSource")//指定数据源
//    private DataSource mysqlDataSource;
//	
//    @Autowired
//    private JpaProperties jpaProperties;
//	@Autowired
//	private HibernateProperties hibernateProperties;
//	
//	@Bean("entityManagerMysql")
//	@Primary
//	public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
//		return entityManagerFactoryBean(builder).getObject().createEntityManager();
//	}
//	
//	@Bean("entityManagerFactoryMysql")
//    @Primary
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
//		// springboot 2.x 的用法
//		Map<String, Object> propertiesMap= hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
//
//		return builder.dataSource(mysqlDataSource)
//                .properties(propertiesMap)
//                .packages("com.spc.cdrm1.oracleentity")//不同的数据源配置不同的entity吗？
//                //持久化单元名称，当存在多个EntityManagerFactory时，需要制定此名称
//                .persistenceUnit("mysqlPersistenceUnit")
//                .build();
//    }
//	@Primary
//    @Bean(name = "transactionManagerMysql")
//    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
//        return new JpaTransactionManager(entityManagerFactoryBean(builder).getObject());
//    }
//
//    
//}
