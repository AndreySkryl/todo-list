package database.todoList.dao;

import database.todoList.model.Colleague;

import java.util.List;

public interface ColleagueDAO {
    void insert(Colleague colleague);
    void insertBatch(List<Colleague> colleagues);
    void insertBatchSQL(String sql);

    List<Colleague> findListOfColleaguesByUserGuid(String userGuid);
    List<Colleague> findAll();

    int findTotalColleague();
}
