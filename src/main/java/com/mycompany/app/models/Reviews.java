package com.mycompany.app.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class Reviews {

    private String id;
    private String comment;
    private int rating;
    private String userId;
    private String productId;
}
