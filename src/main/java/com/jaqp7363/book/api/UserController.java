package com.jaqp7363.book.api;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jaqp7363.book.model.Role;
import com.jaqp7363.book.model.User;
import com.jaqp7363.book.repository.UserRepository;
import com.jaqp7363.book.service.AuthProvider;
import com.jaqp7363.book.util.HashUtil;
import com.jaqp7363.book.vo.UserVO;

@RestController
@RequestMapping("/auth")
public class UserController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthProvider authProvider;
	
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserVO userVo) {
        if(userRepository.existsByUserid(userVo.getLoginId())) {
            throw new RuntimeException("이미 사용중인 ID입니다.");
        }
        
        User user = new User();
        user.setUserid(userVo.getLoginId());
        user.setPassword(HashUtil.sha256(userVo.getPassword()));
        user.setEmail(userVo.getEmail());
        user.setRole(Role.USER);
        User result = userRepository.save(user);
        return new ResponseEntity<User>(result, HttpStatus.CREATED);
    }
    
    @PostMapping("/existsByLoginId")
    public @ResponseBody boolean existsByLoginId(@RequestBody Map<String,String> param) {
    	return userRepository.existsByUserid(param.get("loginId"));
    }
}
