package database.todoList.dao.impl;

import database.todoList.dao.TaskDAO;
import database.todoList.model.Task;
import database.todoList.mappers.TaskRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Repository
public class TaskDAOImpl implements TaskDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired(required = false)
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insert(Task task) {
        String sql =
                "INSERT INTO TASK " +
                "(LIST_OF_TASKS_GUID, GUID, STATUS, DESCRIPTION, CREATE_TIME, UPDATE_TIME) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                task.getListOfTasksGuid(), task.getGuid(),
                task.getStatus(), task.getDescription(),
                task.getCreateTime(), task.getUpdateTime());
    }

    @Override
    public void insertBatch(Collection<Task> tasks) {
        for (Task task : tasks) {
            insert(task);
        }
    }

    @Override
    public void insertBatchSQL(String sql) {
        jdbcTemplate.batchUpdate(sql);
    }

    @Override
    public Task findTaskByGuid(String guid) {
        String sql = "SELECT * FROM TASK WHERE GUID = ?";
        Task task = jdbcTemplate.queryForObject(sql, new TaskRowMapper(), guid);
        return task;
    }

    @Override
    public Collection<Task> findAll() {
        String sql = "SELECT * FROM TASK";
        List<Task> tasks = jdbcTemplate.query(sql, new TaskRowMapper());
        return tasks;
    }

    @Override
    public int findTotalTasks() {
        String sql = "SELECT COUNT(*) FROM TASK";
        Number number = jdbcTemplate.queryForObject(sql, new Object[]{}, Integer.class);
        return (number != null ? number.intValue() : 0);
    }
}
