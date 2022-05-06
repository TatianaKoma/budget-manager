package ua.griddinamycs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Purchase {
    private final Map<Category, List<Product>> purchasesList = new HashMap<>();
    Scanner scanner = new Scanner(System.in);
    double income = 0;
    double balance;

    public double getIncome() {
        return income;
    }

    public void addIncome(double inputIncome) {
        income += inputIncome;
        System.out.println("Income was added!");
    }

    public double getBalance() {
        double balance = 0;
        balance += getIncome();
        balance -= getTotalPrice();
        return balance;
    }

    public void addPurchases() {
        boolean isAddingStoped = false;
        while (!isAddingStoped) {
            System.out.println("""
                    Choose the type of purchase
                    1) Food
                    2) Clothes
                    3) Entertainment
                    4) Other
                    5) Back""");
            int userChoice = scanner.nextInt();
            switch (userChoice) {
                case 1:
                    System.out.println();
                    addOnePurchase(Category.FOOD);
                    System.out.println();
                    break;
                case 2:
                    System.out.println();
                    addOnePurchase(Category.CLOTHES);
                    System.out.println();
                    break;
                case 3:
                    System.out.println();
                    addOnePurchase(Category.ENTERTAINMENT);
                    System.out.println();
                    break;
                case 4:
                    System.out.println();
                    addOnePurchase(Category.OTHER);
                    System.out.println();
                    break;
                case 5:
                    isAddingStoped = true;
                    break;
                default:
                    break;
            }
        }
    }

    public void showListOfPurchases() {
        boolean isShowingStoped = false;
        while (!isShowingStoped) {
            System.out.println("""
                    Choose the type of purchases
                    1) Food
                    2) Clothes
                    3) Entertainment
                    4) Other
                    5) All
                    6) Back""");
            int userChoice = scanner.nextInt();
            switch (userChoice) {
                case 1:
                    System.out.println();
                    showByCategory(Category.FOOD);
                    System.out.println();
                    break;
                case 2:
                    System.out.println();
                    showByCategory(Category.CLOTHES);
                    System.out.println();
                    break;
                case 3:
                    System.out.println();
                    showByCategory(Category.ENTERTAINMENT);
                    System.out.println();
                    break;
                case 4:
                    System.out.println();
                    showByCategory(Category.OTHER);
                    System.out.println();
                    break;
                case 5:
                    System.out.println();
                    showAllPurchases();
                    System.out.println();
                    break;
                case 6:
                    isShowingStoped = true;
                    break;
                default:
                    break;
            }
        }
    }

    public void addOnePurchase(Category category) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter purchase: ");
        String name = scanner.nextLine();
        System.out.println("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());
        Product product = new Product(name, price);
        purchasesList.computeIfAbsent(category, k -> new ArrayList<>()).add(product);
        System.out.println("Purchase was added!");
    }

    public void showByCategory(Category category) {
        if (purchasesList.get(category) == null) {
            System.out.println(category + ": ");
            System.out.println("The purchase list is empty!");
        }
        if (purchasesList.containsKey(category)) {
            double totalPrice = 0;
            System.out.println(category + ": ");
            for (int i = 0; i < purchasesList.get(category).size(); i++) {
                System.out.println(purchasesList.get(category).get(i).getName() + " $" +
                        purchasesList.get(category).get(i).getPrice());
                totalPrice += purchasesList.get(category).get(i).getPrice();
            }

            System.out.printf("Total sum: $%.2f%n", totalPrice);
        }
    }

    private void showAllPurchases() {
        System.out.println("All: ");
        purchasesList.values().stream()
                .flatMap(List::stream)
                .forEach(x -> System.out.println(x.getName() + " $" + x.getPrice()));

        double totalPrice = getTotalPrice();
        System.out.printf("Total sum: $%.2f%n", totalPrice);
    }

    public boolean checkIsEmpty() {
        return purchasesList.size() == 0;
    }

    public double getTotalPrice() {
        return purchasesList.values().stream()
                .flatMap(List::stream)
                .map(Product::getPrice)
                .reduce(0.0, Double::sum);
    }

    public void saveBudget() throws IOException {
        File file = new File("purchases.txt");
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.printf("%.2f\n", getBalance());
            for (Category category : purchasesList.keySet()) {
                for (Product product : purchasesList.get(category)) {
                    writer.printf("%s~%s~%.2f\n", category, product.getName(), product.getPrice());
                }
            }
            writer.close();
            System.out.println("Purchases were saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBudget() throws FileNotFoundException {
        Map<Category, List<Product>> purchasesList = new HashMap<>();
        File file = new File("purchases.txt");
        Scanner reader = new Scanner(file);
        try {
            reader = new Scanner(file);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        this.balance = Double.parseDouble(reader.nextLine().replace(',', '.'));
        while (reader.hasNext()) {
            String[] parsedPurchase = reader.nextLine().split("~");
            String category = parsedPurchase[0];
            String name = parsedPurchase[1];
            double price = Double.parseDouble(parsedPurchase[2].replace(',', '.'));
            Product product = new Product(name, price);
            purchasesList.computeIfAbsent(checkCategory(category), k -> new ArrayList<>()).add(product);
        }
        System.out.println("\nPurchases were loaded!");
    }

    public Category checkCategory(String input) {
        if (input.equals("FOOD")) {
            return Category.FOOD;
        } else if (input.equals("CLOTHES")) {
            return Category.CLOTHES;
        } else if (input.equals("ENTERTAINMENT")) {
            return Category.ENTERTAINMENT;
        } else {
            return Category.OTHER;
        }
    }
}
