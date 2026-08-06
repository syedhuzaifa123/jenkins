package com.contacts.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.contacts.controller.SearchController;
import com.contacts.dto.PaymentRequest;
import com.contacts.dto.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.Mode;

@Service
public class StripeService {

	private final SearchController searchController;

	@Value("${stripe.secretKey}")
	private String secretKey;

	StripeService(SearchController searchController) {
		this.searchController = searchController;
	}

	public PaymentResponse checkout(PaymentRequest request) {

		Stripe.apiKey = secretKey;

		SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData
				.builder().setName(request.getName()).build();

		SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
				.setCurrency(request.getCurrency() == null ? "USD" : request.getCurrency())
				.setUnitAmount((request.getAmount()) * 100).setProductData(productData).build();

		SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
				.setQuantity(request.getQuantity()).setPriceData(priceData).build();

		SessionCreateParams params = SessionCreateParams.builder().setMode(Mode.PAYMENT)
				.setSuccessUrl("http://localhost:8080/user/success?session_id={CHECKOUT_SESSION_ID}")
				.setCancelUrl("http://localhost:8080/user/cancel").addLineItem(lineItem).build();

		System.out.println("Params: " + params);

		Session session = null;

		try {
			session = Session.create(params);
			System.out.println("Session: " + session);
			return new PaymentResponse("SUCCESS", "Checkout session created", session.getId(), session.getUrl());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new PaymentResponse("FAILED", e.getMessage(), null, null);

		}

	}

	public Session retrieveSession(String sessionId) throws StripeException {
		Session session = Session.retrieve(sessionId);
		return session;
	}

	public PaymentIntent retrievePaymentIntent(String paymentIntentId) throws StripeException {
		return PaymentIntent.retrieve(paymentIntentId);
	}

}
