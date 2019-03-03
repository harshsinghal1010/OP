package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.entity.User;
import com.test.service.UserService;
import com.test.utill.APiStatus;
import com.test.utill.Login;
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

			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResponseMessage.USER_NOT_FOUND);

	}
	
	@GetMapping("/getuser/email")

	public ResponseEntity<?> getOneUser(@RequestParam("email") String email) {

		User u = service.getUserByEmail(email);
		if(u!=null)
		return ResponseEntity.status(HttpStatus.OK).body(u);
		else

			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResponseMessage.USER_NOT_FOUND);

	}
	
	@PostMapping("/login")

	public ResponseEntity<?> userLogin(@RequestBody Login login){
		
		APiStatus status = service.login(login);
		return (status.getStatus().equals(ResponseMessage.SUCCESS))? 
				ResponseEntity.status(HttpStatus.CREATED).body(status) : 
					ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(status);
	}

	
	@GetMapping("/getuser/")
	public ResponseEntity<?> getAll(){
		List<User> u = service.getAllUser();
		if(u!=null)
			return ResponseEntity.status(HttpStatus.OK).body(u);
			else
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResponseMessage.USER_NOT_FOUND);
}


	
	@DeleteMapping("/deleteuser/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
		APiStatus<User> status = service.deleteUser(id);
		return (status.getStatus().equals(ResponseMessage.SUCCESS))? 
				ResponseEntity.status(HttpStatus.NO_CONTENT).body(status) : 
					ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(status);
	}
	

	
	@PutMapping("/updateuser/{id}")
	public ResponseEntity<?> updateUser(@PathVariable int id,@RequestBody User user) {
		APiStatus<User> status = service.updateUser(id,user);

		return (status.getStatus().equals(ResponseMessage.SUCCESS))? 
				ResponseEntity.status(HttpStatus.CREATED).body(status) : 
					ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(status);
	}
	private ResponseEntity<?> successResponse(HttpStatus status , T body)
	{
		return ResponseEntity.status(status).body(body);
	}
	
	
	
}
