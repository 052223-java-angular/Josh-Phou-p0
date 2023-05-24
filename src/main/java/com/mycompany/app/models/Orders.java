package com.mycompany.app.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class Orders {

    private String id;
    private String status;
    private String quantity;
    private String userId;
    private String productId;

}
