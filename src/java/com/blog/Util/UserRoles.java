package com.blog.Util;

/**
 * Created by ShurA on 12.06.2018.
 * Роли пользователя
 */
public enum UserRoles {
    USER("USER","Пользователь"),
    ADMIN("ADMIN","Администратор");
    String code;
    String name;

    UserRoles(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
