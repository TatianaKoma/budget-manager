package ua.griddynamics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Purchase implements Serializable {

    private static final String FILE_NAME = "purchases.txt";

    private Balance balance;
    private List<Product> allProducts;

    public Purchase() {
        this.balance = new Balance();
        this.allProducts = new ArrayList<>();
    }

    public void addIncome(double income) {
        this.balance.addIncome(income);
    }

    public String showBalance() {
        return balance.showBalance();
    }

    public void reduceBalance(double delta) {
        balance.reduceBalance(delta);
    }

    public boolean addProduct(Product product) {
        if (product != null) {
            allProducts.add(product);
            return true;
        }
        return false;
    }

    public double getTotalSumByType(ProductType type) {
        return allProducts.stream()
                .filter(p -> p.getType() == type)
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public double getTotalSumOfAllProducts() {
        return allProducts.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public boolean isEmptyAllProductList() {
        return allProducts.isEmpty();
    }

    public boolean isEmptyProductListByType(ProductType type) {
        for (Product p : allProducts) {
            if (p.getType().equals(type)) {
                return false;
            }
        }
        return true;
    }

    public String getDescription(ProductType type) {
        List<Product> products = allProducts.stream()
                .filter(p -> p.getType().equals(type) || type.isUndefined()).toList();
        if (products.isEmpty()) {
            return "\nThe purchase list is empty!";
        }

        StringBuilder sb = new StringBuilder();
        if (type.isUndefined()) {
            sb.append("\nAll:\n");
        } else {
            sb.append("\n").append(type).append(":\n");
        }
        String str = products.stream()
                .map(Product::toString)
                .collect(Collectors.joining("\n"));
        sb.append(str);
        sb.append("\nTotal sum: $").append(String.format("%.2f", getTotalSumOfAllProducts()));
        return sb.toString();
    }

    public void savePurchases() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPurchase() {
        if (new File(FILE_NAME).exists()) {
            try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                while (ios.available() > 0) {
                    Object obj = ios.readObject();
                    if (obj instanceof Purchase purchase) {
                        balance = purchase.balance;
                        allProducts = purchase.allProducts;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Product> getAllProducts() {
        return allProducts;
    }
}
