package com.test.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


	public User findByEmailAndDeletedFalseAndEnableTrue (String email);
	public User findByEmailAndDeletedFalse(String email);
	public User findByUserNameAndDeletedFalse(String username);
	public User findByMobileAndDeletedFalse(Long mobile);
	public User findByEmailOrUserNameAndDeletedFalse(String email,String username);
	public User findByPasswordAndDeletedFalse (String password);

	public User findByIdAndDeletedFalse(int id);
	public User findByIdAndDeletedFalseAndEnableTrue(int id);
	public List<User> findByDeletedFalseAndEnableTrue();
	public List<User> findByDeletedFalseAndEnableTrue(Pageable pageable);
	
	public User findByTokenAndDeletedFalse(String token);

}
