package com.blog.dao;

import com.blog.model.Post;
import com.blog.model.PostComment;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ShurA on 14.06.2018.
 * ДАО для постов
 */
@Repository
public class PostDaoImp implements PostDao {

    @Autowired
    SessionFactory sessionFactory;

    /**
     * Создание поста
     * @param post
     */
    public void create(Post post) {
        sessionFactory.getCurrentSession().saveOrUpdate(post);
    }

    /**
     * Создание комментария к посту
     * @param postComment
     */
    public void create(PostComment postComment) {
        sessionFactory.getCurrentSession().saveOrUpdate(postComment);
    }

    /**
     * Изменение поста
     * @param post пост
     */
    public void update(Post post) {
        sessionFactory.getCurrentSession().update(post);
        sessionFactory.getCurrentSession().flush();
    }

    /**
     * Удаление поста
     * @param post
     */
    public void delete(Post post) {
        sessionFactory.getCurrentSession().delete(post);
        sessionFactory.getCurrentSession().flush();
    }

    /**
     * Удаление комментария
     * @param postComment
     */
    public void delete(PostComment postComment) {
        sessionFactory.getCurrentSession().delete(postComment);
        sessionFactory.getCurrentSession().flush();
    }

    /**
     * Получение поста по id
     */
    public Post getPostById(Integer postId) {
      return (Post)sessionFactory.getCurrentSession().get(Post.class, postId);
    }

    /**
     * Получение списка постов
     */
    public List<Post> getPostList() {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from Post order by date desc");
        return (List<Post>)query.list();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
