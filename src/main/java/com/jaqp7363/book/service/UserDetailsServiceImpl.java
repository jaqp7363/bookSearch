package com.jaqp7363.book.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jaqp7363.book.model.SecurityUser;
import com.jaqp7363.book.model.User;
import com.jaqp7363.book.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        User user = userRepository.findByUserid(userid);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (user.getRole().equals("ADMIN")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
        } else {
            grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        }

        return new SecurityUser(user);
    }

}
