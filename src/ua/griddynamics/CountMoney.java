package ua.griddynamics;

import java.util.Scanner;

public class CountMoney {
    private static boolean quit = false;
    private static final User USER = new User();

    private static void menu() {
        System.out.println("Choose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Balance\n" +
                "5) Save\n" +
                "6) Load\n" +
                "0) Exit");
    }

    static void routine(Scanner scanner) {
        while (!quit) {
            menu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    USER.addIncomeFromMainMenu(scanner, USER);
                    break;
                case 2:
                    Purchase.addPurchaseFromMainMenu(scanner, USER);
                    break;
                case 3:
                    Purchase.showListFromMainMenu(scanner);
                    break;
                case 4:
                    USER.showBalanceFromMainMenu(USER);
                    break;
                case 5:
                    Purchase.savePurchasesFromMainMenu(USER);
                    break;
                case 6:
                    Purchase.loadPurchasesFromMainMenu();
                    break;
                case 0:
                    quit = true;
                    System.out.println("\nBye!");
                    scanner.close();
                    break;
                default:
                    System.out.println("\nSomething went wrong.\n");
            }
        }
    }
}
