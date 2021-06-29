package com.example.api.base.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
public class JDBCHandler {
    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final int fetchSize;
    private final boolean autoCommit;

    public <T> List<T> query(String sql, Map<String, ?> paramMap, Class<T> elementType) {
        return this.namedJdbcTemplate.query(sql, paramMap, BeanPropertyRowMapper.newInstance(elementType));
    }

    void query(String sql, Map<String, ?> paramMap, RowCallbackHandler rch) {
        this.namedJdbcTemplate.query(sql, paramMap, rch);
    }

    public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap) {
        return this.namedJdbcTemplate.queryForList(sql, paramMap);
    }

    public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap) {
        return this.namedJdbcTemplate.queryForMap(sql, paramMap);
    }

    public <T> T queryForObject(String sql, Map<String, ?> paramMap, Class<T> elementType) {
        return this.namedJdbcTemplate.queryForObject(sql, paramMap,  elementType);
    }

    public int update(String sql, Map<String, ?> paramMap) {
        return this.namedJdbcTemplate.update(sql, paramMap);
    }

    public void executeQueryFetchAll(String sql, RowCallbackHandler rowCallbackHandler) throws SQLException {
        DataSource dataSource = this.namedJdbcTemplate.getJdbcTemplate().getDataSource();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = Objects.requireNonNull(dataSource).getConnection();
            connection.setAutoCommit(this.autoCommit);

            stmt = connection.createStatement();
            stmt.setFetchSize(this.fetchSize);

            rs = stmt.executeQuery(sql);
            rowCallbackHandler.processRow(rs);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(stmt);
            JdbcUtils.closeConnection(connection);
        }
    }
    public String poolStats () {
        HikariDataSource dataSource = (HikariDataSource) this.namedJdbcTemplate.getJdbcTemplate().getDataSource();
        assert dataSource != null;
        HikariPoolMXBean pool = dataSource.getHikariPoolMXBean();
        return String.format("Pool stats: total=%d, active=%d, idle=%d, waiting=%d",
                 pool.getTotalConnections(), pool.getActiveConnections(), pool.getIdleConnections(), pool.getThreadsAwaitingConnection());
    }

    public List<List<Object>> executeQueryFetchAll(String sql) throws SQLException {
        DataSource dataSource = this.namedJdbcTemplate.getJdbcTemplate().getDataSource();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        List<List<Object>> rows = new ArrayList<>();
        try {
            connection = Objects.requireNonNull(dataSource).getConnection();
            connection.setAutoCommit(this.autoCommit);

            stmt = connection.createStatement();
            stmt.setFetchSize(this.fetchSize);

            rs = stmt.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();
            int columnCount = metadata.getColumnCount();
            while (rs.next()) {
                List<Object> row = new ArrayList<>(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                rows.add(row);
            }
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(stmt);
            JdbcUtils.closeConnection(connection);
        }
        return rows;
    }
}
