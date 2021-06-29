package com.example.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sql.lab")
public class SQLConfig {

    //------------------------------------------
    // Account SQL
    //------------------------------------------

    private String selectAccount;
    private String selectAccountList;
}
