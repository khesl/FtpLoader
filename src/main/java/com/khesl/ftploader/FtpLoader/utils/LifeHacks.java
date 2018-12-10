package com.khesl.ftploader.FtpLoader.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import java.security.Principal;

public class LifeHacks {

    /**способы получить инфу о текущем пользователе.*/
    public void checkFile(Authentication authentication, Principal principal) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println(authentication.getName());
        System.out.println("-----------------");
        System.out.println(principal.getName());
        System.out.println("-----------------");
        System.out.println(user.getUsername());

    }
}
