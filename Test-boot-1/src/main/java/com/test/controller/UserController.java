package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.entity.User;
import com.test.service.UserService;
import com.test.utill.APiStatus;
import com.test.utill.ResponseMessage;

@RestController
@RequestMapping("/api/user")
public class UserController<T> {

	@Autowired
	private UserService service;
	
	@PostMapping("/register")
	public ResponseEntity<?> saveUser(@RequestBody User user){
		APiStatus status = service.addUser(user);
		return (status.getStatus().equals(ResponseMessage.SUCCESS))? 
				ResponseEntity.status(HttpStatus.CREATED).body(status) : 
					ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(status);
	}
	
	@GetMapping("/getuser/id")
	public ResponseEntity<?> getOneUser(@RequestParam("userId") int id) {
		User u = service.getUserById(id);
		if(u!=null)
		return ResponseEntity.status(HttpStatus.OK).body(u);
		else
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResponseMessage.USER_EXIST);

	}
	
	@GetMapping("/getuser/email")
	public ResponseEntity<?> getOneUser(@RequestParam("userEmail") String email) {
		User u = service.getUserByEmail(email);
		if(u!=null)
		return ResponseEntity.status(HttpStatus.OK).body(u);
		else
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResponseMessage.USER_EXIST);

	}
	
	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@RequestBody User user){
		APiStatus status = service.login(user);
		return (status.getStatus().equals(ResponseMessage.SUCCESS))? 
				ResponseEntity.status(HttpStatus.CREATED).body(status) : 
					ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(status);
	}
	
	@GetMapping("/getuser/all")
	public ResponseEntity<?> getAll(){
		List<User> u = service.getAllUser();
		if(u!=null)
			return ResponseEntity.status(HttpStatus.OK).body(u);
			else
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResponseMessage.USER_EXIST);
}
	
	
	@GetMapping("/deleteuser/id")
	public ResponseEntity<?> deleteUser(@RequestParam("userId") Integer id) {
		APiStatus<User> status = service.deleteUser(id);
		return (status.getStatus().equals(ResponseMessage.SUCCESS))? 
				ResponseEntity.status(HttpStatus.CREATED).body(status) : 
					ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(status);
	}
	
	@PostMapping("/updateuser/id")
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		APiStatus<User> status = service.updateUser(user);
		return (status.getStatus().equals(ResponseMessage.SUCCESS))? 
				ResponseEntity.status(HttpStatus.CREATED).body(status) : 
					ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(status);
	}
	private ResponseEntity<?> successResponse(HttpStatus status , T body)
	{
		return ResponseEntity.status(status).body(body);
	}
	
	
	
}
