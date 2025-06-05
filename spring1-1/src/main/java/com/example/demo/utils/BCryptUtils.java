package com.example.demo.utils;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtils {
    public static String hash(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt());
    }

    public static boolean check(String plain, String hashed) {
        return BCrypt.checkpw(plain, hashed);
    }
}