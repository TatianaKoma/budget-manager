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
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Purchase {
    private static final Category FOOD = new Category("Food");
    private static final Category CLOTHES = new Category("Clothes");
    private static final Category ENTERTAINMENT = new Category("Entertainment");
    private static final Category OTHER = new Category("Other");
    private static final ArrayList<Category> ALL_PRODUCTS = new ArrayList<>();

    public static void addPurchaseFromMainMenu(Scanner scanner, User user) {
        addPurchase(scanner, user);
    }

    public static void showListFromMainMenu(Scanner scanner) {
        showList(scanner);
    }

    public static void savePurchasesFromMainMenu(User user) {
        savePurchases(user);
    }

    public static void loadPurchasesFromMainMenu() {
        loadPurchases();
    }

    public static void sortPurchasesFromMainMenu(Scanner scanner) {
        showSort(scanner);
    }

    private static Category chooseType(int choice) {
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

    private static void addPurchase(Scanner scanner, User user) {
        String name = null;
        double price = 0.0;

        while (true) {
            System.out.println(" \n Choose the type of purchase\n" +
                    " 1) Food\n" +
                    " 2) Clothes\n" +
                    " 3) Entertainment\n" +
                    " 4) Other\n" +
                    " 5) Back");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice != 5) {
                Category category = Objects.requireNonNull(chooseType(choice));
                try {
                    System.out.println("\nEnter purchase name:");
                    name = scanner.nextLine();
                    System.out.println("Enter its price:");
                    price = scanner.nextDouble();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    e.printStackTrace();
                }
                if (category.addProduct(new Product(name, price))) {
                    user.updateBalance(price, user);
                    System.out.println("Purchase was added!");
                } else {
                    System.out.println("Purchase was not added!");
                }
            } else {
                System.out.println();
                return;
            }
        }
    }

    private static void showList(Scanner scanner) {
        double totalSum = 0.0;
        while (true) {
            System.out.println("\nChoose the type of purchase\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) All\n" +
                    "6) Back");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 5) {
                showAll();
            } else if (choice == 6 || noPurchasesAtAll()) {
                System.out.println();
                return;
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
    }

    private static void showSort(Scanner scanner) {
        boolean isSortingStopped = false;
        while (!isSortingStopped) {
            System.out.println("\n" +
                    "How do you want to sort?\n" +
                    "1) Sort all purchases\n" +
                    "2) Sort by type\n" +
                    "3) Sort certain type\n" +
                    "4) Back");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    showAllSortedPurchases();
                    break;
                case 2:
                    showSortByCategories();
                    break;
                case 3:
                    showCategoryMenu(scanner);
                    break;
                case 4:
                    isSortingStopped = true;
                    System.out.println();
                    break;
                default:
                    break;
            }
        }
    }

    private static void showAllSortedPurchases() {
        if (!noPurchasesAtAll()) {
            List<Product> productsList = new ArrayList<>();
            double totalSum = 0.0;
            for (Category c : ALL_PRODUCTS) {
                totalSum += c.getTotalSum();
                productsList.addAll(c.getPRODUCTS());
            }
            System.out.println("\nAll: ");
            productsList.stream()
                    .sorted(Comparator.comparing(Product::getPRICE).reversed().thenComparing(Product::getNAME))
                    .forEach(System.out::println);
            System.out.println("Total sum: $" + String.format("%.2f", totalSum));
        }
    }

    private static void showSortByCategories() {
        System.out.println("\nTypes: ");
        double totalSumOfAll = 0.00;
        Map<String,Double> mapForSorting = new HashMap<>();
        for (Category c : ALL_PRODUCTS) {
            totalSumOfAll += c.getTotalSum();
            if (c.getPRODUCTS().isEmpty()) {
                mapForSorting.put(c.getType(),0.00);
            } else {
                double totalSum = 0;
                for (Product p : c.getPRODUCTS()) {
                    totalSum += p.getPRICE();
                }
                mapForSorting.put(c.getType(),totalSum);
            }
        }
        mapForSorting.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> System.out.println(x.getKey() + " $" + x.getValue()));
        System.out.println("Total sum: $" + String.format("%.2f", totalSumOfAll));
    }

    private static void showCategoryMenu(Scanner scanner) {
        System.out.println("\nChoose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other");
        int choice = scanner.nextInt();
        Category c = chooseType(choice);
        assert c != null;
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

    private static boolean noPurchasesAtAll() {
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

    private static void savePurchases(User user) {
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

    private static void loadPurchases() {
        boolean hasNext = true;
        if (!new File("purchases.txt").exists()) {
            System.out.println("\nThe purchase list is empty! You need to add purchases before loading them\n");
        } else {
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
            System.out.println("\nPurchases were loaded!\n");
        }
    }
}
