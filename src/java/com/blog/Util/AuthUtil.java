package com.blog.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ShurA on 11.06.2018.
 * Утилиты для работы с авторизацией
 */
public class AuthUtil {
    /**
     * Перевод строки в SHA1 формат
     * @param input Строка
     * @return Конвертированная строка в SHA1 формате
     * @throws NoSuchAlgorithmException
     */
    public static String stringToSHA1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString().toUpperCase();
    }
}
