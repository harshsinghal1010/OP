package com.test.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.test.entity.User;
import com.test.repository.UserRepository;
import com.test.service.UserService;
import com.test.utill.APiStatus;
import com.test.utill.Login;
import com.test.utill.ResponseMessage;
import com.test.utill.Utill;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public APiStatus<User> addUser(User user) {
		// TODO Auto-generated method stub

		if (userRepo.findByEmailAndDeletedFalse(user.getEmail()) == null) {
			if (userRepo.findByUserNameAndDeletedFalse(user.getUserName()) == null) {
				if (userRepo.findByMobileAndDeletedFalse(user.getMobile()) == null) {
					User u = userRepo.save(user);

					return (u != null)
							? new APiStatus<>(ResponseMessage.SUCCESS, ResponseMessage.REGISTER_SUCCESS, user)
							: new APiStatus<>(ResponseMessage.FAILED, ResponseMessage.REGISTER_FAILED, null);
				} else {
					return new APiStatus<>(ResponseMessage.FAILED, ResponseMessage.Mobile_EXIST, null);
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

		User user2 = userRepo.findByEmailOrUserNameAndDeletedFalse(login.getEmail(), login.getEmail());
		if (user2 != null) {

			return (login.getPassword().equals(user2.getPassword()))
					? new APiStatus<>(ResponseMessage.SUCCESS, ResponseMessage.LOGIN_SUCCESS, user2)
					: new APiStatus<>(ResponseMessage.FAILED, ResponseMessage.LOGIN_ERROR, null);
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
		// TODO Auto-generated method stub
	if(page>=0 && limit >=1) {
		return userRepo.findByDeletedFalse(PageRequest.of(page, limit));// hello
	}
	return null;
	}

	@Override
	public String imageUpload(MultipartFile file) {
		// TODO Auto-generated method stub
		//String regex= "image/([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
		String extention=file.getContentType();
     if(extention.equalsIgnoreCase("image/jpeg") || extention.equalsIgnoreCase("image/png") || extention.equalsIgnoreCase("image/jpg")) {
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
	}else {
		return "Not valid extension";
		}
	}

}
