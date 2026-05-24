package git.xlamid.entity;

import git.xlamid.exception.OrderNotFoundException;

public class Order {

    private final Integer id;
    private final String productName;
    private final Integer quantity;
    private final Double unitPrice;

    public Order(Integer id, String productName, Integer quantity, Double unitPrice) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Integer getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }
}
