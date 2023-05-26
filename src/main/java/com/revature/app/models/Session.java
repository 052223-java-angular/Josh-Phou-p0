package com.revature.app.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class Session {

    private String id;
    private String username;
    private String roleId;

    public void setSession(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.roleId = user.getRoleId();
    }

}
