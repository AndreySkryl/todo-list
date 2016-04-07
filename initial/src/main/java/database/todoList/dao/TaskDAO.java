package database.todoList.dao;

import database.todoList.model.Task;

import java.util.Collection;

public interface TaskDAO {
    void insert(Task task);
    void insertBatch(Collection<Task> tasks);
    void insertBatchSQL(String sql);

    Task findTaskByGuid(String guid);
    Collection<Task> findAll();

    int findTotalTasks();
}
