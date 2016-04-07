package database.todoList.dao;

import database.todoList.model.Task;

import java.util.List;

public interface TaskDAO {
    void insert(Task task);
    void insertBatch(List<Task> tasks);
    void insertBatchSQL(String sql);

    Task findTaskByGuid(String guid);
    List<Task> findAll();

    int findTotalTasks();
}
