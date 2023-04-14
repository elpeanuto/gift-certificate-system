package com.epam.esm.config;

import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.repository.api.GiftCertificateRepository;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class TestAppConfig {

    @Bean
    public DataSource testDataSource() {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName("org.h2.Driver");
        config.setJdbcUrl("jdbc:h2:mem:testdb");
        config.setUsername("sa");
        config.setPassword("");

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