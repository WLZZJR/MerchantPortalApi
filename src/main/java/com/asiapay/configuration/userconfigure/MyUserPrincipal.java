package com.asiapay.configuration.userconfigure;

import com.asiapay.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


/**
 * 自定义UserDetails
 * 自定义user 信息，方便获取查询数据库信息
 */
public class MyUserPrincipal implements UserDetails {

    private User user;

    public MyUserPrincipal(User user) {
        this.user = user;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getUserName();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
