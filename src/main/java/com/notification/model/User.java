package com.notification.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class User {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private List<Category> subscribed;
    private List<Channel> channels;

}
