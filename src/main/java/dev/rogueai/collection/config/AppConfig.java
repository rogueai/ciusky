package dev.rogueai.collection.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.nio.file.Path;

@Configuration
public class AppConfig {

    @Value("${ciusky.data}")
    private String ciuskyData;

    private final ResourceLoader resourceLoader;

    public AppConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public Path imageDir() {
        return Path.of(ciuskyData);
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        // This create a new database in the home directory called ~/collections so it is persisted between runs
        dataSource.setUrl("jdbc:h2:" + ciuskyData + "/collections;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1");
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    // @Bean
    // public ResourceDatabasePopulator databasePopulator() {
    //     ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
    //     // The first time the db is created every drops should fail.
    //     resourceDatabasePopulator.setIgnoreFailedDrops(true);
    //     // The order of the scripts is important.
    //     String[] scripts = new String[] { "classpath:/scripts/drop.sql", "classpath:/scripts/schema.sql", "classpath:/scripts/basic.sql" };
    //     for (String script : scripts) {
    //         resourceDatabasePopulator.addScript(resourceLoader.getResource(script));
    //     }
    //     return resourceDatabasePopulator;
    // }
    //
    // @Bean
    // public DataSourceInitializer dataSourceInitializer() {
    //     DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
    //     dataSourceInitializer.setDataSource(dataSource());
    //     dataSourceInitializer.setDatabasePopulator(databasePopulator());
    //     dataSourceInitializer.setEnabled(false);
    //     dataSourceInitializer.setCheckInitializationQuery("SELECT INIT_DATE FROM INFO");
    //     return dataSourceInitializer;
    // }

}
