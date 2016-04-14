package database.todoList.dao;

import database.todoList.model.ListOfTasks;

import java.util.Collection;

public interface ListOfTasksDAO {
    String insert(ListOfTasks listOfTasks);
    void insertBatch(Collection<ListOfTasks> listOfTasks);
    void insertBatchSQL(String sql);

    ListOfTasks findListOfTasksByGuid(String guid);
    Collection<ListOfTasks> findAll(String guidOfUser);

    int findTotalListOfTasks();
}
