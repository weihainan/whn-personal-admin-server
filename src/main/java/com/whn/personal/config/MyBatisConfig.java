package com.whn.personal.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * MyBatis mapper文件扫描配置类
 * @author yanguanyu(290536)
 * @since 0.1 created on 2016/10/27.
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:druid.properties")
@MapperScan({
        "com.whn.personal.modules.*.mapper"
})
public class MyBatisConfig {

    private static final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(env.getProperty("druid.jdbc_url"));
        druidDataSource.setUsername(env.getProperty("druid.username"));
        druidDataSource.setPassword(env.getProperty("druid.password"));
        druidDataSource.setDriverClassName(env.getProperty("druid.driver_class_name"));
        druidDataSource.setInitialSize(Integer.valueOf(env.getProperty("druid.initialSize")));
        druidDataSource.setMinIdle(Integer.valueOf(env.getProperty("druid.minIdle")));
        druidDataSource.setMaxActive(Integer.valueOf(env.getProperty("druid.maxActive")));
        druidDataSource.setMaxWait(Long.valueOf(env.getProperty("druid.maxWait")));
        druidDataSource.setMinEvictableIdleTimeMillis(Long.valueOf(env.getProperty("druid.minEvictableIdleTimeMillis")));
        druidDataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(env.getProperty("druid.timeBetweenEvictionRunsMillis")));
        druidDataSource.setValidationQuery(env.getProperty("druid.validationQuery"));
        druidDataSource.setTestWhileIdle(Boolean.valueOf(env.getProperty("druid.testWhileIdle")));
        druidDataSource.setTestOnBorrow(Boolean.valueOf(env.getProperty("druid.testOnBorrow")));
        druidDataSource.setTestOnReturn(Boolean.valueOf(env.getProperty("druid.testOnReturn")));
        try {
            druidDataSource.setFilters(env.getProperty("druid.filters"));
        } catch (SQLException e) {
            logger.error("druid setFilters failed.", e);
            return null;
        }
        return druidDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));
//        sessionFactory.setTypeAliasesPackage("");
        return sessionFactory.getObject();
    }
}
