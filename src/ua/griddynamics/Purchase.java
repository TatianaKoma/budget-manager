package ua.griddynamics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Purchase {
    private static final String SMALL_MENU = "1) %s \n2) %s \n3) %s \n4) %s \n5) %s\n";
    private static final String BIG_MENU = "1) %s \n2) %s \n3) %s \n4) %s \n5) %s \n6) %s\n";
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
            System.out.println("\nChoose the type of purchase");
            System.out.printf(SMALL_MENU, "Food", "Clothes", "Entertainment", "Other", "Back");
            int choice = Validation.validChoiceForAdding(scanner.nextInt());
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
                return;
            }
        }
    }

    private static void showList(Scanner scanner) {
        double totalSum = 0.0;

        while (true) {
            System.out.println("\nChoose the type of purchases");
            System.out.printf(BIG_MENU, "Food", "Clothes", "Entertainment", "Other", "All", "Back");
            int choice = Validation.validChoiceForPrinting(scanner.nextInt());
            scanner.nextLine();
            if (choice == 5) {
                showAll();
            } else if (choice == 6 || noPurchasesAtAll()) {
                return;
            } else {
                String type = Objects.requireNonNull(chooseType(choice)).getType();
                for (Category c : ALL_PRODUCTS) {
                    if (c.getType().equals(type)) {
                        System.out.println("\n" + c.getType() + ":");
                        if (c.getPRODUCTS().isEmpty()) {
                            System.out.println("The purchase list is empty!\n");
                        } else {
                            for (Product p : c.getPRODUCTS()) {
                                totalSum += p.getPRICE();
                                System.out.println(p + "\nTotal sum: $" + String.format("%.2f", totalSum));
                            }
                        }
                    }
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
            System.out.println("\nThe purchase list is empty!\n");
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
        System.out.println("Total sum: $" + String.format("%.2f", totalSum) + "\n");
    }

    private static void savePurchases(User user) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(".\\..\\..\\purchases.txt");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(user);
            objectOutputStream.writeObject(FOOD);
            objectOutputStream.writeObject(CLOTHES);
            objectOutputStream.writeObject(ENTERTAINMENT);
            objectOutputStream.writeObject(OTHER);
            System.out.println("\nPurchases were saved!\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadPurchases() {
        boolean hasNext = true;
        if (!new File(".\\..\\..\\purchases.txt").exists()) {
            System.out.println("\nThe purchase list is empty! You need to add purchases before loading them\n");
        } else {
            try (FileInputStream fileInputStream = new FileInputStream(".\\..\\..\\purchases.txt");
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                while (hasNext) {
                    if (fileInputStream.available() != 0) {
                        Object object = objectInputStream.readObject();
                        if(object instanceof Category)
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
