package com.asiapay.configuration.userconfigure;

import com.asiapay.model.User;
import com.asiapay.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * 自定义MyUserDetailsService
 * 重写loadUserByUsername
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    IUserService userService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userService.selectByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);


    }
}
