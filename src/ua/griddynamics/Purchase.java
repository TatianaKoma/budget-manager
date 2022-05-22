package ua.griddynamics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public ProductType getType(int choice) {
        return switch (choice) {
            case 1 -> ProductType.FOOD;
            case 2 -> ProductType.CLOTHES;
            case 3 -> ProductType.ENTERTAINMENT;
            case 4 -> ProductType.OTHER;
            default -> throw new RuntimeException("Wrong input");
        };
    }

    public String showList(int choice) {
        int choiceForAll = 5;
        StringBuilder sb = new StringBuilder();
        if (choice == choiceForAll) {
            return showAll();
        } else if (isEmptyProductListByType(getType(choice))) {
            sb.append("\n").append("The purchase list is empty!");
        } else {
            ProductType type = getType(choice);
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

    public String showAllSortedPurchases() {
        StringBuilder sb = new StringBuilder();
        if (!isEmptyAllProductList()) {
            sb.append("\nAll: \n");
            String str = allProducts.stream()
                    .sorted(Comparator.comparing(Product::getPrice).reversed())
                    .map(Product::toString)
                    .collect(Collectors.joining("\n"));
            sb.append(str);
            sb.append("\nTotal sum: $").append(String.format("%.2f", getTotalSumOfAllProducts()));
            return sb.toString();
        } else {
            return "\nThe purchase list is empty!";
        }
    }

    public String showSortByCategories() {
        StringBuilder sb = new StringBuilder("\nTypes: \n");
        Map<ProductType, Double> mapForSorting = new HashMap<>();
        for (ProductType type : ProductType.values()) {
            mapForSorting.put(type, getTotalSumByType(type));
        }
        String str = mapForSorting.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(entry -> entry.getKey() + " $" + String.format("%.2f", entry.getValue()))
                .collect(Collectors.joining("\n"));
        sb.append(str).append("\nTotal sum: $").append(String.format("%.2f", getTotalSumOfAllProducts()));
        return sb.toString();
    }

    public String showCategory(int choice) {
        return showSortInCategory(getType(choice));
    }

    private String showSortInCategory(ProductType type) {
        StringBuilder sb = new StringBuilder();
        if (!isEmptyProductListByType(type)) {
            sb.append("\n").append(type).append(":\n");

            String str = allProducts.stream()
                    .filter(p -> p.getType().equals(type))
                    .sorted(Comparator.comparing(Product::getPrice).reversed())
                    .map(Product::toString)
                    .collect(Collectors.joining("\n"));
            sb.append(str);
            sb.append("\nTotal sum: $").append(String.format("%.2f", getTotalSumByType(type)));
            return sb.toString();
        } else {
            return "\nThe purchase list is empty!";
        }
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
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME))){
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
}
