package database.todoList.dao.impl;

import database.todoList.dao.ListOfTasksDAO;
import database.todoList.mappers.ListOfTasksRowMapper;
import database.todoList.model.ListOfTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Repository
public class ListOfTasksImpl implements ListOfTasksDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired(required = false)
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insert(ListOfTasks listOfTasks) {
        String sql =
                "INSERT INTO LIST_OF_TASKS " +
                        "(GUID, USER_GUID, FAVOURITES, NAME, DESCRIPTION, CREATE_TIME, UPDATE_TIME) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                listOfTasks.getGuid(), listOfTasks.getUserGuid(),
                listOfTasks.getFavourites(), listOfTasks.getName(),
                listOfTasks.getDescription(),
                listOfTasks.getCreateTime(), listOfTasks.getUpdateTime());
    }

    @Override
    public void insertBatch(Collection<ListOfTasks> listOfTasks) {
        for (ListOfTasks listOfTask : listOfTasks) {
            insert(listOfTask);
        }
    }

    @Override
    public void insertBatchSQL(String sql) {
        jdbcTemplate.batchUpdate(sql);
    }

    @Override
    public ListOfTasks findListOfTasksByGuid(String guid) {
        String sql = "SELECT * FROM COLLEAGUE WHERE USER_GUID = ?";
        ListOfTasks list = jdbcTemplate.queryForObject(sql, new ListOfTasksRowMapper(), guid);
        return list;
    }

    @Override
    public Collection<ListOfTasks> findAll() {
        String sql = "SELECT * FROM COLLEAGUE";
        List<ListOfTasks> allListOfTasks = jdbcTemplate.query(sql, new ListOfTasksRowMapper());
        return allListOfTasks;
    }

    @Override
    public int findTotalListOfTasks() {
        String sql = "SELECT COUNT(*) FROM LIST_OF_TASKS";
        Number number = jdbcTemplate.queryForObject(sql, new Object[]{}, Integer.class);
        return (number != null ? number.intValue() : 0);
    }
}