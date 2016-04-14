package database.todoList.controllers;

import database.todoList.dao.ListOfTasksDAO;
import database.todoList.dao.UserAndListOfTasksDAO;
import database.todoList.model.ListOfTasks;
import database.todoList.model.User;
import database.todoList.model.UserAndListOfTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static java.util.Collections.EMPTY_LIST;

@RestController
@RequestMapping("/list_of_tasks")
public class ListOfTasksController {
	@Autowired private ListOfTasksDAO listOfTasksDAO;
	@Autowired private UserAndListOfTasksDAO userAndListOfTasksDAO;

	@RequestMapping(value = "/add/one", consumes = "application/json", method = RequestMethod.POST)
	public HttpStatus newListOfTasks(@RequestBody ListOfTasks listOfTasks) {
		try {
			String guidOfListOfTasks = listOfTasksDAO.insert(listOfTasks);
			userAndListOfTasksDAO.insert(new UserAndListOfTasks(listOfTasks.getUserGuid(), guidOfListOfTasks));
			return HttpStatus.OK; // Response.SC_OK;
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return HttpStatus.BAD_REQUEST;
	}

	@RequestMapping(value = "/add/some", consumes = "application/json", method = RequestMethod.POST)
	public HttpStatus newListsOfTasks(@RequestBody Collection<ListOfTasks> listOfTasksCollection) {
		try {
			listOfTasksDAO.insertBatch(listOfTasksCollection);
			return HttpStatus.CREATED;
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return HttpStatus.BAD_REQUEST;
	}

	@RequestMapping(value = "/get/one", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<ListOfTasks> getListOfTasks(@RequestParam(ListOfTasks.GUID_OF_LIST_Of_TASKS) String guid) {
		try {
			ListOfTasks listOfTasks = listOfTasksDAO.findListOfTasksByGuid(guid);
			return new ResponseEntity<>(listOfTasks, HttpStatus.OK);
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(new ListOfTasks(), HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/get/all", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Collection<ListOfTasks>> getAllListOfTasks(@RequestParam(User.GUID_OF_USER) String guidOfUser) {
		try {
			Collection<ListOfTasks> listOfTasksCollection = listOfTasksDAO.findAll(guidOfUser);
			return new ResponseEntity<>(listOfTasksCollection, HttpStatus.OK);
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<Collection<ListOfTasks>>(EMPTY_LIST, HttpStatus.BAD_REQUEST);
	}
}
