package com.jaqp7363.book.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
	
	@Id
    @Column(name = "userid")
	private String userid;
	
    @Email
    @Size(min = 5, max = 30)
    @Column(length = 30, nullable = false)
    private String email;
    
    @Size(min = 6, max = 64)
    @Column(name = "password_hash", length = 64, nullable = false)
    private String password;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
}
