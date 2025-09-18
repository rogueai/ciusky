package dev.rogueai.collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.nio.file.Path;

@Configuration
@ComponentScan(basePackages = "dev.rogueai.collection.service")
public class TestConfig {

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
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public ResourceDatabasePopulator databasePopulator() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        // The first time the db is created every drops should fail.
        resourceDatabasePopulator.setIgnoreFailedDrops(true);
        // The order of the scripts is important.
        String[] scripts = new String[] { "classpath:/scripts/drop.sql", "classpath:/scripts/schema.sql", "classpath:/scripts/basic.sql" };
        for (String script : scripts) {
            resourceDatabasePopulator.addScript(resourceLoader.getResource(script));
        }
        return resourceDatabasePopulator;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource());
        dataSourceInitializer.setDatabasePopulator(databasePopulator());
        dataSourceInitializer.setEnabled(true);
        return dataSourceInitializer;
    }

}
