package ua.griddynamics;

import menu.ShowListMenu;

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

    public String showList(int choice) {
        StringBuilder sb = new StringBuilder();
        if (ShowListMenu.getInstance(choice).equals(ShowListMenu.ALL)) {
            return showAll();
        } else if (isEmptyProductListByType(ProductType.getInstance(choice))) {
            sb.append("\n").append("The purchase list is empty!");
        } else {
            ProductType type = ProductType.getInstance(choice);
            sb.append("\n").append(type).append(":").append("\n");
            for (Product p : allProducts) {
                if (p.getType().equals(type)) {
                    sb.append(p).append("\n");
                }
            }
            sb.append("Total sum: $").append(String.format("%.2f", getTotalSumByType(type)));
        }
        return sb.toString();
    }

    public boolean isEmptyAllProductList() {
        return allProducts.size() == 0;
    }

    public boolean isEmptyProductListByType(ProductType type) {
        int count = 0;
        for (Product p : allProducts) {
            if (p.getType().equals(type)) {
                count++;
            }
        }
        return count == 0;
    }

    private String showAll() {
        StringBuilder sb = new StringBuilder();
        if (allProducts.isEmpty()) {
            sb.append("\nThe purchase list is empty!");
        } else {
            sb.append("\nAll:\n");
            String str = allProducts.stream()
                    .map(Product::toString)
                    .collect(Collectors.joining("\n"));
            sb.append(str);
            sb.append("\nTotal sum: $").append(String.format("%.2f", getTotalSumOfAllProducts()));
        }
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
        boolean hasNext = true;
        if (new File(FILE_NAME).exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                while (hasNext) {
                    if (fileInputStream.available() != 0) {
                        Object obj = objectInputStream.readObject();

                        if (obj instanceof Purchase purchase) {
                            balance = purchase.balance;
                            allProducts = purchase.allProducts;
                        }
                    } else {
                        hasNext = false;
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
