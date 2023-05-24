package com.mycompany.app.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class Users {

    private String id;
    private String name;
    private String password;
    private String roleId;

}
