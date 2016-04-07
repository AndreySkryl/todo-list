package database.todoList.dao.impl;

import database.todoList.dao.UserAndListOfTasksDAO;
import database.todoList.mappers.UserAndListOfTasksRowMapper;
import database.todoList.model.UserAndListOfTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserAndListOfTasksImpl implements UserAndListOfTasksDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired(required = false)
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insert(UserAndListOfTasks userAndListOfTasks) {
        String sql =
                "INSERT INTO USER__LIST_OF_TASKS " +
                "(USER_GUID, LIST_OF_TASKS_GUID) " +
                "VALUES (?, ?)";

        jdbcTemplate.update(sql,
                userAndListOfTasks.getUserGuid(),
                userAndListOfTasks.getListOfTasksGuid());
    }

    @Override
    public void insertBatch(List<UserAndListOfTasks> listOfUserAndListOfTasks) {
        for (UserAndListOfTasks userAndListOfTasks : listOfUserAndListOfTasks) {
            insert(userAndListOfTasks);
        }
    }

    @Override
    public void insertBatchSQL(String sql) {
        jdbcTemplate.batchUpdate(sql);
    }

    @Override
    public List<UserAndListOfTasks> findUserAndListOfTasksByListOfTasksGuid(String listOfTasksGuid) {
        String sql = "SELECT * FROM USER__LIST_OF_TASKS WHERE LIST_OF_TASKS_GUID = ?";
        List<UserAndListOfTasks> list = jdbcTemplate.query(sql, new UserAndListOfTasksRowMapper(), listOfTasksGuid);
        return list;
    }

    @Override
    public List<UserAndListOfTasks> findAll() {
        String sql = "SELECT * FROM USER__LIST_OF_TASKS";
        List<UserAndListOfTasks> list = jdbcTemplate.query(sql, new UserAndListOfTasksRowMapper());
        return list;
    }

    @Override
    public int findTotalUserAndListOfTasks() {
        String sql = "SELECT COUNT(*) FROM USER__LIST_OF_TASKS";
        Number number = jdbcTemplate.queryForObject(sql, new Object[]{}, Integer.class);
        return (number != null ? number.intValue() : 0);
    }
}