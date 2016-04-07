package database.todoList.controllers;

import database.todoList.dao.UserDAO;
import database.todoList.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserDAO userDAO;

	/*@RequestMapping(name = "/printWelcome", method = RequestMethod.GET)
	public String printWelcome() {
		return "Spring 3 MVC - Hello world";
	}

    @RequestMapping(name = "/user", method = RequestMethod.GET)
    public ResponseEntity<String> getUserOnHTTP() {
        List<User> users = userDAO.findAll();
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }*/

    @RequestMapping(value = "/all-users", produces = "application/json", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }
}
