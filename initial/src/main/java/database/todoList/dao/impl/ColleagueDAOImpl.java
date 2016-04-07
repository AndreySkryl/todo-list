package database.todoList.dao.impl;

import database.todoList.dao.ColleagueDAO;
import database.todoList.mappers.ColleagueRowMapper;
import database.todoList.model.Colleague;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ColleagueDAOImpl implements ColleagueDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired(required = false)
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insert(Colleague colleague) {
        String sql =
                "INSERT INTO COLLEAGUE " +
                "(USER_GUID, COLLEAGUE_GUID, CREATE_TIME, UPDATE_TIME) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                colleague.getUserGuid(), colleague.getColleagueGuid(),
                colleague.getCreateTime(), colleague.getUpdateTime()
        );
    }

    @Override
    public void insertBatch(List<Colleague> colleagues) {
        for (Colleague colleague : colleagues) {
            insert(colleague);
        }
    }

    @Override
    public void insertBatchSQL(String sql) {
        jdbcTemplate.batchUpdate(sql);
    }

    @Override
    public List<Colleague> findListOfColleaguesByUserGuid(String userGuid) {
        String sql = "SELECT * FROM COLLEAGUE WHERE USER_GUID = ?";
        List<Colleague> colleagues = jdbcTemplate.query(sql, new ColleagueRowMapper(), userGuid);
        return colleagues;
    }

    @Override
    public List<Colleague> findAll() {
        String sql = "SELECT * FROM COLLEAGUE";
        List<Colleague> colleagues = jdbcTemplate.query(sql, new ColleagueRowMapper());
        return colleagues;
    }

    @Override
    public int findTotalColleague() {
        String sql = "SELECT COUNT(*) FROM COLLEAGUE";
        Number number = jdbcTemplate.queryForObject(sql, new Object[]{}, Integer.class);
        return (number != null ? number.intValue() : 0);
    }
}