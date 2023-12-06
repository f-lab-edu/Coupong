package com.coupong.order.service.impl;

import com.coupong.config.exception.BadRequestException;
import com.coupong.config.exception.NotFoundException;
import com.coupong.config.response.BaseResponse;
import com.coupong.constant.CouponKind;
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

import static com.coupong.constant.BaseStatus.*;

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
        if(orderDto.getOrderItems().isEmpty()) {
            // 주문할 상품이 없을 때
            throw new BadRequestException(new BaseResponse(NO_ITEM_TO_ORDER));
        }
        for(OrderItemDto orderItemDto : orderDto.getOrderItems()) {
            // TODO : 상품 옵션 추가로 확인해야 함
            Item item = itemRepository.findByRid(orderItemDto.getItemRid());
            // 상품을 주문할 수 있는지 확인
            canOrder(item, orderItemDto.getQuantity());
            item.order(orderItemDto.getQuantity());

            // 상품 쿠폰 사용
            // TODO : 쿠폰 사용 시 쿠폰 종류 넘기기(쿠폰서비스 수정 필요)
            IssuedCoupon itemCoupon = couponService.useCoupon(orderItemDto.getIssuedCouponId(), CouponKind.ITEM);
            //IssuedCoupon itemCoupon = couponService.useCoupon(orderItemDto.getIssuedCouponId(), CouponType.ITEM);

            int couponAppAmt = getCouponAppAmt(item, itemCoupon.getCoupon());
            OrderItem orderItem = OrderItem.createOrderItem(item, orderItemDto.getQuantity(), itemCoupon, couponAppAmt);
            orderItems.add(orderItem);
        }

        // 장바구니 쿠폰 사용
        IssuedCoupon basketCoupon = couponService.useCoupon(orderDto.getIssuedCouponId(), CouponKind.BASKET);
        //IssuedCoupon basketCoupon = couponService.useCoupon(orderDto.getIssuedCouponId(), CouponType.BASKET);

        // 주문 생성
        // TODO 배송비 어떻게 할지 정해야 함
        int totalItemFee = getTotalItemFee(orderItems, basketCoupon.getCoupon());
        Order order = Order.createOrder(member, orderDto.getAddress(), basketCoupon, orderItems, totalItemFee, 3000);

        return new OrderResultDto(orderRepository.save(order));
    }

    @Override
    public int getCouponAppAmt(Item item, Coupon coupon) {
        // 쿠폰적용금액
        int discountAmt = (item.getPrice() * coupon.getDiscntRate()) / 100;
        if(discountAmt > coupon.getMaxDiscntPrice()) {
            discountAmt = coupon.getMaxDiscntPrice();
        }
        return item.getPrice() - discountAmt;
    }

    @Override
    public int getTotalItemFee(List<OrderItem> orderItems, Coupon coupon) {
        // 총상품금액 = 주문상품의 쿠폰적용금액 합산(수량 계산까지) - 장바구니 쿠폰 할인 금액
        int totalItemFee = 0;
        for(OrderItem orderItem : orderItems) {
            totalItemFee += orderItem.getCouponAppAmt() * orderItem.getQuantity();
        }
        // 장바구니 쿠폰 할인 금액
        int discountFee = (totalItemFee * coupon.getDiscntRate()) / 100;
        if(discountFee > coupon.getMaxDiscntPrice()) {
            discountFee = coupon.getMaxDiscntPrice();
        }
        return totalItemFee - discountFee;
    }

    @Override
    public void canOrder(Item item, Integer quantity) {
        if(item.getStatus().compareTo(0) > 0) {
            // 주문 가능 상태인지 확인
            throw new BadRequestException(new BaseResponse(UNORDERABLE_ITEM));
        }
        if(item.getRemains().compareTo(quantity) < 0) {
            // 주문하려는 수량보다 적은지 확인
            throw new BadRequestException(new BaseResponse(OUT_OF_STOCK));
        }
        // 상품 옵션 확인 필요

    }

}
