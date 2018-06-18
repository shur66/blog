package com.blog.secure;

import com.blog.dao.UserDao;
import com.blog.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ShurA on 10.06.2018.
 * Сервис для авторизации пользователя spring security
 */

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Set<UserRole> userRoles = new HashSet<UserRole>();
        com.blog.model.User user = userDao.findUserByLogin(login);
        if (user!= null){
            userRoles = user.getUserRole();
            List<GrantedAuthority> authorities = buildUserAuthority(userRoles);
            return buildUserForAuthentication(user, authorities);
        }else{
            return null;
        }

    }

    // Converts com.blog.model.User to
    // org.springframework.security.core.userdetails.User
    private User buildUserForAuthentication(com.blog.model.User user,
                                            List<GrantedAuthority> authorities) {
        return new User(user.getLogin(), user.getPassword(),
                true, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // Build user's authorities
        for (UserRole userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        }

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
