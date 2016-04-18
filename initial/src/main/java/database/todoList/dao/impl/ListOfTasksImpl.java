package database.todoList.dao.impl;

import database.todoList.dao.ListOfTasksDAO;
import database.todoList.mappers.ListOfTasksRowMapper;
import database.todoList.model.ListOfTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.UUID;

@Repository
public class ListOfTasksImpl implements ListOfTasksDAO {
	@Autowired(required = false)
	private JdbcTemplate jdbcTemplate;

    @Override
    public String insert(ListOfTasks listOfTasks) {
		String guidOfListOfTasks = UUID.randomUUID().toString();
		if (listOfTasks.getGuid() != null) guidOfListOfTasks = listOfTasks.getGuid();

        String sql = "INSERT INTO LIST_OF_TASKS (GUID, USER_GUID, FAVOURITES, NAME, DESCRIPTION) VALUES (?, ?, ?, ?, ?);";
		jdbcTemplate.update(sql, guidOfListOfTasks, listOfTasks.getUserGuid(),
				listOfTasks.getFavourites(), listOfTasks.getName(), listOfTasks.getDescription());

		return guidOfListOfTasks;
    }

    @Override
    public void insertBatch(Collection<ListOfTasks> listOfTasks) {
        for (ListOfTasks listOfTask : listOfTasks) insert(listOfTask);
    }

    @Override
    public void insertBatchSQL(String sql) {
        jdbcTemplate.batchUpdate(sql);
    }

    @Override
    public ListOfTasks findListOfTasksByGuid(String guidOfListOfTasks) {
        String sql = "SELECT * FROM LIST_OF_TASKS WHERE GUID = ?;";
		return jdbcTemplate.queryForObject(sql, new ListOfTasksRowMapper(), guidOfListOfTasks);
    }

	@Override
	public Collection<ListOfTasks> findAll() {
		String sql = "SELECT * FROM LIST_OF_TASKS;";
		return jdbcTemplate.query(sql, new ListOfTasksRowMapper());
	}

    @Override
    public int findTotalListOfTasks() {
        String sql = "SELECT COUNT(*) FROM LIST_OF_TASKS;";
        Number number = jdbcTemplate.queryForObject(sql, new Object[]{}, Integer.class);
        return (number != null ? number.intValue() : 0);
    }

	@Override
	public String findGuidOfOwner(String guidOfListOfTasks) {
		String sqlSelectOwnerForListOfTasks = "SELECT USER_GUID FROM LIST_OF_TASKS WHERE GUID = ?;";
		return String.valueOf(jdbcTemplate.query(sqlSelectOwnerForListOfTasks, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				return resultSet.getString("USER_GUID");
			}
		}, guidOfListOfTasks));
	}

	@Override
	public void update(String oldGuidOfListOfTasks, ListOfTasks listOfTasks) {
		String sql = "UPDATE LIST_OF_TASKS SET GUID = ?, USER_GUID = ?, FAVOURITES = ?, NAME = ?, DESCRIPTION = ? WHERE GUID = ?;";
		jdbcTemplate.update(sql, listOfTasks.getGuid(), listOfTasks.getUserGuid(), listOfTasks.getFavourites(),
				listOfTasks.getName(), listOfTasks.getDescription(), oldGuidOfListOfTasks);
	}

	@Override
	public void delete(String guidOfListOfTasks) {
		String sql = "DELETE FROM LIST_OF_TASKS WHERE GUID = ?;";
		jdbcTemplate.update(sql, guidOfListOfTasks);
	}
}