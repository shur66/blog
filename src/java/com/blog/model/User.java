package com.blog.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ShurA on 10.06.2018.
 * Модель таблицы пользователя
 */
public class User {
    @Getter @Setter
    Integer id;
    @Getter @Setter
    String login;
    @Getter @Setter
    String name;
    @Getter @Setter
    String email;
    @Getter @Setter
    String password;
    @Getter @Setter
    Set<UserRole> userRole = new HashSet<UserRole>(0);
    @Getter @Setter
    Set<Post> posts = new HashSet<Post>(0);
}
