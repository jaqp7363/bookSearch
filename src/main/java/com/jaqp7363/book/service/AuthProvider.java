package com.jaqp7363.book.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jaqp7363.book.model.SecurityUser;
import com.jaqp7363.book.util.HashUtil;


@Component("authProvider")
public class AuthProvider implements AuthenticationProvider {

	@Autowired
    UserService userService;
	
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userid = authentication.getName();
        String password = authentication.getCredentials().toString();
        password = HashUtil.sha256(password);
        if(userid == null || "".equals(userid)) {
        	return null;
        }
        
        // email에 맞는 user가 없거나 비밀번호가 맞지 않는 경우.
        if (!password.equals(userService.findByUserid(userid).getPassword())) {
            return null;
        }
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("USER"));
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userid, password, roles);
		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
