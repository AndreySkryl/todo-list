package database.todoList.services.impl;

import database.todoList.dao.ListOfTasksDAO;
import database.todoList.exceptions.UserIsNotOwnerOfListOfTasksException;
import database.todoList.services.ListOfTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class ListOfTasksServiceImpl implements ListOfTasksService {
	@Autowired private ListOfTasksDAO listOfTasksDAO;

	@Override
	public HttpStatus delete(String guidOfListOfTasks, String guidOfUser) throws UserIsNotOwnerOfListOfTasksException {
		String guidOfOwner = listOfTasksDAO.findGuidOfOwner(guidOfListOfTasks);

		if (!guidOfOwner.equals(guidOfUser)) {
			throw new UserIsNotOwnerOfListOfTasksException();
		}

		listOfTasksDAO.delete(guidOfListOfTasks);

		return HttpStatus.OK;
	}
}
