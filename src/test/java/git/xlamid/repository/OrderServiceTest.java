package git.xlamid.repository;

import git.xlamid.entity.Order;
import git.xlamid.exception.OrderNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Test
    public void shouldCorrectlyCreateOrder() {
        // Arrange
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.saveOrder(any())).thenReturn(1);

        Order order = new Order(1, "Gazprom", 2, 600.0);
        OrderService orderService = new OrderService(orderRepository);
        // Act
        String res = orderService.processOrder(order);
        // Assert
        assertEquals("Order processes successfully", res);
        // Verify
        verify(orderRepository, times(1)).saveOrder(any());
    }

    @Test
    public void shouldNotCorrectlyCreateOrder() {
        // Arrange
        OrderRepository orderRepository = mock(OrderRepository.class);

        Order order = new Order(1, "Gazprom", 2, null);
        OrderService orderService = new OrderService(orderRepository);
        // Act
        String res = orderService.processOrder(order);
        // Assert
        assertEquals("Unit price and quantity cannot be null", res);
    }

    @Test
    public void shouldCorrectlyCalculateTotal() {
        // Arrange
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.getOrderById(1))
                .thenReturn(Optional.of(new Order(1, "Lukoil", 3, 100.0)));

        OrderService orderService = new OrderService(orderRepository);
        // Act
        Double res = orderService.calculateTotal(1);
        // Assert
        assertEquals(300.0, res);
        // Verify
        verify(orderRepository, times(1)).getOrderById(1);
    }

    @Test
    public void shouldReturnOrderNotFound() {
        // Arrange
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.getOrderById(2)).thenReturn(Optional.empty());

        OrderService orderService = new OrderService(orderRepository);
        // Act
        Exception res = assertThrows(OrderNotFoundException.class, () -> orderService.calculateTotal(2));
        // Assert
        assertEquals("Order with id 2 not found", res.getMessage());
    }

    @Test
    public void shouldCorrectlyCalculateTotalForZeroOrders() {
        // Arrange
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.getOrderById(1))
                .thenReturn(Optional.of(new Order(1, "Prikoil", 0, 100.0)));

        OrderService orderService = new OrderService(orderRepository);
        // Act
        Double res = orderService.calculateTotal(1);
        // Assert
        assertEquals(0.0, res);
        // Verify
        verify(orderRepository, times(1)).getOrderById(1);
    }
}