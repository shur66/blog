package com.blog.service;

import com.blog.Util.FormatUtil;
import com.blog.dao.PostDao;
import com.blog.model.Post;
import com.blog.model.PostComment;
import com.blog.model.User;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ShurA on 14.06.2018.
 * Сервис для постов
 */
@Service("postService")
public class PostService {

    @Autowired
    @Getter
    @Setter
    PostDao postDao;

    /**
     * Запрос всех постов
     * @return Список постов
     */
    @Transactional(readOnly = true)
    public List<Post> getPostList(){
        return postDao.getPostList();
    }

    /**
     * Запросить пост по id
     * @param postId id поста
     * @return Пост
     */
    @Transactional(readOnly = true)
    public Post getPostById(Integer postId){
        return postDao.getPostById(postId);
    }

    /**
     * Создание поста
     * @param post пост
     */
    @Transactional
    public void create(Post post){
        postDao.create(post);
    }

    /**
     * Создания комментария поста
     * @param postComment комментарий поста
     */
    @Transactional
    public void create(PostComment postComment){
        postDao.create(postComment);
    }

    /**
     * Изменение поста
     * @param post поста
     */
    @Transactional
    public void update(Post post){
        postDao.update(post);
    }

    /**
     * Удаление поста по id
     * @param postId id поста
     */
    @Transactional
    public void deletePostById(Integer postId){
        Post post = postDao.getPostById(postId);
        postDao.delete(post);
    }
    /**
     * Удаление комментария поста по id
     * @param postComment комментарий поста
     */
    @Transactional
    public void delete(PostComment postComment){
        postDao.delete(postComment);
    }
    /**
     * Удаление комментария поста по id
     * @param commentId id комментария
     */
    @Transactional
    public void deletePostCommentById(Integer commentId){
        PostComment postComment = new PostComment();
        postComment.setId(commentId);
        postDao.delete(postComment);
    }

    /**
     * Возвращает json комментариев поста
     * @param post Пост
     * @param user Пользователь
     *
     */
    public JSONArray getCommentsJson(Post post, User user) {
        JSONArray result = new JSONArray();
        for (PostComment postComment: post.getPostComments()){
            JSONObject comment = new JSONObject();
            comment.put("post_id", post.getId());
            comment.put("id", postComment.getId());
            comment.put("comment", postComment.getComment());
            comment.put("user_id", postComment.getUser().getId());
            comment.put("user_name", postComment.getUser().getName());
            comment.put("date", FormatUtil.formatDateTime(postComment.getDate()));
            comment.put("owner", post.getUser().getId() == user.getId());
            result.add(comment);
        }
        return result;
    }

}
