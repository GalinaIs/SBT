package com.sbt.service;

import com.sbt.model.InventoryResponse;
import com.sbt.model.Order;

import java.util.Map;

public interface OrderService {
    void sendOrder(Order order);

    void updateOrder(InventoryResponse response);

    Map<String, Order> getAllOrders();
}
