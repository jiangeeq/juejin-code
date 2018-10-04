package com.jls.example.springdtxdbdb;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DBConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.ds-user") //设置读取在properties文件内容的前缀
    public DataSourceProperties userDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.ds-user")
    public DataSource userDataSource(){
        return userDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public JdbcTemplate userJdbcTemplate(@Qualifier("userDataSource") DataSource userDataSource){
        return new JdbcTemplate(userDataSource);
    }

    @Bean
    @ConfigurationProperties(prefix="spring.ds-order") //设置读取在properties文件内容的前缀
    public DataSourceProperties orderDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix="spring.ds-order")
    public DataSource orderDataSource(){
        return orderDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public JdbcTemplate orderJdbcTemplate(@Qualifier("orderDataSource") DataSource orderDataSource){
        return new JdbcTemplate(orderDataSource);
    }

}
