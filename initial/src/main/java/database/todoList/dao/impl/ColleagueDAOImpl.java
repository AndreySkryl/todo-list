package database.todoList.dao.impl;

import database.todoList.dao.ColleagueDAO;
import database.todoList.mappers.ColleagueRowMapper;
import database.todoList.model.Colleague;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Repository
public class ColleagueDAOImpl implements ColleagueDAO {
	@Autowired(required = false)
	private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(Colleague colleague) {
        String sql = "INSERT INTO COLLEAGUE (USER_GUID, COLLEAGUE_GUID) VALUES (?, ?);";
        jdbcTemplate.update(sql, colleague.getUserGuid(), colleague.getColleagueGuid());
    }

    @Override
    public void insertBatch(Collection<Colleague> colleagues) {
        for (Colleague colleague : colleagues) insert(colleague);
    }

    @Override
    public void insertBatchSQL(String sql) {
        jdbcTemplate.batchUpdate(sql);
    }

    @Override
    public Collection<String> findGuidOfColleagues(String userGuid) {
        String sql = "SELECT COLLEAGUE_GUID FROM COLLEAGUE WHERE USER_GUID = ?;";
        return jdbcTemplate.query(sql, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet resultSet, int i) throws SQLException {
				String colleagueGuid = resultSet.getString("COLLEAGUE_GUID");
				return colleagueGuid;
			}
		}, userGuid);
    }

    @Override
    public Collection<Colleague> findColleaguesByUserGuid(String userGuid) {
        String sql = "SELECT * FROM COLLEAGUE WHERE USER_GUID = ?;";
        return jdbcTemplate.query(sql, new ColleagueRowMapper(), userGuid);
    }

    @Override
    public Collection<Colleague> findAll() {
        String sql = "SELECT * FROM COLLEAGUE;";
        return jdbcTemplate.query(sql, new ColleagueRowMapper());
    }

    @Override
    public int findTotalColleague() {
        String sql = "SELECT COUNT(*) FROM COLLEAGUE;";
        Number number = jdbcTemplate.queryForObject(sql, new Object[]{}, Integer.class);
        return (number != null ? number.intValue() : 0);
    }

	@Override
	public void delete(String userGuid, String guidOfColleague) {
		String sql = "DELETE FROM COLLEAGUE WHERE USER_GUID = ? AND COLLEAGUE_GUID = ?;";
		jdbcTemplate.update(sql, userGuid, guidOfColleague);
	}

	@Override
	public void delete(String userGuid, Collection<String> guidOfColleagues) {
		for (String guidOfColleague : guidOfColleagues) delete(userGuid, guidOfColleague);
	}
}