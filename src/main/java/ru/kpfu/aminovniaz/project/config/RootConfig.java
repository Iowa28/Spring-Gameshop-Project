package ru.kpfu.aminovniaz.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.yaml")
public class RootConfig {

//    @Resource
//    private Environment env;
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
//        dataSource.setUrl(env.getRequiredProperty("db.url"));
//        dataSource.setUsername(env.getRequiredProperty("db.user"));
//        dataSource.setPassword(env.getRequiredProperty("db.password"));
//
//        return dataSource;
//    }

}
