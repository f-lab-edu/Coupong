package com.coupong.order.service;

import com.coupong.entity.Order;
import com.coupong.order.dto.OrderDto;
import com.coupong.order.dto.OrderHistDto;

import java.util.List;

public interface OrderService {

    List<OrderHistDto> getOrderInfoByGuest(String phoneNumber);

    List<OrderHistDto> getOrderInfoByMember(String orderRid);
    String order(OrderDto orderDto);

}
