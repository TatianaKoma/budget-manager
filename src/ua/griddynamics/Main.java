package ua.griddynamics;

import analyzer.AllProductsAnalyzer;
import analyzer.Analyzer;
import analyzer.CertainTypeAnalyzer;
import analyzer.ProductsByTypeAnalyzer;
import menu.AddPurchaseMenu;
import menu.RootMenu;
import menu.ShowListMenu;
import menu.SortMenu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        Purchase purchase = new Purchase();
        boolean quit = false;
        while (!quit) {
            System.out.println(RootMenu.getMenuStr());
            int choice = SCANNER.nextInt();
            SCANNER.nextLine();
            switch (RootMenu.getInstance(choice)) {
                case ADD_INCOME:
                    System.out.println("\nEnter income:");
                    double income = SCANNER.nextDouble();
                    SCANNER.nextLine();
                    purchase.addIncome(income);
                    System.out.println("Income was added!\n");
                    break;
                case ADD_PURCHASE:
                    addPurchase(purchase);
                    break;
                case SHOW_LIST_OF_PURCHASES:
                    while (true) {
                        System.out.println(ShowListMenu.getMenuStr());
                        int choiceForShowList = SCANNER.nextInt();
                        SCANNER.nextLine();
                        if (ShowListMenu.getInstance(choiceForShowList).equals(ShowListMenu.BACK)) {
                            System.out.println();
                            break;
                        } else {
                            System.out.println(purchase.showList(choiceForShowList));
                        }
                    }
                    break;
                case BALANCE:
                    System.out.println(purchase.showBalance());
                    break;
                case SAVE:
                    purchase.savePurchases();
                    System.out.println("\nPurchases were saved!\n");
                    break;
                case LOAD:
                    purchase.loadPurchase();
                    System.out.println("\nPurchases were loaded!\n");
                    break;
                case ANALYZE:
                    showSort(purchase);
                    break;
                case EXIT:
                    quit = true;
                    System.out.println("\nBye!");
                    SCANNER.close();
                    break;
                case UNDEFINED:
                    System.out.println("\nSomething went wrong.\n");
            }
        }
    }

    private static void addPurchase(Purchase purchase) {
        String name = null;
        double price = 0.0;
        while (true) {
            System.out.println(AddPurchaseMenu.getMenuStr());
            int choice = SCANNER.nextInt();
            SCANNER.nextLine();
            if (!AddPurchaseMenu.getInstance(choice).equals(AddPurchaseMenu.BACK)) {
                try {
                    System.out.println("\nEnter purchase name:");
                    name = SCANNER.nextLine();
                    System.out.println("Enter its price:");
                    price = SCANNER.nextDouble();
                    SCANNER.nextLine();
                } catch (InputMismatchException e) {
                    e.printStackTrace();
                }
                if (purchase.addProduct(new Product(name, price, ProductType.getInstance(choice)))) {
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
        SortMenu sortType = SortMenu.UNDEFINED;
        while (!sortType.equals(SortMenu.BACK)) {
            System.out.println(SortMenu.getMenuStr());
            int choice = SCANNER.nextInt();
            sortType = SortMenu.getInstance(choice);
            Analyzer analyzer = getAnalyzerStrategy(sortType);
            if (analyzer != null) {
                System.out.println(analyzer.getSortedResult(purchase));
            }
        }
        System.out.println();
    }

    public static Analyzer getAnalyzerStrategy(SortMenu sortType) {
        switch (sortType) {
            case SORT_ALL:
                return new AllProductsAnalyzer();
            case SORT_TYPES:
                return new ProductsByTypeAnalyzer();
            case SORT_IN_TYPE:
                System.out.println(ProductType.getMenuStr());
                int choiceForCategory = SCANNER.nextInt();
                return new CertainTypeAnalyzer(ProductType.getInstance(choiceForCategory));
        }
        return null;
    }
}
