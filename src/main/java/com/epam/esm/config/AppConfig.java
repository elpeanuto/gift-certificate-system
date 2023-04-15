package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
@EnableTransactionManagement
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        Properties props = new Properties();

        try {
            props.load(getClass().getClassLoader().getResourceAsStream("testDatabase.properties"));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load database properties", e);
        }

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(props.getProperty("jdbc.driverClassName"));
        config.setJdbcUrl(props.getProperty("jdbc.url"));
        config.setUsername(props.getProperty("jdbc.username"));
        config.setPassword(props.getProperty("jdbc.password"));
        config.setConnectionTimeout(Integer.parseInt(props.getProperty("jdbc.connectionTimeout")));
        config.setIdleTimeout(Integer.parseInt(props.getProperty("jdbc.idleTimeout")));
        config.setMaxLifetime(Integer.parseInt(props.getProperty("jdbc.maxLifetime")));
        config.setMaximumPoolSize(Integer.parseInt(props.getProperty("jdbc.maximumPoolSize")));
        config.setMinimumIdle(Integer.parseInt(props.getProperty("jdbc.minimumIdle")));

        return new HikariDataSource(config);    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }
}
