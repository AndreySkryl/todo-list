package database.todoList.dao;

import database.todoList.model.Colleague;

import java.util.Collection;

public interface ColleagueDAO {
    void insert(Colleague colleague);
    void insertBatch(Collection<Colleague> colleagues);
    void insertBatchSQL(String sql);

    Collection<Colleague> findListOfColleaguesByUserGuid(String userGuid);
    Collection<Colleague> findAll();

    int findTotalColleague();
}
