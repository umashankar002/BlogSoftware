package com.myblog1.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncodePassword {

    public static void main(String[] args) {
        PasswordEncoder encoder=new BCryptPasswordEncoder();
        System.out.println(encoder.encode("admin"));
    }
}
