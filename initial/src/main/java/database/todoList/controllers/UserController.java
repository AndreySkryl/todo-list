package database.todoList.controllers;

import database.todoList.dao.UserDAO;
import database.todoList.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDAO userDAO;

	@RequestMapping(value = "/add", consumes = "application/json", method = RequestMethod.POST)
	public ResponseEntity<User> newUser(@RequestBody User user) {
		if (user != null) userDAO.insert(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/{guid}", produces = "application/json", method = RequestMethod.GET)
    public User getUser(@PathVariable("guid") String guid) {
        User user = userDAO.findUserByGuid(guid);
		return user;
    }

    @RequestMapping(value = "/all", produces = "application/json", method = RequestMethod.GET)
    public Collection<User> getAllUsers() {
        return userDAO.findAll();
    }
}
