package database.todoList.dao;

import database.todoList.model.User;

import java.util.Collection;

public interface UserDAO {
    void insert(User user);
    void insertBatch(Collection<User> users);
    void insertBatchSQL(String sql);

    User findUserByGuid(String guid);
    String findUserLoginByGuid(String guid);
    Collection<User> findAll();

    int findTotalUsers();
}