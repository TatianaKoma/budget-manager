package ua.griddynamics;

public class Menu {
    public static void menuMain() {
        System.out.println("Choose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Balance\n" +
                "5) Save\n" +
                "6) Load\n" +
                "7) Analyze (Sort)\n" +
                "0) Exit");
    }

    public static void menuAddPurchase() {
        System.out.println(" \n Choose the type of purchase\n" +
                " 1) Food\n" +
                " 2) Clothes\n" +
                " 3) Entertainment\n" +
                " 4) Other\n" +
                " 5) Back");
    }

    public static void menuShowList() {
        System.out.println("\nChoose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) All\n" +
                "6) Back");
    }

    public static void menuShowSort() {
        System.out.println("\n" +
                "How do you want to sort?\n" +
                "1) Sort all purchases\n" +
                "2) Sort by type\n" +
                "3) Sort certain type\n" +
                "4) Back");
    }

    public static void menuCategory() {
        System.out.println("\nChoose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other");
    }
}
