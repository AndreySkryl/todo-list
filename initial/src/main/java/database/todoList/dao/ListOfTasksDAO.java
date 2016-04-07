package database.todoList.dao;

import database.todoList.model.ListOfTasks;

import java.util.List;

public interface ListOfTasksDAO {
    void insert(ListOfTasks listOfTasks);
    void insertBatch(List<ListOfTasks> listOfTasks);
    void insertBatchSQL(String sql);

    ListOfTasks findListOfTasksByGuid(String guid);
    List<ListOfTasks> findAll();

    int findTotalListOfTasks();
}
