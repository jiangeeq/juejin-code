package com.jls.example.springdtx.service;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DBConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.ds-user") //设置读取在properties文件内容的前缀
    public DataSourceProperties userDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource userDataSource() {
        return userDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setDataSource(userDataSource());
        factory.setPackagesToScan("com.jls.example.springdtx");
        return factory;
    }
    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager userTM = new JpaTransactionManager();
        userTM.setEntityManagerFactory(entityManagerFactory().getObject());
        PlatformTransactionManager orderTM = new DataSourceTransactionManager(orderDataSource());
        ChainedTransactionManager tm = new ChainedTransactionManager(orderTM,userTM);
        return tm;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.ds-order") //设置读取在properties文件内容的前缀
    public DataSourceProperties orderDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource orderDataSource() {
        return orderDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public JdbcTemplate orderJdbcTemplate(@Qualifier("orderDataSource") DataSource orderDataSource) {
        return new JdbcTemplate(orderDataSource);
    }

}
