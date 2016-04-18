package database.todoList.services;

import database.todoList.model.Colleague;

import java.util.Collection;

public interface ColleagueService {
	void insert(Colleague colleague);
	void insertBatch(Collection<Colleague> colleagues);
	void insertBatchSQL(String sql);

	Collection<String> findGuidOfColleagues(String userGuid);
	Collection<Colleague> findColleaguesByUserGuid(String userGuid);
	Collection<Colleague> findAll();
	int findTotalColleague();

	void delete(String userGuid, String guidOfColleague);
	void delete(String userGuid, Collection<String> guidOfColleagues);
}
