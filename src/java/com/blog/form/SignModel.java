package com.blog.form;

import com.blog.model.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ShurA on 13.06.2018.
 * Модель для формы регистрации пользователя
 */
public class SignModel {
    @Getter @Setter
    User user;
    @Getter @Setter
    String passwordConfirm;
    @Getter @Setter
    String error;
}
