package com.notification.repository;

import com.notification.model.Category;
import com.notification.model.Channel;
import com.notification.model.User;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepository {

    private static final List<User> USERS = Arrays.asList(
            User.builder()
                    .id(1L)
                    .name("Alice Johnson")
                    .email("alice@example.com")
                    .phoneNumber("+1555123456")
                    .channels(Arrays.asList(Channel.SMS, Channel.EMAIL))
                    .subscribed(Arrays.asList(Category.SPORTS, Category.MOVIES))
                    .build(),
            User.builder()
                    .id(2L)
                    .name("Bob Smith")
                    .email("bob@example.com")
                    .phoneNumber("+1555987654")
                    .channels(Arrays.asList(Channel.PUSH, Channel.EMAIL))
                    .subscribed(List.of(Category.FINANCE))
                    .build(),
            User.builder()
                    .id(3L)
                    .name("Charlie Davis")
                    .email("charlie@example.com")
                    .phoneNumber("+1555000111")
                    .channels(Arrays.asList(Channel.SMS, Channel.PUSH))
                    .subscribed(Arrays.asList(Category.SPORTS, Category.FINANCE, Category.MOVIES))
                    .build(),
            User.builder()
                    .id(4L)
                    .name("Diana Prince")
                    .email("diana@example.com")
                    .phoneNumber("+1555444333")
                    .channels(List.of(Channel.EMAIL))
                    .subscribed(List.of(Category.MOVIES))
                    .build()
    );

    public static List<User> getAllUsers() {
        return USERS;
    }

}
