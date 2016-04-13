package database.todoList.controllers;

import database.todoList.dao.ColleagueDAO;
import database.todoList.dao.UserDAO;
import database.todoList.model.Colleague;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/colleague")
public class ColleagueController {
	public static final String GUID_OF_USER = "guidOfUser";
	public static final String GUID_OF_COLLEAGUE = "guidOfColleague";

	@Autowired private ColleagueDAO colleagueDAO;
	@Autowired private UserDAO userDAO;

	@RequestMapping(value = "/add/one", method = RequestMethod.POST)
	public int newColleague(@RequestParam(GUID_OF_USER) String guidOfUser, @RequestParam(GUID_OF_COLLEAGUE) String guidOfColleague) {
		Colleague colleague = new Colleague(guidOfUser, guidOfColleague);
		try {
			colleagueDAO.insert(colleague);
			return Response.SC_CREATED;
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return Response.SC_BAD_REQUEST;
	}

	@RequestMapping(value = "/add/some", consumes = "application/json", method = RequestMethod.POST)
	public int newColleagues(@RequestParam(GUID_OF_USER) String guidOfUser, @RequestBody Collection<String> guidOfColleagues) {
		List<Colleague> listOfColleague = new ArrayList<>();
		for (String guidOfColleague : guidOfColleagues) listOfColleague.add(new Colleague(guidOfUser, guidOfColleague));
		try {
			colleagueDAO.insertBatch(listOfColleague);
			return Response.SC_OK;
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return Response.SC_BAD_REQUEST;
	}

	@RequestMapping(value = "/get/all/guides", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Collection<String>> getGuidOfColleagues(@RequestParam(GUID_OF_USER) String guidOfUser) {
		try {
			Collection<String> listOfGuidOfColleagues = colleagueDAO.findGuidOfColleagues(guidOfUser);
			return new ResponseEntity<>(listOfGuidOfColleagues, HttpStatus.OK);
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<Collection<String>>(Collections.EMPTY_LIST, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/get/all", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Collection<User>> getColleagues(@RequestParam(GUID_OF_USER) String guidOfUser) {
		Collection<String> listOfGuidOfColleagues = getGuidOfColleagues(guidOfUser).getBody();
		try {
			Collection<User> listOfColleagues = userDAO.find(listOfGuidOfColleagues);
			return new ResponseEntity<>(listOfColleagues, HttpStatus.OK);
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<Collection<User>>(Collections.EMPTY_LIST, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/delete/one", method = RequestMethod.DELETE)
	public int deleteOneColleague(@RequestParam(GUID_OF_USER) String guidOfUser, @RequestParam(GUID_OF_COLLEAGUE) String guidOfColleague) {
		try {
			colleagueDAO.delete(guidOfUser, guidOfColleague);
			return Response.SC_OK;
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return Response.SC_BAD_REQUEST;
	}

	@RequestMapping(value = "/delete/some", consumes = "application/json", method = RequestMethod.DELETE)
	public int deleteSomeColleagues(@RequestParam(GUID_OF_USER) String guidOfUser, @RequestBody Collection<String> guidOfColleagues) {
		try {
			colleagueDAO.delete(guidOfUser, guidOfColleagues);
			return Response.SC_OK;
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return Response.SC_BAD_REQUEST;
	}

	@RequestMapping(value = "/delete/all", method = RequestMethod.DELETE)
	public int deleteAllColleagues(@RequestParam(GUID_OF_USER) String guidOfUser) {
		Collection<String> listOfGuidOfColleagues = colleagueDAO.findGuidOfColleagues(guidOfUser);
		return deleteSomeColleagues(guidOfUser, listOfGuidOfColleagues);
	}
}