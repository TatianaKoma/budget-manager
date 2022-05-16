package ua.griddynamics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class User implements Serializable {
    private double balance;
    private double income;

    public User() {
        this.balance = 0.0;
        this.income = 0.0;
    }

    public double getBalance() {
        return balance;
    }

    void addIncomeFromMainMenu(Scanner scanner, User user) {
        addIncome(scanner, user);
    }

    void showBalanceFromMainMenu(User user) {
        showBalance(user);
    }

    private void addIncome(Scanner scanner, User user) {
        try {
            System.out.println("\nEnter income:");
            income = scanner.nextDouble();
            scanner.nextLine();
            if (income < 0.0) {
                System.out.println("Income must be positive! Try again:");
                addIncome(scanner, user);
            }
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
        this.balance += income;
        System.out.println("Income was added!\n");
        saveUserToFile(user);
    }

    private void showBalance(User user) {
        boolean hasNext = true;
        if (!new File("purchases.txt").exists()) {
            System.out.println("\nBalance: $0.00\n");
        } else {
            try (FileInputStream fileInputStream = new FileInputStream("purchases.txt");
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                while (hasNext) {
                    if (fileInputStream.available() != 0) {
                        Object object = objectInputStream.readObject();
                        if (object instanceof User) {
                            user = (User) object;
                            hasNext = false;
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            System.out.println("\nBalance: $" + String.format("%.2f", user.getBalance()) + "\n");
        }
    }
    void updateBalance(double price, User user) {
        if (this.balance >= price) {
            this.balance -= price;
        } else {
            this.balance = 0.0;
        }
        saveUserToFile(user);
    }

    private void saveUserToFile(User user) {
        try (FileOutputStream fileOutputStream = new FileOutputStream("purchases.txt");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
