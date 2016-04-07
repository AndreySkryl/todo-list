package database.todoList.controllers;

import database.todoList.dao.UserDAO;
import database.todoList.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/user")
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

    @RequestMapping(name = "/{guid}", produces = "application/json", method = RequestMethod.GET)
    public User getUser(@PathVariable String guid) {
        User user = userDAO.findUserByGuid(guid);
		System.out.println(guid);
		return user;
    }

    @RequestMapping(value = "/", produces = "application/json", method = RequestMethod.GET)
    public Collection<User> getAllUsers() {
        return new ArrayList<User>(userDAO.findAll());
    }
}
