package com.sbt.repo;

import com.sbt.model.Order;

import java.util.Map;

public interface OrderRepository {

    void putOrder(Order order);

    Order getOrder(String orderId);

    Map<String, Order> getAllOrders();
}
