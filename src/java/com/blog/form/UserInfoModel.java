package com.blog.form;

import com.blog.model.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ShurA on 14.06.2018.
 * Модель формы для информации пользователя
 */
public class UserInfoModel {
    @Getter @Setter
    User user;
    @Getter @Setter
    String goBack;
    @Getter @Setter
    String error;
}
