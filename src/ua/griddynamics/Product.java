package ua.griddynamics;

import java.io.Serializable;

public class Product implements Serializable {

    private final String name;
    private final double price;
    private final ProductType type;

    public Product(String name, double price, ProductType type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public double getPrice() {
        return this.price;
    }

    public ProductType getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.name + " $" + String.format("%.2f", this.price);
    }
}
