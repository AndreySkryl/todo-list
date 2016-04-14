package database.todoList.dao.impl;

import database.todoList.dao.UserDAO;
import database.todoList.mappers.UserRowMapper;
import database.todoList.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public class UserDAOImpl implements UserDAO {
	@Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(User user) {
        String sql = "INSERT INTO USER (LOGIN, LASTNAME, FIRSTNAME, PASSWORD, EMAIL) VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, user.getLogin(), user.getLastName(), user.getFirstName(),
				user.getPassword(), user.geteMail());
    }

    @Override
    public void insertBatch(Collection<User> users) {
        for (User user : users) insert(user);
    }

    @Override
    public void insertBatchSQL(final String sql) {
        jdbcTemplate.batchUpdate(sql);
    }

    @Override
    public User findUserByGuid(String guid) {
        String sql = "SELECT * FROM USER WHERE GUID = ?;";
		return jdbcTemplate.queryForObject(sql,  new UserRowMapper(), guid);
    }

	@Override
	public Collection<User> find(Collection<String> guides) {
		Collection<User> listOfUser = new ArrayList<>();
		for (String guid : guides) {
			User user = findUserByGuid(guid);
			listOfUser.add(user);
		}
		return listOfUser;
	}

	@Override
    public Collection<User> findAll() {
        String sql = "SELECT * FROM USER;";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public int findTotalUsers() {
        String sql = "SELECT COUNT(*) FROM USER;";
        Number number = jdbcTemplate.queryForObject(sql, new Object[]{}, Integer.class);
        return (number != null ? number.intValue() : 0);
    }

    @Override
    public void update(String guidOfUser, User user) {
        String sql = "UPDATE USER SET LOGIN = ?, PASSWORD = ?, LASTNAME = ?, FIRSTNAME = ?, EMAIL = ? WHERE GUID = ?;";
        jdbcTemplate.update(sql, user.getLogin(), user.getPassword(), user.getLastName(), user.getFirstName(),
				user.geteMail(), guidOfUser);
    }

    @Override
    public void delete(String guid) {
        String sql = "DELETE FROM USER WHERE GUID = ?;";
        jdbcTemplate.update(sql, guid);
    }

	@Override
	public void delete(Collection<String> guides) {
		for (String guid : guides) delete(guid);
	}
}