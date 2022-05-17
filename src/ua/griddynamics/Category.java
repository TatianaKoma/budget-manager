package ua.griddynamics;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    private final String type;
    private double totalSum;
    private final ArrayList<Product> PRODUCTS = new ArrayList<>();

    public Category(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public ArrayList<Product> getPRODUCTS() {
        return new ArrayList<>(PRODUCTS);
    }

    public double getTotalSum() {
        return totalSum;
    }

    public boolean addProduct(Product product) {
        if (product != null) {
            PRODUCTS.add(product);
            this.totalSum += product.getPRICE();
            return true;
        }
        return false;
    }
}
