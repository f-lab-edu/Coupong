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
            // TODO : 상품 옵션 추가로 확인해야 함
            Optional<Item> opItem = itemRepository.findByRid(orderItemDto.getItemRid());
            Item item = opItem.orElseThrow(NotFoundException::new);
            item.order(orderItemDto.getQuantity());

            // 상품 쿠폰 사용
            // TODO : 쿠폰 사용 시 쿠폰 종류 넘기기(쿠폰서비스 수정 필요)
            IssuedCoupon itemCoupon = couponService.useCoupon(orderItemDto.getIssuedCouponId());

            OrderItem orderItem = OrderItem.createOrderItem(item, orderItemDto.getQuantity(), itemCoupon);
            orderItems.add(orderItem);
        }

        // 장바구니 쿠폰 사용
        IssuedCoupon basketCoupon = couponService.useCoupon(orderDto.getIssuedCouponId());

        // 주문 생성
        Order order = Order.createOrder(member, orderDto.getAddress(), basketCoupon, orderItems);

        return new OrderResultDto(orderRepository.save(order));
    }

}
