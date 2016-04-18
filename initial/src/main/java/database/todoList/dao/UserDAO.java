package database.todoList.dao;

import database.todoList.model.User;

import java.util.Collection;

public interface UserDAO {
    void insert(User user);
    void insertBatch(Collection<User> users);
    void insertBatchSQL(String sql);

    User findUserByGuid(String guid);
    Collection<User> find(Collection<String> guides);
    Collection<User> findAll();
    int findCountOfUsers();

    void update(String guid, User user);

    void delete(String guid);
	void delete(Collection<String> guides);
}