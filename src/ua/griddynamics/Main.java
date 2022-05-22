package ua.griddynamics;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final int EXIT_FROM_ADD_MENU = 5;
    private static final int EXIT_FROM_SHOW_MENU = 6;

    public static void main(String[] args) {
        Purchase purchase = new Purchase();
        boolean quit = false;
        while (!quit) {
            Menu.menuMain();
            int choice = SCANNER.nextInt();
            SCANNER.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("\nEnter income:");
                    double income = SCANNER.nextDouble();
                    SCANNER.nextLine();
                    purchase.addIncome(income);
                    System.out.println("Income was added!\n");
                    break;
                case 2:
                    addPurchase(purchase);
                    break;
                case 3:
                    while (true) {
                        Menu.menuShowList();
                        int choiceForShowList = SCANNER.nextInt();
                        SCANNER.nextLine();
                        if (choiceForShowList == EXIT_FROM_SHOW_MENU) {
                            System.out.println();
                            break;
                        } else {
                            System.out.println(purchase.showList(choiceForShowList));
                        }
                    }
                    break;
                case 4:
                    System.out.println(purchase.showBalance());
                    break;
                case 5:
                    purchase.savePurchases();
                    System.out.println("\nPurchases were saved!\n");
                    break;
                case 6:
                    purchase.loadPurchase();
                    System.out.println("\nPurchases were loaded!\n");
                    break;
                case 7:
                    showSort(purchase);
                    break;
                case 0:
                    quit = true;
                    System.out.println("\nBye!");
                    SCANNER.close();
                    break;
                default:
                    System.out.println("\nSomething went wrong.\n");
            }
        }
    }

    private static void addPurchase(Purchase purchase) {
        String name = null;
        double price = 0.0;
        while (true) {
            Menu.menuAddPurchase();
            int choice = SCANNER.nextInt();
            SCANNER.nextLine();
            if (choice != EXIT_FROM_ADD_MENU) {
                try {
                    System.out.println("\nEnter purchase name:");
                    name = SCANNER.nextLine();
                    System.out.println("Enter its price:");
                    price = SCANNER.nextDouble();
                    SCANNER.nextLine();
                } catch (InputMismatchException e) {
                    e.printStackTrace();
                }
                if (purchase.addProduct(new Product(name, price, purchase.getType(choice)))) {
                    purchase.reduceBalance(price);
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

    public static void showSort(Purchase purchase) {
        boolean isSortingStopped = false;
        while (!isSortingStopped) {
            Menu.menuShowSort();
            int choice = SCANNER.nextInt();
            switch (choice) {
                case 1 -> System.out.println(purchase.showAllSortedPurchases());
                case 2 -> System.out.println(purchase.showSortByCategories());
                case 3 -> {
                    Menu.menuCategory();
                    int choiceForCategory = SCANNER.nextInt();
                    System.out.println(purchase.showCategory(choiceForCategory));
                }
                case 4 -> {
                    isSortingStopped = true;
                    System.out.println();
                }
                default -> {
                }
            }
        }
    }
}
