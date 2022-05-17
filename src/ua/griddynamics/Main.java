package ua.griddynamics;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {

        boolean quit = false;
        final User USER = new User();
        while (!quit) {
            Menu.menuMain();
            int choice = SCANNER.nextInt();
            SCANNER.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("\nEnter income:");
                    double income = SCANNER.nextDouble();
                    SCANNER.nextLine();
                    USER.addIncome(income, USER);
                    break;
                case 2:
                    addPurchase(USER);
                    Purchase.savePurchases(USER);
                    break;
                case 3:
                    do {
                        Menu.menuShowList();
                        int choiceForShowList = SCANNER.nextInt();
                        SCANNER.nextLine();
                        if (choiceForShowList == 6 || Purchase.noPurchasesAtAll()) {
                            System.out.println();
                            break;
                        } else {
                            Purchase.showList(choiceForShowList);
                        }
                    } while (true);
                    break;
                case 4:
                    USER.showBalance(USER);
                    break;
                case 5:
                    System.out.println("\nPurchases were saved!\n");
                    break;
                case 6:
                    Purchase.loadPurchases();
                    System.out.println("\nPurchases were loaded!\n");
                    break;
                case 7:
                    showSort();
                    break;
                case 0:
                    quit = true;
                    System.out.println("\nBye!");
                    try {
                        File file = new File("purchases.txt");
                        file.deleteOnExit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SCANNER.close();
                    break;
                default:
                    System.out.println("\nSomething went wrong.\n");
            }
        }

    }

    private static void addPurchase(User user) {
        String name = null;
        double price = 0.0;
        while (true) {
            Menu.menuAddPurchase();
            int choice = SCANNER.nextInt();
            SCANNER.nextLine();
            if (choice != 5) {
                Category category = Objects.requireNonNull(Purchase.chooseType(choice));
                try {
                    System.out.println("\nEnter purchase name:");
                    name = SCANNER.nextLine();
                    System.out.println("Enter its price:");
                    price = SCANNER.nextDouble();
                    SCANNER.nextLine();
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

    public static void showSort() {
        boolean isSortingStopped = false;
        while (!isSortingStopped) {
            Menu.menuShowSort();
            int choice = SCANNER.nextInt();
            switch (choice) {
                case 1 -> Purchase.showAllSortedPurchases();
                case 2 -> Purchase.showSortByCategories();
                case 3 -> {
                    Menu.menuCategory();
                    int choiceForCategory = SCANNER.nextInt();
                    Purchase.showCategory(choiceForCategory);
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
