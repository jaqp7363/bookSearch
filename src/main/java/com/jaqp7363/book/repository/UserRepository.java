package com.jaqp7363.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jaqp7363.book.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Boolean existsByUserid(String userid);
	public User findByUserid(String userid);
}