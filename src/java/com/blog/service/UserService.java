package com.blog.service;

import com.blog.Util.AuthUtil;
import com.blog.Util.UserRoles;
import com.blog.dao.UserDao;
import com.blog.model.User;
import com.blog.model.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ShurA on 12.06.2018.
 * Сервис по работе с Пользователем
 */
@Service("userService")
@Transactional
public class UserService {
    @Autowired
    @Getter
    @Setter
    UserDao userDao;

    /**
     * Поиск пользователя по логину
     * @param login логин
     * @return Модель пользователя
     */
    @Transactional(readOnly = true)
    public User findUserByLogin(String login){
        return userDao.findUserByLogin(login);
    }

    /**
     * Поиск пользователя по идентификатору
     * @param id Идентификатор пользователя
     * @return Модель пользователя
     */
    @Transactional(readOnly = true)
    public User findUserById(Integer id){
        return userDao.findUserById(id);
    }

    /**
     * Создание нового пользователя
     * @param user Пользователь
     */
    @Transactional
    public void createUser(User user) throws Exception {

        try {
            user.setPassword(AuthUtil.stringToSHA1(user.getPassword()));
            UserRole userRole = new UserRole();
            userRole.setRole(UserRoles.USER.getCode());
            userRole.setUser(user);
            Set<UserRole> userRoles = new HashSet<UserRole>();
            userRoles.add(userRole);
            user.setUserRole(userRoles);
            userDao.create(user);
            userDao.create(userRole);
        }catch (ConstraintViolationException ex){
            throw new Exception("Пользователь c логином "+user.getLogin()+" уже существует");
        }
    }

}
