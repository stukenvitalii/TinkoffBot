package edu.java.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
public class SpringJdbcConfig {
    @Bean
    public DataSource myPostgresDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/scrapper");
        ds.setUsername("postgres");
        ds.setPassword("postgres");

        return ds;
    }
}
