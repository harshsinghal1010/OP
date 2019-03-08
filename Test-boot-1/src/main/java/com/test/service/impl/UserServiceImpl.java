package com.test.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.test.entity.User;
import com.test.repository.UserRepository;
import com.test.service.EmailService;
import com.test.service.UserService;
import com.test.utill.APiStatus;
import com.test.utill.Login;
import com.test.utill.ResponseMessage;
import com.test.utill.Utill;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private EmailService emailService;
	

	@Override
	public APiStatus<User> addUser(User user) {
		// TODO Auto-generated method stub

		if (userRepo.findByEmailAndDeletedFalse(user.getEmail()) == null) {
			if (userRepo.findByUserNameAndDeletedFalse(user.getUserName()) == null) {
				if (userRepo.findByMobileAndDeletedFalse(user.getMobile()) == null) {
					//user.setConfirmationToken(UUID.randomUUID().toString());
					User u = userRepo.save(user);

					if(u != null) {
						emailService.sendMail(u);
							return new APiStatus<>(ResponseMessage.SUCCESS, ResponseMessage.REGISTER_SUCCESS, user);
					}else {	
							return new APiStatus<>(ResponseMessage.FAILED, ResponseMessage.REGISTER_FAILED, null);
			
					}
				} else {
					return new APiStatus<>(ResponseMessage.FAILED, ResponseMessage.MOBILE_EXIST, null);
				}
			} else {
				return new APiStatus<>(ResponseMessage.FAILED, ResponseMessage.USERNAME_EXIST, null);
			}
		}

		else {
			return new APiStatus<>(ResponseMessage.FAILED, ResponseMessage.EMAIL_EXIST, null);
		}

	}

	@Override
	public APiStatus<User> getUserById(int id) {
		// TODO Auto-generated method stub
         User user=userRepo.findByIdAndDeletedFalse(id);
         if(user !=null) {
        	 user.setEnable(true);
        	 userRepo.save(user);
        	 return new APiStatus<User>(ResponseMessage.SUCCESS,ResponseMessage.USER_CONFIRM, user);
         }else
		return new APiStatus<User>(ResponseMessage.FAILED, ResponseMessage.USER_NOT_FOUND, null);
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub

		return userRepo.findByEmailAndDeletedFalse(email);
	}

	@Override
	public APiStatus<User> login(Login login) {
		// TODO Auto-generated method stub

		User user2 = userRepo.findByEmailOrUserNameAndDeletedFalse(login.getEmail(), login.getEmail());
		if (user2 != null) {
            if(user2.getEnable() == true) {
			return (login.getPassword().equals(user2.getPassword()))
					? new APiStatus<>(ResponseMessage.SUCCESS, ResponseMessage.LOGIN_SUCCESS, user2)
					: new APiStatus<>(ResponseMessage.FAILED, ResponseMessage.LOGIN_ERROR, null);
            }else {
            	return new APiStatus<>(ResponseMessage.FAILED, ResponseMessage.USER_NOT_CONFIRM, null);
            }
            
		} else {

			return new APiStatus<>(ResponseMessage.FAILED, ResponseMessage.LOGIN_ERROR, null);
		}

	}

	@Override
	public APiStatus<User> deleteUser(int id) {
		// TODO Auto-generated method stub

		User u = userRepo.findByIdAndDeletedFalse(id);
		if (u != null) {
			u.setDeleted(true);
			userRepo.save(u);
			return new APiStatus<>(ResponseMessage.SUCCESS, ResponseMessage.DELETE_SUCCESS, u);
		} else {

			return new APiStatus<>(ResponseMessage.FAILED, ResponseMessage.USER_NOT_FOUND, null);
		}
	}

	@Override
	public APiStatus<User> updateUser(int id, User user) {
		// TODO Auto-generated method stub

		User u = userRepo.findByIdAndDeletedFalse(id);

		if (u != null) {
			u.setAddress(user.getAddress());
			u.setAge(user.getAge());
			u.setMobile(user.getMobile());
			u.setName(user.getName());

			userRepo.save(u);
			return new APiStatus<>(ResponseMessage.SUCCESS, ResponseMessage.UPDATE_SUCCESS, u);

		} else
			return new APiStatus<>(ResponseMessage.FAILED, ResponseMessage.INVALID_ID, null);
	}

	@Override
	public List<User> getAllUser(int page, int limit) {
	
		return userRepo.findByDeletedFalse(PageRequest.of(page, limit));// hello

	}
	
	@Override
	public List<User> getAllUsers() {
	
		return userRepo.findByDeletedFalse();// hello

	}
	
/*	@Override
	public List<User> getAllUsers() {
	
		return userRepo.findByDeletedFalse();// hello

	}*/

	@Override
	public String imageUpload(MultipartFile file) {
		// TODO Auto-generated method stub
		
    
		String filename = Utill.generateUniqueFileName() + file.getOriginalFilename();
		String path = ResponseMessage.UPLOAD_FOLDER + filename;
		File cf = new File(path);
		try {
			cf.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(cf);
			outputStream.write(file.getBytes());
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			filename = null;
		}

		return filename;
	
	}


}
