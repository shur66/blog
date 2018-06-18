package com.blog.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by ShurA on 11.06.2018.
 * Модель таблицы комментариев поста
 */
public class PostComment {
    @Getter @Setter
    Integer id;
    @Getter @Setter
    Post post;
    @Getter @Setter
    User user;
    @Getter @Setter
    String comment;
    @Getter @Setter
    Date date;
}
