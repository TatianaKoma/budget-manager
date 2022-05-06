package ua.griddinamycs;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Purchase purchase = new Purchase();

        boolean isGameOver = false;
        while (!isGameOver) {
            System.out.println("""
                    Choose your action:
                    1) Add income
                    2) Add purchase
                    3) Show list of purchases
                    4) Balance
                    5) Save
                    6) Load
                    0) Exit""");

            int userChoice = scanner.nextInt();
            switch (userChoice) {
                case 1:
                    System.out.println();
                    System.out.println("Enter income: ");
                    double inputIncome = scanner.nextDouble();
                    purchase.addIncome(inputIncome);
                    System.out.println();
                    break;
                case 2:
                    System.out.println();
                    purchase.addPurchases();
                    System.out.println();
                    break;
                case 3:
                    System.out.println();
                    if (purchase.checkIsEmpty()) {
                        System.out.println("The purchase list is empty!");
                        System.out.println();
                        break;
                    }
                    purchase.showListOfPurchases();
                    System.out.println();
                    break;
                case 4:
                    System.out.println();
                    System.out.printf("Balance: $%.2f%n", purchase.getBalance());
                    System.out.println();
                    break;
                case 5:
                    System.out.println();
                    purchase.saveBudget();
                    System.out.println();
                    break;
                case 6:
                    purchase.loadBudget();
                    System.out.println();
                    break;
                case 0:
                    System.out.println();
                    System.out.println("Bye!");
                    isGameOver = true;
                    break;
                default:
                    break;
            }
        }
    }
}
