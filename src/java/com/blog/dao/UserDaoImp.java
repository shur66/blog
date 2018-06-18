package com.blog.dao;

import com.blog.model.User;
import com.blog.model.UserRole;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by ShurA on 12.06.2018.
 */
@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

    public void create(User user){
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    public void create(UserRole userRole) {sessionFactory.getCurrentSession().saveOrUpdate(userRole);}

    public void update(User user){
        sessionFactory.getCurrentSession().update(user);
        sessionFactory.getCurrentSession().flush();
    }

    public void delete(User user){
        sessionFactory.getCurrentSession().delete(user);
        sessionFactory.getCurrentSession().flush();
    }

    /**
     * Поиска пользователя по логину
     * @param login Логин пользователя
     * @return Найденного пользователя
     */
    @SuppressWarnings("unchecked")
    public User findUserByLogin(String login){
        User result = null;
        Query query  = sessionFactory.getCurrentSession()
                .createQuery("from User where login =:login")
                .setParameter("login", login);
        Object object = query.uniqueResult();
        if (object != null){
            result = (User)object;
        }
        return result;
    }
    /**
     * Поиск пользователя по идентификатору
     * @param id Идентификатор пользователя
     * @return Модель пользователя
     */
    public User findUserById(Integer id) {
        return (User)sessionFactory.getCurrentSession().get(User.class, id);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
