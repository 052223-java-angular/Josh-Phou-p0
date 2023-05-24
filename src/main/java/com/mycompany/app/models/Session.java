package com.mycompany.app.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class Session {

    private String userId;
    private String username;
    private String role;


}
