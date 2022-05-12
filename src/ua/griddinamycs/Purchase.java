package ua.griddinamycs;

import java.io.*;
import java.util.*;

public class Purchase {
    private Map<Category, List<Product>> purchasesList = new HashMap<>();
    Scanner scanner = new Scanner(System.in);

    double income = 0.0d;
    double balance = 0.0d;

    public double getIncome() {
        return income;
    }

    public void addIncome() {
        double income1 = 0;
        try {
            System.out.println("\nEnter income:");
            income1 = scanner.nextDouble();
            scanner.nextLine();
            if (income < 0.0) {
                System.out.println("Income must be positive! Try again:");
            }
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
        income += income1;
        System.out.println("Income was added!\n");
    }

    public double getBalance() {
        double balance = 0.0;
        balance += getIncome();
        balance -= getTotalPrice();
        return balance;
    }

    public void addPurchases() {
        boolean isAddingStoped = false;
        while (!isAddingStoped) {
            System.out.println(" \n Choose the type of purchase\n" +
                    " 1) Food\n" +
                    " 2) Clothes\n" +
                    " 3) Entertainment\n" +
                    " 4) Other\n" +
                    " 5) Back");
            int userChoice = scanner.nextInt();
            switch (userChoice) {
                case 1:
                    addOnePurchase(Category.FOOD);
                    break;
                case 2:
                    addOnePurchase(Category.CLOTHES);
                    break;
                case 3:
                    addOnePurchase(Category.ENTERTAINMENT);
                    break;
                case 4:
                    addOnePurchase(Category.OTHER);
                    break;
                case 5:
                    isAddingStoped = true;
                    System.out.println();
                    break;
                default:
                    break;
            }
        }
    }

    public void showListOfPurchases() {
        boolean isShowingStoped = false;
        while (!isShowingStoped) {
            System.out.println("\nChoose the type of purchase\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) All\n" +
                    "6) Back");
            int userChoice = scanner.nextInt();
            switch (userChoice) {
                case 1 -> showByCategory(Category.FOOD);
                case 2 -> showByCategory(Category.CLOTHES);
                case 3 -> showByCategory(Category.ENTERTAINMENT);
                case 4 -> showByCategory(Category.OTHER);
                case 5 -> showAllPurchases();
                case 6 -> {
                    isShowingStoped = true;
                    System.out.println();
                }
                default -> {
                }
            }
        }
    }

    public void showSortMenu() {
        boolean isSortingStopped = false;
        while (!isSortingStopped) {
            System.out.println("\n" +
                    "How do you want to sort?\n" +
                    "1) Sort all purchases\n" +
                    "2) Sort by type\n" +
                    "3) Sort certain type\n" +
                    "4) Back");
            int userChoice = scanner.nextInt();
            switch (userChoice) {
                case 1 -> showAllSortedPurchases();
                case 2 -> showSortByCategories();
                case 3 -> showCategoryMenu();
                case 4 -> {
                    isSortingStopped = true;
                    System.out.println();
                }
                default -> {
                }
            }
        }
    }

    public void showCategoryMenu() {
        System.out.println("\nChoose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other");
        int userChoice = scanner.nextInt();
        switch (userChoice) {
            case 1 -> showSortInCategory(Category.FOOD);
            case 2 -> showSortInCategory(Category.CLOTHES);
            case 3 -> showSortInCategory(Category.ENTERTAINMENT);
            case 4 -> showSortInCategory(Category.OTHER);
        }
    }

    public void addOnePurchase(Category category) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter purchase: ");
        String name = scanner.nextLine();
        System.out.println("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());
        Product product = new Product(name, price);
        List<Product> productList = purchasesList.getOrDefault(category, new ArrayList<>());
        purchasesList.put(category, productList);
        productList.add(product);
        balance -= price;
        if (this.balance < 0.0d) {
            this.balance = 0.0d;
        }
        System.out.println("Purchase was added!");
    }

    public void showByCategory(Category category) {
        if (purchasesList.get(category) == null) {
            System.out.println("\n" + category + ": ");
            System.out.println("The purchase list is empty!");
        }
        if (purchasesList.containsKey(category)) {
            double totalPrice = 0.0;
            System.out.println("\n" + category + ": ");
            for (int i = 0; i < purchasesList.get(category).size(); i++) {
                System.out.println(purchasesList.get(category).get(i).getName() + " $" +
                        purchasesList.get(category).get(i).getPrice());
                totalPrice += purchasesList.get(category).get(i).getPrice();
            }

            System.out.printf("Total sum: $%.2f%n", totalPrice);
        }
    }

    private void showAllPurchases() {
        System.out.println("\nAll: ");
        purchasesList.values().stream()
                .flatMap(List::stream)
                .forEach(x -> System.out.println(x.getName() + " $" + x.getPrice()));

        System.out.printf("Total sum: $%.2f%n", getTotalPrice());
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

    public void saveBudget() {
        try (FileWriter fw = new FileWriter("purchases.txt");
             PrintWriter pw = new PrintWriter(fw)) {
            pw.printf("%.2f\n", getBalance());
            for (Category category : purchasesList.keySet()) {
                for (Product product : purchasesList.get(category)) {
                    pw.printf("%s~%s~%.2f\n", category, product.getName(), product.getPrice());
                }
            }
            System.out.println("\nPurchases were saved!\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void loadBudget() throws IOException {
        File file = new File("purchases.txt");
        if (file.createNewFile()) {
            System.out.println("\nPurchases were loaded!\n");
        } else {
            purchasesList = new HashMap<>();
            try (FileReader fr = new FileReader("purchases.txt");
                 BufferedReader br = new BufferedReader(fr)) {
                String balanceStr = br.readLine();
                if (!balanceStr.isEmpty()) {
                    balance = Double.parseDouble(balanceStr.replace(',', '.'));
                } else {
                    balance = 0.00;
                }
                String productStr;
                while ((productStr = br.readLine()) != null) {
                    String[] parsedPurchase = productStr.split("~");
                    Category category = Category.valueOf(parsedPurchase[0]);
                    String name = parsedPurchase[1];
                    double price = Double.parseDouble(parsedPurchase[2].replace(',', '.'));
                    List<Product> productList = purchasesList.getOrDefault(category, new ArrayList<>());
                    purchasesList.put(category, productList);
                    productList.add(new Product(name, price));
                }
                System.out.println("\nPurchases were loaded!\n");
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public void showAllSortedPurchases() {
        if (purchasesList.size() == 0) {
            System.out.println("\nThe purchase list is empty!");
        } else {
            List<Product> productsList = new ArrayList<>();
            for (List<Product> products : purchasesList.values()) {
                productsList.addAll(products);
            }
            System.out.println("\nAll: ");
            productsList.stream()
                    .sorted(Comparator.comparing(Product::getPrice).reversed())
                    .forEach(x -> System.out.println(x.getName() + " $" + x.getPrice()));
            System.out.printf("Total sum: $%.2f%n", getTotalPrice());

        }
    }

    public void showSortByCategories() {
        System.out.println("\nTypes: ");
        Map<Category, Double> mapForSorting = new HashMap<>();
        for (Category category : Category.values()) {
            if (purchasesList.containsKey(category)) {
                double totalPrice = 0;
                for (Product product : purchasesList.get(category)) {
                    totalPrice += product.getPrice();
                }
                mapForSorting.put(category, totalPrice);
            } else {
                mapForSorting.put(category, 0.00);
            }
        }
        mapForSorting.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> System.out.println(x.getKey() + " $" + x.getValue()));

        System.out.printf("Total sum: $%.2f%n", getTotalPrice());
    }

    public void showSortInCategory(Category category) {
        if (purchasesList.get(category) == null) {
            System.out.println("\nThe purchase list is empty!");
        }
        if (purchasesList.containsKey(category)) {
            double totalPrice = 0;
            System.out.println("\n" + category + ": ");
            purchasesList.get(category).stream()
                    .sorted(Comparator.comparing(Product::getPrice).reversed())
                    .forEach(x -> System.out.println(x.getName() + " $" + x.getPrice()));

            for (Product product : purchasesList.get(category)) {
                totalPrice += product.getPrice();
            }

            System.out.printf("Total sum: $%.2f%n", totalPrice);
        }
    }

    public void reset() {
        try (FileWriter fw = new FileWriter("purchases.txt");
             PrintWriter pw = new PrintWriter(fw)) {
            pw.printf("%.2f\n", 0.00);
            for (Category category : Category.values()) {
                purchasesList.put(category, new ArrayList<Product>());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
