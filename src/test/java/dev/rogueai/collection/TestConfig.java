package dev.rogueai.collection;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Path;

@ActiveProfiles("test")
@Configuration
@ComponentScan(basePackages = "dev.rogueai.collection.service")
public class TestConfig {

    @Bean
    public Flyway flyway() {
        FluentConfiguration configuration = Flyway.configure().dataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1","","").cleanDisabled(false);
        return new Flyway(configuration);
    }

    // @Autowired
    // private Flyway flyway;

    private final ResourceLoader resourceLoader;

    public TestConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public Path imageDir() {
        return Path.of(System.getProperty("java.io.tmpdir"));
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        Flyway flyway = flyway();
        flyway.clean();
        flyway.migrate();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}
