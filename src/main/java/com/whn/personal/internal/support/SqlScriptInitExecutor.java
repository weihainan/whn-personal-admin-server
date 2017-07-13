package com.whn.personal.internal.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * sql脚本初始化执行器<br/>
 * 在容器启动完后触发执行，需要程序自行判断执行脚本的前置条件<br/>
 * note：<br/>
 *      慎用！   因为生产数据库被限制了，所以通过这个类来执行一些数据表的新增和修改操作.
 */

@Component
public class SqlScriptInitExecutor implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(SqlScriptInitExecutor.class);

    @Resource
    private DataSource dataSource;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initTables();
    }

    private void initTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("sql/personal_admin.sql"));
        try {
            populator.populate(dataSource.getConnection());
        } catch (SQLException e) {
            logger.error("init tables error", e);
        }
    }
}
