package database.todoList.dao.impl;

import database.todoList.dao.ListOfTasksDAO;
import database.todoList.mappers.ListOfTasksRowMapper;
import database.todoList.model.ListOfTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public class ListOfTasksImpl implements ListOfTasksDAO {
	@Autowired(required = false)
	private JdbcTemplate jdbcTemplate;

    @Override
    public String insert(final ListOfTasks listOfTasks) {
		String guidOfListOfTasks = UUID.randomUUID().toString();

        String sql = "INSERT INTO LIST_OF_TASKS (GUID, USER_GUID, FAVOURITES, NAME, DESCRIPTION) VALUES (?, ?, ?, ?);";
		jdbcTemplate.update(sql, guidOfListOfTasks, listOfTasks.getUserGuid(),
				listOfTasks.getFavourites(), listOfTasks.getName(), listOfTasks.getDescription());

		return guidOfListOfTasks;
		/*KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				//PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				PreparedStatement ps = connection.prepareStatement(
						"INSERT INTO LIST_OF_TASKS (USER_GUID, FAVOURITES, NAME, DESCRIPTION) VALUES (?, ?, ?, ?);",
						new String[]{"USER_GUID"}); //Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, listOfTasks.getUserGuid());
				ps.setInt(2, listOfTasks.getFavourites());
				ps.setString(3, listOfTasks.getName());
				ps.setString(4, listOfTasks.getDescription());
				return ps;
			}
		}, keyHolder);

		return keyHolder.getKey().toString();*/
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
    public ListOfTasks findListOfTasksByGuid(String guid) {
        String sql = "SELECT * FROM LIST_OF_TASKS WHERE USER_GUID = ?;";
		return jdbcTemplate.queryForObject(sql, new ListOfTasksRowMapper(), guid);
    }

	@Override
	public Collection<ListOfTasks> findAll(String guidOfUser) {
		throw new RuntimeException("123");
//		String sql = "SELECT LIST_OF_TASKS.* FROM LIST_OF_TASKS INNER JOIN ;
//		return jdbcTemplate.query(sql, new ListOfTasksRowMapper());
	}

    @Override
    public int findTotalListOfTasks() {
        String sql = "SELECT COUNT(*) FROM LIST_OF_TASKS;";
        Number number = jdbcTemplate.queryForObject(sql, new Object[]{}, Integer.class);
        return (number != null ? number.intValue() : 0);
    }
}