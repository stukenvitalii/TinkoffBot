package edu.java.scrapper;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
public abstract class IntegrationTest {
    public static PostgreSQLContainer<?> POSTGRES;

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("scrapper")
            .withUsername("postgres")
            .withPassword("postgres");
        POSTGRES.start();

        System.out.println(POSTGRES.getMappedPort(5432));

        try {
            runMigrations(POSTGRES);
        } catch (SQLException | LiquibaseException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void runMigrations(JdbcDatabaseContainer<?> c)
        throws SQLException, LiquibaseException, FileNotFoundException {
        String url = c.getJdbcUrl();
        String username = c.getUsername();
        String password = c.getPassword();

        Path path = new File(".").toPath().toAbsolutePath()
            .getParent().getParent().resolve("migrations");

        Connection connection = DriverManager.getConnection(url, username, password);

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

        Liquibase liquibase = new liquibase.Liquibase("master.xml", new DirectoryResourceAccessor(path), database);

        liquibase.update(new Contexts(), new LabelExpression());
    }

//    @DynamicPropertySource
//    static void jdbcProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.hikari.jdbc-url", POSTGRES::getJdbcUrl);
//        registry.add("spring.datasource.hikari.username", POSTGRES::getUsername);
//        registry.add("spring.datasource.hikari.password", POSTGRES::getPassword);
//    }

    @DynamicPropertySource
    protected static void setProperties(
        DynamicPropertyRegistry registry
    ) {
        setDataSourceProperties(registry);
    }

    private static void setDataSourceProperties(DynamicPropertyRegistry registry) {
        var mysqlDbHost = POSTGRES.getHost();
        var mysqlDbPort = POSTGRES.getFirstMappedPort();

        System.out.println(mysqlDbHost + mysqlDbPort);
        registry.add(
            "spring.datasource.hikari.url",
            () -> String.format("jdbc:postgresql://%s:%d/scrapper", mysqlDbHost, mysqlDbPort)
        );
        registry.add("spring.datasource.hikari.driver-class-name",org.postgresql.Driver.class);

    }

//    @Bean
//    public DataSource myDataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl("jdbc:postgresql://localhost:"+POSTGRES.getFirstMappedPort()+ ":5432/scrapper");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("postgres");
//
//        return dataSource;
//    }


}
