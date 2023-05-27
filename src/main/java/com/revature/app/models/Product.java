package com.revature.app.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Product {

    private String id;
    private String name;
    private String price;
    private String onHand;
    private String departmentId;

}
