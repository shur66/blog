package com.blog.form;

import com.blog.model.Post;
import com.blog.model.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ShurA on 13.06.2018.
 * Модель для формы поста
 */
public class PostModel {
    @Getter @Setter
    Post post;
    @Getter @Setter
    String action;
    @Getter @Setter
    String error;
}
