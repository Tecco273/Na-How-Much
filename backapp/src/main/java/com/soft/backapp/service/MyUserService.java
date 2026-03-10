package com.soft.backapp.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soft.backapp.model.MyUser;
import com.soft.backapp.repository.MyUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyUserService implements UserDetailsService {
    private final MyUserRepository myUserRepository;;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        MyUser user = myUserRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }

    public UserDetails loadUserByEmail(String email)
            throws UsernameNotFoundException {
        MyUser user = myUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }

    public MyUser getProfile(){
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return getUserByUsername(username);
    }

    public void save(MyUser user) {
        myUserRepository.save(user);
    }

    public MyUser getUserByUsername(String username) {
        return myUserRepository.findByUsername(username).orElse(null);
    }

    public MyUser getUserById(Long id) {
        return myUserRepository.findById(id).orElse(null);
    }

    
}
