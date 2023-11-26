package com.coupong.controller;

import com.coupong.order.dto.OrderDto;
import com.coupong.order.dto.OrderHistDto;
import com.coupong.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/guest")
    public String orderByGuest(@RequestBody OrderDto orderDto) {
        // TODO : 멤버 처리하기

        return orderService.order(orderDto);
    }

    @GetMapping("/guest")
    public List<OrderHistDto> getOrderInfoByGuest(@RequestBody String phoneNumber) {
        return orderService.getOrderInfoByGuest(phoneNumber);
    }

    @PostMapping("/member")
    public String orderByMember(@RequestBody OrderDto orderDto) {
        // TODO : 멤버 정보 가져오기

        return orderService.order(orderDto);
    }

    @GetMapping("/member")
    public List<OrderHistDto> getOrderInfoByMember(@RequestBody String orderRid) {
        return orderService.getOrderInfoByMember(orderRid);
    }

}
