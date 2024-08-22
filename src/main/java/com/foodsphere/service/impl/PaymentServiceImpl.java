package com.foodsphere.service.impl;

import com.foodsphere.model.Order;
import com.foodsphere.response.PaymentResponse;
import com.foodsphere.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Override
    public PaymentResponse createPaymentLink(Order order) throws StripeException {

        Stripe.apiKey=stripeSecretKey;
        SessionCreateParams params=SessionCreateParams.builder().addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD).setMode(SessionCreateParams.Mode.PAYMENT).setSuccessUrl("http://localhost:3300/payment/success/"+order.getId()).setCancelUrl("http://localhost:3300/payment/fail").addLineItem(SessionCreateParams.LineItem.builder().setQuantity(1L).setPriceData(SessionCreateParams.LineItem.PriceData.builder().setCurrency("usd").setUnitAmount((long) order.getTotalPrice()*100).setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder().setName("FoodSphere").build()).build()).build()).build();

        Session session=Session.create(params);

        PaymentResponse res=new PaymentResponse();
        res.setPayment_url(session.getUrl());

        return res;
    }
}
