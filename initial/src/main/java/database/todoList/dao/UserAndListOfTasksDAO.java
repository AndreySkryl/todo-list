package database.todoList.dao;

import database.todoList.model.UserAndListOfTasks;

import java.util.List;

public interface UserAndListOfTasksDAO {
    void insert(UserAndListOfTasks userAndListOfTasks);
    void insertBatch(List<UserAndListOfTasks> listOfUserAndListOfTasks);
    void insertBatchSQL(String sql);

    List<UserAndListOfTasks> findUserAndListOfTasksByListOfTasksGuid(String listOfTasksGuid);

    List<UserAndListOfTasks> findAll();

    int findTotalUserAndListOfTasks();
}
