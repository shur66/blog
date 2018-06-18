package com.blog.service;

import com.blog.Util.AuthUtil;
import com.blog.secure.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ShurA on 12.06.2018.
 * Сервис авторизации 
 */
@Service("authService")
public class AuthService {
    @Autowired
    MyUserDetailsService detailsService;
    @Autowired
    UserService userService;

    @Transactional
    public void authUser(HttpSession session, String login, String password) throws Exception {
        session.setAttribute("authUser",userService.findUserByLogin(login));
        UserDetails userDetails = detailsService.loadUserByUsername(login);
        UsernamePasswordAuthenticationToken token = null;
        if (userDetails != null) {
            userDetails.getAuthorities();
            token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        }
        if (userDetails == null || !userDetails.getPassword().equals(AuthUtil.stringToSHA1(password))) {
            throw new UsernameNotFoundException("Логин или пароль введены неверно");
        }
        SecurityContextHolder.getContext().setAuthentication(token);
        session.setAttribute("authUser",userService.findUserByLogin(login));
    }

    public void setDetailsService(MyUserDetailsService detailsService) {
        this.detailsService = detailsService;
    }
}
