package com.mycompany.app.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class Products {

    private String id;
    private String name;
    private String price;
    private String onHand;
    private String departmentId;

}
