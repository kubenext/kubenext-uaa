package com.github.kubenext.uaa.config;

import com.github.kubenext.config.H2ConfigurationHelper;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;

/**
 * 数据库配置
 *
 * @author lishangjin
 */
@Configuration
@EnableJpaRepositories("com.github.kubenext.uaa.repository")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private final Environment env;

    public DatabaseConfiguration(Environment env) {
        this.env = env;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2TcpServer() throws SQLException {
        //TODO 从配置中设置H2数据库端口，不应该硬编码在代码里
        String port = "18080";
        logger.debug("H2 database is available on port {}", port);
        return H2ConfigurationHelper.createServer(port);
    }

}
