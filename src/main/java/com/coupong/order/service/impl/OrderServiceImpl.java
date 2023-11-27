package com.coupong.order.service.impl;

import com.coupong.config.exception.NotFoundException;
import com.coupong.coupon.service.CouponService;
import com.coupong.entity.*;
import com.coupong.item.repository.ItemRepository;
import com.coupong.order.dto.OrderDto;
import com.coupong.order.dto.OrderHistDto;
import com.coupong.order.dto.OrderItemDto;
import com.coupong.order.dto.OrderResultDto;
import com.coupong.order.repository.OrderRepository;
import com.coupong.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    private final CouponService couponService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository
            , ItemRepository itemRepository
            , CouponService couponService) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.couponService = couponService;
    }

    @Override
    @Transactional
    public List<OrderHistDto> getOrderInfoByGuest(String phoneNumber) {

        List<Order> orders = orderRepository.findByPhoneNumber(phoneNumber);
        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for(Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for(OrderItem orderItem : orderItems) {
                // orderItem 이미지 가져오기
                orderHistDto.addOrderItems(orderItem);
            }
            orderHistDtos.add(orderHistDto);
        }

        return orderHistDtos;
    }

    @Override
    @Transactional
    public List<OrderHistDto> getOrderInfoByMember(String orderRid) {

        List<Order> orders = orderRepository.findByOrderRid(orderRid);
        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for(Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for(OrderItem orderItem : orderItems) {
                // orderItem 이미지 가져오기
                orderHistDto.addOrderItems(orderItem);
            }

            orderHistDtos.add(orderHistDto);
        }

        return orderHistDtos;
    }

    @Override
    @Transactional
    public OrderResultDto order(Member member, OrderDto orderDto) {

        List<OrderItem> orderItems = new ArrayList<>();

        // 주문한 상품 설정
        for(OrderItemDto orderItemDto : orderDto.getOrderItems()) {
            Optional<Item> opItem = itemRepository.findByRid(orderItemDto.getItemRid());
            Item item = opItem.orElseThrow(NotFoundException::new);
            item.order(orderItemDto.getQuantity());

            OrderItem orderItem = OrderItem.createOrderItem(item, orderItemDto.getQuantity());
            orderItems.add(orderItem);
        }

        // 쿠폰 사용
        IssuedCoupon issuedCoupon = couponService.useCoupon(orderDto.getIssuedCouponId());

        // 주문 생성
        Order order = Order.createOrder(member, orderItems, issuedCoupon, orderDto.getAddress());

        return new OrderResultDto(orderRepository.save(order));
    }

}
