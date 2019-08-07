package com.asiapay.configuration;

import com.asiapay.service.IUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppleUserDetailService implements UserDetailsService {

    private static final Log logger = LogFactory.getLog(AppleUserDetailService.class);

    @Resource
    private IUserService userService;

    /**
     *
     * @param userName 根据用户名获取用户 - 用户的角色、权限等信息
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        UserDetails userDetails = null;

        if(userName==null|| "".equals(userName)){
            return null;
        }

        try {
            com.asiapay.model.User user=userService.selectByName(userName);

            Collection<GrantedAuthority> authList = getAuthorities();
            userDetails = new User(userName, user.getPassword().toLowerCase(),true,true,true,true,authList);
        }catch (Exception e) {
            e.printStackTrace();
        }


        return userDetails;


    }

    /**
     * 获取用户的角色权限
     * @return
     */

    private Collection<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authList;
    }
}
