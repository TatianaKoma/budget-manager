package ua.griddinamycs;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Purchase purchase = new Purchase();

        boolean isGameOver = false;
        while (!isGameOver) {
            System.out.println("Choose your action:\n" +
                    "1) Add income\n" +
                    "2) Add purchase\n" +
                    "3) Show list of purchases\n" +
                    "4) Balance\n" +
                    "5) Save\n" +
                    "6) Load\n" +
                    "7) Analyze (Sort)\n" +
                    "0) Exit");

            int userChoice = scanner.nextInt();
            switch (userChoice) {
                case 1:
                    purchase.addIncome();
                    break;
                case 2:
                    purchase.addPurchases();
                    break;
                case 3:
                    if (purchase.checkIsEmpty()) {
                        System.out.println("\nThe purchase list is empty!\n");
                        break;
                    }
                    purchase.showListOfPurchases();
                    break;
                case 4:
                    System.out.printf("\nBalance: $%.2f%n\n", purchase.getBalance());
                    break;
                case 5:
                    purchase.saveBudget();
                    break;
                case 6:
                    purchase.loadBudget();
                    break;
                case 7:
                    purchase.showSortMenu();
                    break;
                case 0:
                    System.out.println("\nBye!");
                    isGameOver = true;
                    scanner.close();
                    purchase.reset();
                    break;
                default:
                    break;
            }
        }
    }
}
