package com.revature.app.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class User {

    private String id;
    private String name;
    private String password;
    private String roleId;

}
