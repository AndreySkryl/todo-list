package database.todoList.controllers;

import database.todoList.model.User;
import database.todoList.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired private UserService userService;

	@RequestMapping(value = "/add/one", consumes = "application/json", method = RequestMethod.POST)
	public HttpStatus newUser(@RequestBody User user) {
		try {
			userService.insertUser(user);
			return HttpStatus.CREATED;
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@RequestMapping(value = "/add/some", consumes = "application/json", method = RequestMethod.POST)
	public HttpStatus newUsers(@RequestBody Collection<User> users) {
		try {
			userService.insertUsers(users);
			return HttpStatus.CREATED;
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@RequestMapping(value = "/get/one", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<User> getUserByGuid(@RequestParam(User.GUID_OF_USER) String guid) {
		try {
			User user = userService.findUserByGuid(guid);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(new User(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

    @RequestMapping(value = "/get/all", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> getAllUsers() {
		try {
			return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<Collection<User>>(Collections.EMPTY_LIST, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/get/all/count", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Integer> getCountOfUsers() {
		try {
			return new ResponseEntity<>(userService.findCountOfUsers(), HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/edit/one", consumes = "application/json", method = RequestMethod.PUT)
	public HttpStatus updateUser(@RequestParam(User.GUID_OF_USER) String guidOfUser, @RequestBody User user) {
		try {
			userService.updateUser(guidOfUser, user);
			return HttpStatus.OK;
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@RequestMapping(value = "/delete/one", method = RequestMethod.DELETE)
	public HttpStatus deleteUser(@RequestParam(User.GUID_OF_USER) String guid) {
		try {
			userService.deleteUser(guid);
			return HttpStatus.OK;
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@RequestMapping(value = "/delete/some", consumes = "application/json", method = RequestMethod.DELETE)
	public HttpStatus deleteUsers(@RequestBody Collection<String> guides) {
		try {
			userService.deleteUsers(guides);
			return HttpStatus.OK;
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
