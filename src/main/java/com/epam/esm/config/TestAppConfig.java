package com.epam.esm.config;

import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.repository.api.GiftCertificateRepository;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@Profile("test")
public class TestAppConfig {

    @Bean
    public DataSource testDataSource() {
        HikariConfig config = new HikariConfig();

        Properties props = new Properties();

        try {
            props.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load database properties", e);
        }

        config.setDriverClassName(props.getProperty("jdbc.driverClassName"));
        config.setJdbcUrl(props.getProperty("jdbc.url"));
        config.setUsername(props.getProperty("jdbc.username"));
        config.setPassword(props.getProperty("jdbc.password"));

        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate testJdbcTemplate() {
        return new JdbcTemplate(testDataSource());
    }

    @Bean
    public PlatformTransactionManager testTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(testDataSource());
        return transactionManager;
    }

    @Bean
    public GiftCertificateRepository<GiftCertificate> giftCertificateRepository() {
        return new GiftCertificateRepositoryImpl(testJdbcTemplate(), testDataSource());
    }
}