package ua.griddynamics;

import java.io.Serializable;

public class Product implements Serializable {
    private final String NAME;
    private final double PRICE;

    public Product(String name, double price) {
        this.NAME = name;
        this.PRICE = price;
    }

    public double getPRICE() {
        return this.PRICE;
    }

    public String getNAME() {
        return NAME;
    }

    @Override
    public String toString() {
        return this.NAME + " $" + String.format("%.2f", this.PRICE);
    }
}
