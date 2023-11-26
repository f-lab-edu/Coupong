package com.coupong.order.service.impl;

import com.coupong.coupon.repository.CouponRepository;
import com.coupong.coupon.service.CouponService;
import com.coupong.entity.IssuedCoupon;
import com.coupong.entity.Item;
import com.coupong.entity.Order;
import com.coupong.entity.OrderItem;
import com.coupong.item.repository.ItemRepository;
import com.coupong.order.dto.OrderDto;
import com.coupong.order.dto.OrderHistDto;
import com.coupong.order.dto.OrderItemDto;
import com.coupong.order.repository.OrderRepository;
import com.coupong.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public String order(OrderDto orderDto) {

        List<OrderItem> orderItems = new ArrayList<>();

        // 주문한 상품 설정
        for(OrderItemDto orderItemDto : orderDto.getOrderItems()) {
            Item item = itemRepository.findByRid(orderItemDto.getItemRid());
            item.order(orderItemDto.getQuantity());

            OrderItem orderItem = OrderItem.createOrderItem(item, orderItemDto.getQuantity());
            orderItems.add(orderItem);
        }

        // 쿠폰 사용하기
        IssuedCoupon issuedCoupon = couponService.useCoupon(orderDto.getIssuedCouponId());

        Order order = Order.createOrder(1, orderItems, issuedCoupon, orderDto.getAddress());
        orderRepository.save(order);

        return null;
    }

}
