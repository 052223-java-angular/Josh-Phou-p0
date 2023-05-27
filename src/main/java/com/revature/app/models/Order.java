package com.revature.app.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class Order {

    private String id;
    private String status;
    private String quantity;
    private String userId;
    private String productId;

    private String orderId;
    private Product product;

}
