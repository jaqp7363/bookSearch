package com.jaqp7363.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaqp7363.book.model.User;
import com.jaqp7363.book.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	@Transactional(readOnly=true)
	public User findByUserid(String userid) {
		return userRepository.findByUserid(userid);
	}

}
