package ua.griddynamics;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        CountMoney.routine(SCANNER);
    }
}
