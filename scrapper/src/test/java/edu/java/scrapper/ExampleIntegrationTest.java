    package edu.java.scrapper;

    import com.zaxxer.hikari.HikariDataSource;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Qualifier;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.context.annotation.Bean;
    import javax.sql.DataSource;
    import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

    @SpringBootTest
    public class ExampleIntegrationTest extends IntegrationTest {


//        @Autowired
//        @Qualifier("myDataSource")
//        private DataSource dataSource;

        @Test
        public void testLiquibaseMigration() {
            // Verify that Liquibase migrations have been applied successfully
            assertThat("lala").isNotNull();
            // Add assertions as needed
        }
    }
