package database.todoList.dao;

import database.todoList.model.ListOfTasks;

import java.util.Collection;

public interface ListOfTasksDAO {
    void insert(ListOfTasks listOfTasks);
    void insertBatch(Collection<ListOfTasks> listOfTasks);
    void insertBatchSQL(String sql);

    ListOfTasks findListOfTasksByGuid(String guid);
    Collection<ListOfTasks> findAll();

    int findTotalListOfTasks();
}
