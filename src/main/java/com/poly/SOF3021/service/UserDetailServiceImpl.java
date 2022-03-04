package com.poly.SOF3021.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.poly.SOF3021.model.Account;
import com.poly.SOF3021.repository.AccountRepository;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.getAccountByUsernameAndActivated(username, true);
        if (account == null){
        	throw new UsernameNotFoundException("Account " + username + " was not found");
        }else {
        	session.setAttribute("username", account.getUsername());
        }
        List<GrantedAuthority> authorityList =new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(account.getRole().toUpperCase()));
        UserDetails userDetails = new User(account.getUsername(), account.getPassword(), authorityList);
        return userDetails;
    }

}