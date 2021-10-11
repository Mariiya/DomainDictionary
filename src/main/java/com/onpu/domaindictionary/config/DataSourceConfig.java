package com.onpu.domaindictionary.config;

import oracle.jdbc.datasource.OracleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;


@Configuration
@PropertySource({"classpath:application.properties"})
public class DataSourceConfig {

    @Autowired
    Environment environment;

    @Bean
    public DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new oracle.jdbc.pool.OracleDataSource();
        dataSource.setUser(environment.getProperty("spring.datasource.username"));//"domain_dictionary");
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));//"masha");
        dataSource.setURL(environment.getProperty("spring.datasource.url"));//"jdbc:oracle:thin:@localhost:1521:xe");
        dataSource.getConnection();
        System.out.println(environment.getProperty("spring.datasource.username"));
        //  DriverManagerDataSource dataSource = new DriverManagerDataSource();
      //  dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
       // dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
       // dataSource.setUsername("domain_dictionary");
       // dataSource.setPassword("masha");
        return dataSource;
    }

}
