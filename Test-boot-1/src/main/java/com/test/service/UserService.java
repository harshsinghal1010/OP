package com.test.service;

import java.util.List;

import com.test.entity.User;
import com.test.utill.APiStatus;

public interface UserService {

	public APiStatus<User> addUser(User user);
	public User getUserById(int id);
	public User getUserByEmail(String email);
	public APiStatus<User> login(User user);
	public APiStatus<User> deleteUser(int id);
	public APiStatus<User> updateUser(User user);
	public List<User> getAllUser();
}
