package com.blog.dao;

import com.blog.model.User;
import com.blog.model.UserRole;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ShurA on 10.06.2018.
 * Интерфейс ДАО Пользователя
 */

public interface UserDao {

    void create(User user);
    void create(UserRole userRole);
    void update(User user);
    void delete(User user);
    User findUserByLogin(String login);
    User findUserById(Integer id);
}
