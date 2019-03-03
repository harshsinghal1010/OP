package com.test.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.entity.User;
import com.test.repository.UserRepository;
import com.test.service.UserService;
import com.test.utill.APiStatus;
import com.test.utill.Login;
import com.test.utill.ResponseMessage;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	

	
	@Override
	public APiStatus<User> addUser(User user) {
		// TODO Auto-generated method stub
		
		if(userRepo.findByEmailAndDeletedFalse(user.getEmail())==null)
		{
		User u = userRepo.save(user);
		
		
		return (u!=null) ?
			 new APiStatus<>(ResponseMessage.SUCCESS,ResponseMessage.REGISTER_SUCCESS,user):
			 new APiStatus<>(ResponseMessage.FAILED,ResponseMessage.REGISTER_FAILED,null);
		}
		else {
			return new APiStatus<>(ResponseMessage.FAILED,ResponseMessage.EMAIL_EXIST,null);
		}
		
	
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		

		return userRepo.findByIdAndDeletedFalse(id);
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub

		return userRepo.findByEmailAndDeletedFalse(email);
	}

	@Override
	public APiStatus<User> login(Login login) {
		// TODO Auto-generated method stub

		User user2=userRepo.findByEmailAndDeletedFalse(login.getEmail());
		if(user2!=null) {
			
			return (login.getPassword().equals(user2.getPassword())) ?
					 new APiStatus<>(ResponseMessage.SUCCESS,ResponseMessage.LOGIN_SUCCESS,user2):
					 new APiStatus<>(ResponseMessage.FAILED,ResponseMessage.LOGIN_ERROR,null);
		}else {
			return new APiStatus<>(ResponseMessage.FAILED,ResponseMessage.LOGIN_ERROR,null);
		}
		
			}
	

	@Override
	public APiStatus<User> deleteUser(int id) {
		// TODO Auto-generated method stub

		User u= userRepo.findByIdAndDeletedFalse(id);
		if(u!=null) {
			u.setDeleted(true);
			userRepo.save(u);
			return new APiStatus<>(ResponseMessage.SUCCESS,ResponseMessage.DELETE_SUCCESS,u);
		}else {

		return new APiStatus<>(ResponseMessage.FAILED,ResponseMessage.USER_NOT_FOUND,null);
	}
	}
	
	
	
	@Override
	public APiStatus<User> updateUser(int id,User user) {
		// TODO Auto-generated method stub

			
			User u = userRepo.findByIdAndDeletedFalse(id);
			
			if(u!=null)
			{
			u.setAddress(user.getAddress());	
			u.setAge(user.getAge());
			u.setMobile(user.getMobile());
			u.setName(user.getName());
			
			userRepo.save(u);
			return new APiStatus<>(ResponseMessage.SUCCESS,ResponseMessage.UPDATE_SUCCESS,u);

			}
			else
			return new APiStatus<>(ResponseMessage.FAILED,ResponseMessage.INVALID_ID,null);
}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub

		return userRepo.findByDeletedFalse();//hello
	}

}
