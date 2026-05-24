package git.xlamid.repository;

import git.xlamid.entity.Order;
import git.xlamid.exception.OrderNotFoundException;

public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String processOrder(Order order) {
        try {
            checkUnitPriceAndQuantity(order);
            Integer id = orderRepository.saveOrder(order);
            return "Order processes successfully";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    private void checkUnitPriceAndQuantity(Order order) {
        Double unitPrice = order.getUnitPrice();
        Integer quantity = order.getQuantity();
        if (unitPrice == null || quantity == null) {
            throw new IllegalArgumentException("Unit price and quantity cannot be null");
        }
        if (unitPrice < 0.0 || quantity < 0) {
            throw new IllegalArgumentException("Unit price and quantity cannot be negative");
        }
    }

    public Double calculateTotal(Integer id) {
        Order order = orderRepository.getOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
        return order.getUnitPrice() * order.getQuantity();
    }
}