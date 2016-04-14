package database.todoList.controllers;

import database.todoList.dao.ColleagueDAO;
import database.todoList.dao.UserDAO;
import database.todoList.model.Colleague;
import database.todoList.model.User;
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
import java.util.List;

import static java.util.Collections.EMPTY_LIST;

@RestController
@RequestMapping("/colleague")
public class ColleagueController {
	@Autowired private ColleagueDAO colleagueDAO;
	@Autowired private UserDAO userDAO;

	@RequestMapping(value = "/add/one", method = RequestMethod.POST)
	public HttpStatus newColleague(@RequestParam(User.GUID_OF_USER) String guidOfUser,
								   @RequestParam(Colleague.GUID_OF_COLLEAGUE) String guidOfColleague) {
		Colleague colleague = new Colleague(guidOfUser, guidOfColleague);
		try {
			colleagueDAO.insert(colleague);
			return HttpStatus.OK;
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return HttpStatus.BAD_REQUEST;
	}

	@RequestMapping(value = "/add/some", consumes = "application/json", method = RequestMethod.POST)
	public HttpStatus newColleagues(@RequestParam(User.GUID_OF_USER) String guidOfUser,
									@RequestBody Collection<String> guidOfColleagues) {
		List<Colleague> listOfColleague = new ArrayList<>();
		for (String guidOfColleague : guidOfColleagues) listOfColleague.add(new Colleague(guidOfUser, guidOfColleague));
		try {
			colleagueDAO.insertBatch(listOfColleague);
			return HttpStatus.OK;
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return HttpStatus.BAD_REQUEST;
	}

	@RequestMapping(value = "/get/all/guides", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Collection<String>> getGuidOfColleagues(@RequestParam(User.GUID_OF_USER) String guidOfUser) {
		try {
			Collection<String> listOfGuidOfColleagues = colleagueDAO.findGuidOfColleagues(guidOfUser);
			return new ResponseEntity<>(listOfGuidOfColleagues, HttpStatus.OK);
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<Collection<String>>(EMPTY_LIST, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/get/all", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Collection<User>> getColleagues(@RequestParam(User.GUID_OF_USER) String guidOfUser) {
		Collection<String> listOfGuidOfColleagues = getGuidOfColleagues(guidOfUser).getBody();
		try {
			Collection<User> listOfColleagues = userDAO.find(listOfGuidOfColleagues);
			return new ResponseEntity<>(listOfColleagues, HttpStatus.OK);
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<Collection<User>>(EMPTY_LIST, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/delete/one", method = RequestMethod.DELETE)
	public HttpStatus deleteOneColleague(@RequestParam(User.GUID_OF_USER) String guidOfUser,
										 @RequestParam(Colleague.GUID_OF_COLLEAGUE) String guidOfColleague) {
		try {
			colleagueDAO.delete(guidOfUser, guidOfColleague);
			return HttpStatus.OK;
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return HttpStatus.BAD_REQUEST;
	}

	@RequestMapping(value = "/delete/some", consumes = "application/json", method = RequestMethod.DELETE)
	public HttpStatus deleteSomeColleagues(@RequestParam(User.GUID_OF_USER) String guidOfUser, @RequestBody Collection<String> guidOfColleagues) {
		try {
			colleagueDAO.delete(guidOfUser, guidOfColleagues);
			return HttpStatus.OK;
		} catch (DataAccessException exception) {
			System.err.println(exception.getMessage());
		}
		return HttpStatus.BAD_REQUEST;
	}

	@RequestMapping(value = "/delete/all", method = RequestMethod.DELETE)
	public HttpStatus deleteAllColleagues(@RequestParam(User.GUID_OF_USER) String guidOfUser) {
		Collection<String> listOfGuidOfColleagues = colleagueDAO.findGuidOfColleagues(guidOfUser);
		return deleteSomeColleagues(guidOfUser, listOfGuidOfColleagues);
	}
}