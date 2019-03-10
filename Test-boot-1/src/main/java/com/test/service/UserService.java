package com.test.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.test.entity.User;
import com.test.utill.APiStatus;
import com.test.utill.Login;

public interface UserService {

	public APiStatus<User> addUser(User user);
	public APiStatus<User> getUserById(int id);
	public APiStatus<User> verifyToken(String token);
	public User getUserByEmail(String email);
	public APiStatus<User> login(Login login);
	public APiStatus<User> deleteUser(int id);
	public APiStatus<User> updateUser(int id, User user);
	List<User> getAllUser(String type ,int page, int limit);
	public String imageUpload(MultipartFile file) throws IOException;

}
