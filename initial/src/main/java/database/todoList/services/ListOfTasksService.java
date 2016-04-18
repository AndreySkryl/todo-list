package database.todoList.services;

import database.todoList.exceptions.UserIsNotOwnerOfListOfTasksException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public interface ListOfTasksService {
	HttpStatus delete(String guidOfListOfTasks, String guidOfUser) throws UserIsNotOwnerOfListOfTasksException;
}
