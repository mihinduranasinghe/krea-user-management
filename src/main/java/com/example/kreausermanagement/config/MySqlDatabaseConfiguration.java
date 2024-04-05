package com.example.kreausermanagement.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableTransactionManagement
public class MySqlDatabaseConfiguration {
    @Value("${app.datasource.jdbcurl}")
    private String jdbcUrl;

    @Value("${app.datasource.username}")
    private String username;

    @Value("${app.datasource.password}")
    private String password;

    @Value("${app.datasource.max-idle}")
    private Integer maxIdle;

    @Value("${app.datasource.max-idle-excess}")
    private Integer maxIdleExcess;

    @Value("${app.datasource.checkout-timeout}")
    private Integer checkoutTimeout;

    @Value("${app.datasource.max-poolsize}")
    private Integer maxPoolSize;

    @Value("${app.datasource.min-poolsize}")
    private Integer minPoolSize;

    @Value("${app.datasource.initial-poolsize}")
    private Integer initialPoolSize;


    @Primary
    @Bean("mySqlDataSource")
    @ConfigurationProperties("app.datasource")
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = DataSourceBuilder.create().type(ComboPooledDataSource.class).build();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setMaxPoolSize(maxPoolSize);
        dataSource.setMinPoolSize(minPoolSize);
        dataSource.setInitialPoolSize(initialPoolSize);
        dataSource.setTestConnectionOnCheckin(true);
        dataSource.setTestConnectionOnCheckout(false);
        dataSource.setIdleConnectionTestPeriod(300);
        dataSource.setCheckoutTimeout(Math.toIntExact(TimeUnit.SECONDS.toMillis(checkoutTimeout)));
        dataSource.setMaxIdleTime(maxIdle);
        dataSource.setMaxIdleTimeExcessConnections(maxIdleExcess);
        return dataSource;
    }

    @Bean("mySqlJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("mySqlDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
