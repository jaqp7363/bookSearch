package com.jaqp7363.book.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserVO {
	@NotBlank
	private String loginId;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String password;
}
