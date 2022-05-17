package ua.griddynamics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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

    public void addIncome(double income, User user) {
            if (income < 0.0) {
                System.out.println("Income must be positive! Try again:");
                addIncome(income, user);
            }
        this.balance += income;
        System.out.println("Income was added!\n");
        saveUserToFile(user);
    }

    public void showBalance(User user) {
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

    public void updateBalance(double price, User user) {
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
