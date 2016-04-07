package database.todoList.dao;

import database.todoList.model.User;

import java.util.List;

public interface UserDAO {
    void insert(User user);
    void insertBatch(List<User> users);
    void insertBatchSQL(String sql);

    User findUserByGuid(String guid);
    String findUserLoginByGuid(String guid);
    List<User> findAll();

    int findTotalUsers();
}