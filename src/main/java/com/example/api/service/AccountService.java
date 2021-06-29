package com.example.api.service;

import com.example.api.base.jdbc.JDBCHandler;
import com.example.api.config.AppConfig;
import com.example.api.config.SQLConfig;
import com.example.api.response.AccountResult;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {
    private final AppConfig conf;
    private final SQLConfig sqls;
    private final JDBCHandler jdbcHandler;

    @PostConstruct
    public void init() {
        log.trace("Service HashCode: {}, AppConfig: {}", this.hashCode(), conf);
    }

    public List<Map<String, Object>> getAllAccounts() {
        List<Map<String, Object>> accounts = new ArrayList<>();
        Map<String, Object> acc = new HashMap<>();
        acc.put("account01", 1);
        acc.put("account02", 2);
        accounts.add(acc);
        return accounts;

        /*
        return this.jdbcHandler.queryForList(this.sqls.getSelectAccountList(), null);
         */
    }

    public AccountResult getAccount(String userId) {
        AccountResult result = new AccountResult();
        result.setAccountId("account01");
        result.setAccountNum(1);

        return result;

        /*
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("user_id", userId);

        return this.jdbcHandler.queryForObject(this.sqls.getSelectAccount(), paramMap, AccountResult.class);
         */
    }
}
