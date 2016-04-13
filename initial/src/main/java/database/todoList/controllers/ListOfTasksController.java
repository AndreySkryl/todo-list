package database.todoList.controllers;

import database.todoList.dao.ListOfTasksDAO;
import database.todoList.model.ListOfTasks;
import org.apache.catalina.connector.Response;
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

@RestController
@RequestMapping("/list_of_tasks")
public class ListOfTasksController {
	public static final String GUID_OF_LIST_Of_TASKS = "guidOfListOfTasks";

	@Autowired private ListOfTasksDAO listOfTasksDAO;

	@RequestMapping(value = "/add/one", consumes = "application/json", method = RequestMethod.POST)
	public int newListOfTasks(@RequestBody ListOfTasks listOfTasks) {
		try {
			listOfTasksDAO.insert(listOfTasks);
			return Response.SC_OK;
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return Response.SC_BAD_REQUEST;
	}

	@RequestMapping(value = "/add/some", consumes = "application/json", method = RequestMethod.POST)
	public int newListsOfTasks(@RequestBody Collection<ListOfTasks> listOfTasksCollection) {
		try {
			listOfTasksDAO.insertBatch(listOfTasksCollection);
			return Response.SC_CREATED;
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return Response.SC_CONFLICT;
	}

	@RequestMapping(value = "/get/one", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<ListOfTasks> getListOfTasks(@RequestParam(GUID_OF_LIST_Of_TASKS) String guid) {
		try {
			ListOfTasks listOfTasks = listOfTasksDAO.findListOfTasksByGuid(guid);
			return new ResponseEntity<>(listOfTasks, HttpStatus.OK);
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(new ListOfTasks(), HttpStatus.CONFLICT);
	}
}
