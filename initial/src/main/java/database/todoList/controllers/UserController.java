package database.todoList.controllers;

import database.todoList.dao.UserDAO;
import database.todoList.model.User;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDAO userDAO;

	@RequestMapping(value = "/add_user", consumes = "application/json", method = RequestMethod.POST)
	public int newUser(@RequestBody User user) {
		try {
			userDAO.insert(user);
			return Response.SC_CREATED;
		} catch (DataAccessException exception) {
			System.out.println(exception.getMessage());
		}
		return Response.SC_CONFLICT;
	}

	@RequestMapping(value = "/add_users", consumes = "application/json", method = RequestMethod.POST)
	public int newUsers(@RequestBody Collection<User> users) {
		try {
			userDAO.insertBatch(users);
			return Response.SC_CREATED;
		} catch (DataAccessException exception) {
			System.out.println(exception.getMessage());
		}
		return Response.SC_CONFLICT;
	}

	@RequestMapping(value = "/{guid}", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("guid") String guid) {
		try {
			User user = userDAO.findUserByGuid(guid);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (DataAccessException exception) {
			System.out.println(exception.getMessage());
		}
		return new ResponseEntity<>(new User(), HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/all", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> getAllUsers() {
		try {
			return new ResponseEntity<>(userDAO.findAll(), HttpStatus.OK);
		} catch (DataAccessException exception) {
			System.out.println(exception.getMessage());
		}
		return new ResponseEntity<Collection<User>>(Collections.EMPTY_LIST, HttpStatus.CONFLICT);
    }

	@RequestMapping(value = "/count_of_users", method = RequestMethod.GET)
	public ResponseEntity<Integer> getCountOfUsers() {
		try {
			return new ResponseEntity<>(userDAO.findTotalUsers(), HttpStatus.OK);
		} catch (DataAccessException exception) {
			System.out.println(exception.getMessage());
		}
		return new ResponseEntity<>(0, HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/edit/{guid}", method = RequestMethod.PUT)
	public int updateUser(@PathVariable("guid") String guidUser, @RequestBody User user) {
		try {
			userDAO.update(guidUser, user);
			return Response.SC_OK;
		} catch (DataAccessException exception) {
			System.out.println(exception.getMessage());
		}
		return Response.SC_CONFLICT;
	}

	@RequestMapping(value = "/delete/{guid}", method = RequestMethod.DELETE)
	public int deleteUser(@PathVariable("guid") String guid) {
		try {
			userDAO.delete(guid);
			return Response.SC_OK;
		} catch (DataAccessException exception) {
			System.out.println(exception.getMessage());
		}
		return Response.SC_CONFLICT;
	}

	@RequestMapping(value = "/delete/", consumes = "application/json", method = RequestMethod.DELETE)
	public int deleteUsers(@RequestBody Collection<String> guides) {
		try {
			userDAO.delete(guides);
			return Response.SC_OK;
		} catch (DataAccessException exception) {
			System.out.println(exception.getMessage());
		}
		return Response.SC_CONFLICT;
	}
}
