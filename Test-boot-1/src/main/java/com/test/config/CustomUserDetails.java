package com.test.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.entity.Role;
import com.test.entity.User;
import com.test.repository.UserRepository;

@Service
public class CustomUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	
    	User user = repo.findByEmailOrUserNameAndDeletedFalse(username, username);
    	if(user==null)
    		return null;
    	else {
    	
    		List<GrantedAuthority> list = new ArrayList();
    		for(Role role : user.getRoles())
    				list.add(new SimpleGrantedAuthority(role.getRole()));
    				
    		return new org.springframework.security.core.userdetails
    				.User(user.getEmail(),
    						user.getPassword(), 
    						list);
    	}
    	
    	
       
    }
}