package com.contacts.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String paymentIntentId;
	private String sessionId;
	private String email;
	private Long amount;
	private String currency;
	private String status;
	private String customerReference;
	private String orderReference;
	private Date createdAt;

	@ManyToOne
	private User user;

	public Payment() {
	}

	public Payment(Long id, String paymentIntentId, String sessionId, String email, Long amount, String currency,
			String status, String customerReference, String orderReference, Date createdAt, User user) {
		super();
		this.id = id;
		this.paymentIntentId = paymentIntentId;
		this.sessionId = sessionId;
		this.email = email;
		this.amount = amount;
		this.currency = currency;
		this.status = status;
		this.customerReference = customerReference;
		this.orderReference = orderReference;
		this.createdAt = createdAt;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaymentIntentId() {
		return paymentIntentId;
	}

	public void setPaymentIntentId(String paymentIntentId) {
		this.paymentIntentId = paymentIntentId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
	}

	public String getOrderReference() {
		return orderReference;
	}

	public void setOrderReference(String orderReference) {
		this.orderReference = orderReference;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", paymentIntentId=" + paymentIntentId + ", sessionId=" + sessionId + ", email="
				+ email + ", amount=" + amount + ", currency=" + currency + ", status=" + status
				+ ", customerReference=" + customerReference + ", orderReference=" + orderReference + ", createdAt="
				+ createdAt + ", user=" + user + "]";
	}

}
