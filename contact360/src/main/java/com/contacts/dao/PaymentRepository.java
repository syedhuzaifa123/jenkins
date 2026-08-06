package com.contacts.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contacts.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
