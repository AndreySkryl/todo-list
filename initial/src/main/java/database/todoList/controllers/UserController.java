package database.todoList.controllers;

import database.todoList.dao.UserDAO;
import database.todoList.model.User;
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
import java.util.Collections;

@RestController
@RequestMapping("/user")
public class UserController {
	public static final String GUID_OF_USER = "guidOfUser";

    @Autowired private UserDAO userDAO;

	@RequestMapping(value = "/add/one", consumes = "application/json", method = RequestMethod.POST)
	public int newUser(@RequestBody User user) {
		try {
			userDAO.insert(user);
			return Response.SC_CREATED;
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return Response.SC_CONFLICT;
	}

	@RequestMapping(value = "/add/some", consumes = "application/json", method = RequestMethod.POST)
	public int newUsers(@RequestBody Collection<User> users) {
		try {
			userDAO.insertBatch(users);
			return Response.SC_CREATED;
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return Response.SC_CONFLICT;
	}

	@RequestMapping(value = "/get/one", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@RequestParam(GUID_OF_USER) String guid) {
		try {
			User user = userDAO.findUserByGuid(guid);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(new User(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/get/all", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> getAllUsers() {
		try {
			return new ResponseEntity<>(userDAO.findAll(), HttpStatus.OK);
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<Collection<User>>(Collections.EMPTY_LIST, HttpStatus.CONFLICT);
    }

	@RequestMapping(value = "/get/all/count", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Integer> getCountOfUsers() {
		try {
			return new ResponseEntity<>(userDAO.findTotalUsers(), HttpStatus.OK);
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(0, HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/edit/one", consumes = "application/json", method = RequestMethod.PUT)
	public int updateUser(@RequestParam(GUID_OF_USER) String guidUser, @RequestBody User user) {
		try {
			userDAO.update(guidUser, user);
			return Response.SC_OK;
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return Response.SC_CONFLICT;
	}

	@RequestMapping(value = "/delete/one", method = RequestMethod.DELETE)
	public int deleteUser(@RequestParam(GUID_OF_USER) String guid) {
		try {
			userDAO.delete(guid);
			return Response.SC_OK;
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return Response.SC_CONFLICT;
	}

	@RequestMapping(value = "/delete/some", consumes = "application/json", method = RequestMethod.DELETE)
	public int deleteUsers(@RequestBody Collection<String> guides) {
		try {
			userDAO.delete(guides);
			return Response.SC_OK;
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return Response.SC_CONFLICT;
	}
}
