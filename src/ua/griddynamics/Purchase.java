package ua.griddynamics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Purchase {

    private static final Category FOOD = new Category("Food");
    private static final Category CLOTHES = new Category("Clothes");
    private static final Category ENTERTAINMENT = new Category("Entertainment");
    private static final Category OTHER = new Category("Other");
    private static final ArrayList<Category> ALL_PRODUCTS = new ArrayList<>();

    public static Category chooseType(int choice) {
        switch (choice) {
            case 1:
                return FOOD;
            case 2:
                return CLOTHES;
            case 3:
                return ENTERTAINMENT;
            case 4:
                return OTHER;
            default:
        }
        return null;
    }

    public static void showList(int choice) {
        double totalSum = 0.0;
        if (choice == 5) {
            showAll();
        } else {
            String type = Objects.requireNonNull(chooseType(choice)).getType();
            for (Category c : ALL_PRODUCTS) {
                if (c.getType().equals(type)) {
                    System.out.println("\n" + c.getType() + ":");
                    if (c.getPRODUCTS().isEmpty()) {
                        System.out.println("The purchase list is empty!");
                    } else {
                        for (Product p : c.getPRODUCTS()) {
                            totalSum += p.getPRICE();
                            System.out.println(p);
                        }
                        System.out.println("Total sum: $" + String.format("%.2f", totalSum));
                    }
                }
            }
        }
    }

   public static void showAllSortedPurchases() {
        if (!noPurchasesAtAll()) {
            List<Product> productsList = new ArrayList<>();
            double totalSum = 0.0;
            for (Category c : ALL_PRODUCTS) {
                totalSum += c.getTotalSum();
                productsList.addAll(c.getPRODUCTS());
            }
            System.out.println("\nAll: ");
            productsList.stream()
                    .sorted(Comparator.comparing(Product::getPRICE).reversed())
                    .forEach(System.out::println);
            System.out.println("Total sum: $" + String.format("%.2f", totalSum));
        }
    }

    public static void showSortByCategories() {
        System.out.println("\nTypes: ");
        double totalSumOfAll = 0.00;
        Map<String, Double> mapForSorting = new HashMap<>();
        for (Category c : ALL_PRODUCTS) {
            totalSumOfAll += c.getTotalSum();
            if (c.getPRODUCTS().isEmpty()) {
                mapForSorting.put(c.getType(), 0.00);
            } else {
                double totalSum = 0;
                for (Product p : c.getPRODUCTS()) {
                    totalSum += p.getPRICE();
                }
                mapForSorting.put(c.getType(), totalSum);
            }
        }
        mapForSorting.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> System.out.println(x.getKey() + " $" + x.getValue()));
        System.out.println("Total sum: $" + String.format("%.2f", totalSumOfAll));
    }

    public static void showCategory(int choice) {
        Category c = chooseType(choice);
        showSortInCategory(c.getType());
    }

    private static void showSortInCategory(String type) {
        double totalSum = 0.00;
        if (!noPurchasesAtAll()) {
            for (Category c : ALL_PRODUCTS) {
                if (c.getType().equals(type)) {
                    System.out.println("\n" + c.getType() + ":");
                    for (Product p : c.getPRODUCTS()) {
                        totalSum += p.getPRICE();
                    }
                    c.getPRODUCTS().stream()
                            .sorted(Comparator.comparing(Product::getPRICE).reversed())
                            .forEach(System.out::println);
                    System.out.println("Total sum: $" + String.format("%.2f", totalSum));
                }
            }
        }
    }

    public static boolean noPurchasesAtAll() {
        int counter = 0;
        for (Category c : ALL_PRODUCTS) {
            if (c.getPRODUCTS().isEmpty()) {
                counter++;
            }
        }
        if (counter == ALL_PRODUCTS.size()) {
            System.out.println("\nThe purchase list is empty!");
            return true;
        }
        return false;
    }

    private static void showAll() {
        double totalSum = 0.0;
        System.out.println("\nAll:");
        for (Category c : ALL_PRODUCTS) {
            totalSum += c.getTotalSum();
            c.getPRODUCTS().forEach(product -> System.out.println(product.toString()));
        }
        System.out.println("Total sum: $" + String.format("%.2f", totalSum));
    }

    public static void savePurchases(User user) {
        try (FileOutputStream fileOutputStream = new FileOutputStream("purchases.txt");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(user);
            objectOutputStream.writeObject(FOOD);
            objectOutputStream.writeObject(CLOTHES);
            objectOutputStream.writeObject(ENTERTAINMENT);
            objectOutputStream.writeObject(OTHER);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadPurchases() {
        boolean hasNext = true;
        if (new File("purchases.txt").exists()) {
            try (FileInputStream fileInputStream = new FileInputStream("purchases.txt");
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                while (hasNext) {
                    if (fileInputStream.available() != 0) {
                        Object object = objectInputStream.readObject();
                        if (object instanceof Category)
                            ALL_PRODUCTS.add((Category) object);
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