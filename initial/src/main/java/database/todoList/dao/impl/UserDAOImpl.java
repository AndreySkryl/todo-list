package database.todoList.dao.impl;

import database.todoList.dao.UserDAO;
import database.todoList.mappers.UserRowMapper;
import database.todoList.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired(required = false)
    private JdbcTemplate jdbcTemplate;


    public void insert(User user) {
        String sql =
                "INSERT INTO USER " +
                "(LOGIN, LASTNAME, FIRSTNAME, PASSWORD, EMAIL) " +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                user.getLogin(),
                user.getLastName(), user.getFirstName(),
                user.getPassword(), user.geteMail());
    }

    public void insertBatch(List<User> users) {
        for (User user : users) {
            insert(user);
        }
    }

    public void insertBatchSQL(final String sql) {
        jdbcTemplate.batchUpdate(sql);
    }

    public User findUserByGuid(String guid) {
        String sql = "SELECT * FROM USER WHERE GUID = ?";
        User user = jdbcTemplate.queryForObject(sql,  new UserRowMapper(), guid);
        return user;
    }

    public String findUserLoginByGuid(String guid) {
        String sql = "SELECT LOGIN FROM USER WHERE GUID = ?";
        String login = jdbcTemplate.queryForObject(sql, String.class, guid);
        return login;
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM USER";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper());
        return users;
    }

    public int findTotalUsers() {
        String sql = "SELECT COUNT(*) FROM USER";
        Number number = jdbcTemplate.queryForObject(sql, new Object[]{}, Integer.class);
        return (number != null ? number.intValue() : 0);
    }
}