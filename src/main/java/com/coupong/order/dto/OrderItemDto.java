package com.coupong.order.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class OrderItemDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String itemRid;

    private Integer quantity;

}
