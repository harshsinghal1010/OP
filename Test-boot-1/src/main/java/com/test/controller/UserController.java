package com.test.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.entity.User;
import com.test.service.UserService;
import com.test.utill.APiStatus;
import com.test.utill.Config;
import com.test.utill.Login;
import com.test.utill.ResponseMessage;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService service;

	  @RequestMapping("/hello")
	    public String sayHello() {
	        int i = 5 / 0;
	        return "hello Greetings !";
	    }
	  
	  @RequestMapping("/user")
	    public String user() {
	        return "Hello user Greetings !";
	    }
	
	@PostMapping("/register")
	public ResponseEntity<?> saveUser(@RequestBody User user) {
		// **************
		APiStatus status = service.addUser(user);
		return (status.getStatus().equals(ResponseMessage.SUCCESS))
				? ResponseEntity.status(HttpStatus.CREATED).body(status)
				: ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(status);
	}
	
	@GetMapping("/registrationConfirm")
	public ResponseEntity<?> userConfirm(@RequestParam("token") String token) {
		
		APiStatus<User> status = service.verifyToken(token);
		return (status.getStatus().equals(ResponseMessage.SUCCESS))
				? ResponseEntity.status(HttpStatus.CREATED).body(status)
				: ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(status);

		
	}

	@GetMapping("/getuser/id")//***************************************** only enable user come
	public ResponseEntity<?> getOneUser(@RequestParam("userId") int id) {
		APiStatus<User> u = service.getUserById(id);
		if (u != null)
			return ResponseEntity.status(HttpStatus.OK).body(u);
		else

			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResponseMessage.USER_NOT_FOUND);

	}

	@GetMapping("/getuser/email")

	public ResponseEntity<?> getOneUser(@RequestParam("email") String email) {

		User u = service.getUserByEmail(email);
		if (u != null)
			return ResponseEntity.status(HttpStatus.OK).body(u);
		else

			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResponseMessage.USER_NOT_FOUND);

	}

	@PostMapping("/login")

	public ResponseEntity<?> userLogin(@RequestBody Login login) {
		// done
		APiStatus status = service.login(login);
		return (status.getStatus().equals(ResponseMessage.SUCCESS))
				? ResponseEntity.status(HttpStatus.CREATED).body(status)
				: ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(status);
	}

	@GetMapping("/getuser/")
	public ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "-10") int page,
			@RequestParam(name = "limit", defaultValue = "-10") int limit) {

		List<User> u;
		if(page==-10 || limit==-10) {
			 u = service.getAllUser("ALL",page, limit);
		}
		
		else if(page >=0 && limit >0) {
			 u = service.getAllUser("PAGE",page, limit);
		}
		else {
			return errorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ResponseMessage.INVALID_INPUT);
		}
		return (!CollectionUtils.isEmpty(u))?  ResponseEntity.status(HttpStatus.OK).body(u):
			errorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ResponseMessage.USER_NOT_FOUND);
		
		
		
	}

	@DeleteMapping("/deleteuser/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
		APiStatus<User> status = service.deleteUser(id);
		return (status.getStatus().equals(ResponseMessage.SUCCESS))
				? ResponseEntity.status(HttpStatus.NO_CONTENT).body(status)
				: ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(status);
	}

	@PutMapping("/updateuser/{id}")
	public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User user) {
		APiStatus<User> status = service.updateUser(id, user);

		return (status.getStatus().equals(ResponseMessage.SUCCESS))
				? successResponse(HttpStatus.CREATED, status.getMessage(), status.getEntity())
				: errorResponse(HttpStatus.UNPROCESSABLE_ENTITY, status.getMessage());
	}

	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile) throws IOException {
		// only image upload
		if(uploadfile.getContentType().contains("image")) {
		System.err.println(uploadfile.getContentType());
		String name = service.imageUpload(uploadfile);

		return (name == null) ? errorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ResponseMessage.IMAGE_UPLOAD_ERROR)
				: successResponse(HttpStatus.OK, name, null);
	}else {
		return errorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ResponseMessage.INVALID_IMAGE);
	}
	}

	@GetMapping("download")
	private ResponseEntity<?> downloadFile(@RequestParam("filename") String filename) throws IOException {

		File file = new File(Config.UPLOAD_FOLDER + filename);
		if (file.exists()) {
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

			return ResponseEntity.status(HttpStatus.OK)
					// Content-Disposition
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
					// Content-Length
					.contentLength(file.length()).body(resource);

		} else
			return errorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "file not found");
	}

	private ResponseEntity<?> successResponse(HttpStatus status, String message, User body) {
		return ResponseEntity.status(status).body(new APiStatus(ResponseMessage.SUCCESS, message, body));
	}

	private ResponseEntity<?> errorResponse(HttpStatus status, String message) {
		return ResponseEntity.status(status).body(new APiStatus(ResponseMessage.FAILED, message, null));
	}

}
