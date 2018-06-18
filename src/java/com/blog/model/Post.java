package com.blog.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ShurA on 10.06.2018.
 * Модель таблицы поста
 */
public class Post {
    @Getter @Setter
    Integer id;
    @Getter @Setter
    User user;
    @Getter @Setter
    String caption;
    @Getter @Setter
    String body;
    @Getter @Setter
    String tags;
    @Getter @Setter
    Date date;
    @Getter @Setter
    Set<PostComment> postComments = new HashSet<PostComment>(0);
}
