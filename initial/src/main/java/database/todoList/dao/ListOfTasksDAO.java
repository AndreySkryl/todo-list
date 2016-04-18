package database.todoList.dao;

import database.todoList.model.ListOfTasks;

import java.util.Collection;

public interface ListOfTasksDAO {
    String insert(ListOfTasks listOfTasks);
    void insertBatch(Collection<ListOfTasks> listOfTasks);
    void insertBatchSQL(String sql);

    ListOfTasks findListOfTasksByGuid(String guidOfListOfTasks);
    Collection<ListOfTasks> findAll();
    int findTotalListOfTasks();

    String findGuidOfOwner(String guidOfListOfTasks);

    void update(String oldGuidOfListOfTasks, ListOfTasks listOfTasks);

    void delete(String guidOfListOfTasks);
}
