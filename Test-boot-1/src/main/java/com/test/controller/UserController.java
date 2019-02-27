package com.test.controller;

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
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("user not found");

	}
	
	@GetMapping("/getuser/email")
	public ResponseEntity<?> getOneUser(@RequestParam("userEmail") String email) {
		User u = service.getUserByEmail(email);
		if(u!=null)
		return ResponseEntity.status(HttpStatus.OK).body(u);
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user not found");

	}
	
	private ResponseEntity<?> successResponse(HttpStatus status , T body)
	{
		return ResponseEntity.status(status).body(body);
	}
	
	
	
}
