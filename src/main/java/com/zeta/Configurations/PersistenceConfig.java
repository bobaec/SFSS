package com.zeta.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

//Configuration for local database connection.
@Configuration
public class PersistenceConfig {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/testdb"); //TODO: change back to VM server before deploy
        dataSource.setUsername("testuser".trim());
        dataSource.setPassword("testtest".trim());
        return dataSource;
    }
}