package database.todoList.services;

import database.todoList.model.User;

import java.util.Collection;

public interface UserService {
	void insertUser(User user);
	void insertUsers(Collection<User> users);

	User findUserByGuid(String guid);
	Collection<User> findUsersByGuid(Collection<String> guides);
	Collection<User> findAllUsers();
	int findCountOfUsers();

	void updateUser(String guid, User user);

	void deleteUser(String guid);
	void deleteUsers(Collection<String> guides);
}
