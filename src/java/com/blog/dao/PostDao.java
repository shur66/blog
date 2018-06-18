package com.blog.dao;

import com.blog.model.Post;
import com.blog.model.PostComment;
import com.blog.model.User;

import java.util.List;

/**
 * Created by ShurA on 14.06.2018.
 * Интерфейс для ДАО постов и комментариев
 */
public interface PostDao {
    void create(Post post);
    void create(PostComment postComment);
    void update(Post post);
    void delete(Post post);
    void delete(PostComment postComment);
    Post getPostById(Integer postId);
    List<Post> getPostList();

}
