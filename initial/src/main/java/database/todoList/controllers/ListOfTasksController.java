package database.todoList.controllers;

import database.todoList.dao.ListOfTasksDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/list_of_tasks")
public class ListOfTasksController {
	@Autowired private ListOfTasksDAO listOfTasksDAO;

//	@RequestMapping(value = "/add/one")
}
