package ua.griddynamics;

public class Validation {
    static int validChoiceForAdding(int choice) {
        if (choice < 1 || choice > 5) {
            System.out.println("Enter number between 1 and 5!");
        } else if (choice == 5) {
            System.out.println();
        }
        return choice;
    }

    static int validChoiceForPrinting(int choice) {
        if (choice < 1 || choice > 6) {
            System.out.println("Enter number between 1 and 6!");
        } else if (choice == 6) {
            System.out.println();
        }
        return choice;
    }
}
