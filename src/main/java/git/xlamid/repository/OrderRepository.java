package git.xlamid.repository;

import git.xlamid.entity.Order;
import git.xlamid.exception.OrderNotFoundException;

import java.util.*;

public class OrderRepository {

    private final Map<Integer, Order> orders = new HashMap<>();

    public Integer saveOrder(Order order) {
        Integer id = order.getId();
        orders.put(id, order);
        return id;
    }

    public Optional<Order> getOrderById(Integer id) {
        return Optional.ofNullable(orders.get(id));
    }
}