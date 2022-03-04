package com.poly.SOF3021.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.SOF3021.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
