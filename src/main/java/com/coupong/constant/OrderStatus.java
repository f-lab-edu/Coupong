package com.coupong.constant;

public enum OrderStatus {

    /*
    주문상태: 주문접수, 결제완료, 상품준비중, 배송준비중, 배송시작, 배송중, 배송완료
    CS상태: 취소, 교환, 반품, 환불
    결제상태: 입금전, 추가입금대기, 입금완료, 결제완료 (결제 기능 생략)
     */
    ORDER_RECEIVED
    , PAYMENT_COMPLETED
    , PRODUCT_READY
    , DELIVERY_READY
    , DELIVERY_STARTED
    , DELIVERY_COMPLETED
}
