package database.todoList.dao.impl;

import database.todoList.dao.TaskDAO;
import database.todoList.mappers.TaskRowMapper;
import database.todoList.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class TaskDAOImpl implements TaskDAO {
    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

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
        for (Task task : tasks) insert(task);
    }

    @Override
    public void insertBatchSQL(String sql) {
        jdbcTemplate.batchUpdate(sql);
    }

    @Override
    public Task findTaskByGuid(String guid) {
        String sql = "SELECT * FROM TASK WHERE GUID = ?";
		return jdbcTemplate.queryForObject(sql, new TaskRowMapper(), guid);
    }

    @Override
    public Collection<Task> findAll() {
        String sql = "SELECT * FROM TASK";
		return jdbcTemplate.query(sql, new TaskRowMapper());
    }

    @Override
    public int findTotalTasks() {
        String sql = "SELECT COUNT(*) FROM TASK";
        Number number = jdbcTemplate.queryForObject(sql, new Object[]{}, Integer.class);
        return (number != null ? number.intValue() : 0);
    }
}