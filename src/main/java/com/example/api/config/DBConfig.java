package com.example.api.config;

import com.example.api.base.jdbc.JDBCHandler;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Setter
@ToString

@Configuration
@ConfigurationProperties(prefix = "db.datasource")
public class DBConfig {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private Long connectionTimeoutMs;
    private Integer maximumPoolSize;
    private String poolName;
    private int fetchSize;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(this.driverClassName);
        dataSource.setJdbcUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setConnectionTimeout(this.connectionTimeoutMs);
        dataSource.setMaximumPoolSize(this.maximumPoolSize);
        dataSource.setPoolName(this.poolName);
        dataSource.setAutoCommit(false);
        return dataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public JDBCHandler jdbcHandler(NamedParameterJdbcTemplate namedJdbcTemplate) {
        return new JDBCHandler(namedJdbcTemplate, this.fetchSize, false);
    }
}
