package dev.rogueai.ciusky.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Value("${ciusky.data}")
    private String ciuskyData;

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.withUsername("doraemon@ciusky.com").password(passwordEncoder().encode("ciusky")).roles("USER").build();
        UserDetails admin = User.withUsername("admin@ciusky.com").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user1, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http //
                .authorizeHttpRequests(authorize -> authorize //
                        .requestMatchers("/favicon.ico", "/*.css", "/*.js", "/images/**").permitAll() //
                        .anyRequest().authenticated()) //
                .formLogin(formLogin -> formLogin //
                        .loginPage("/login").permitAll()//
                        .defaultSuccessUrl("/", true)//
                        .failureUrl("/login?error=true")) //
                .logout(LogoutConfigurer::permitAll) //
                .rememberMe(Customizer.withDefaults());
        return http.build();

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

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.DAYS).maximumSize(100_000).recordStats();
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setAllowNullValues(false);
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

}
