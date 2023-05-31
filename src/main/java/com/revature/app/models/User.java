package com.revature.app.models;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class User {

    private String id;
    private String username;
    private String password;
    private String roleId;

    public User(String username, String password, String roleId) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.roleId = roleId;
    }

}
