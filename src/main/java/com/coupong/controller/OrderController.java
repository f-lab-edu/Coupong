package com.coupong.controller;

import com.coupong.entity.Member;
import com.coupong.entity.Role;
import com.coupong.order.dto.OrderDto;
import com.coupong.order.dto.OrderHistDto;
import com.coupong.order.dto.OrderResultDto;
import com.coupong.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public OrderResultDto orderByGuest(@RequestBody OrderDto orderDto) {
        // TODO : 멤버 처리하기
        Member member = new Member(1L, "user@gmail.com"
                , "password", "nickname", "127.0.0.0", "01012341234"
                , LocalDateTime.now(), LocalDateTime.now(), null, new Role(1L, "Member"));

        return orderService.order(member, orderDto);
    }

    @GetMapping("/guest")
    public List<OrderHistDto> getOrderInfoByGuest(@RequestBody String phoneNumber) {
        return orderService.getOrderInfoByGuest(phoneNumber);
    }

    @PostMapping("/member")
    public OrderResultDto orderByMember(@RequestBody OrderDto orderDto) {
        // TODO : 멤버 정보 가져오기
        Member member = new Member(1L, "user@gmail.com"
                , "password", "nickname", "127.0.0.0", "01012341234"
                , LocalDateTime.now(), LocalDateTime.now(), null, new Role(1L, "Member"));

        return orderService.order(member, orderDto);
    }

    @GetMapping("/member")
    public List<OrderHistDto> getOrderInfoByMember(@RequestBody String orderRid) {
        return orderService.getOrderInfoByMember(orderRid);
    }

}
