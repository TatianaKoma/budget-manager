package ua.griddynamics;

import java.io.Serializable;

public class Balance implements Serializable {
    private double balance;

    public Balance() {
        this.balance = 0.0;
    }

    public double getBalance() {
        return balance;
    }

    public void addIncome(double income) {
        this.balance += income;
    }

    public String showBalance() {
        return "\nBalance: $" + String.format("%.2f", getBalance()) + "\n";
    }

    public void reduceBalance(double delta) {
        if (this.balance >= delta) {
            this.balance -= delta;
        } else {
            this.balance = 0.0;
        }
    }
}
