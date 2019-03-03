package com.test.service;

import java.util.List;

import com.test.entity.User;
import com.test.utill.APiStatus;
import com.test.utill.Login;

public interface UserService {

	public APiStatus<User> addUser(User user);
	public User getUserById(int id);
	public User getUserByEmail(String email);
	public APiStatus<User> login(Login login);
	public APiStatus<User> deleteUser(int id);
	public APiStatus<User> updateUser(int id, User user);
	public List<User> getAllUser();
}
