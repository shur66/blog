package com.blog.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by ShurA on 10.06.2018.
 * Модель таблицы ролей пользователя
 */
public class UserRole implements Serializable {
    @Getter @Setter
    User user;
    @Getter @Setter
    Integer id;
    @Getter @Setter
    Integer user_id;
    @Getter @Setter
    String role;
}
