package com.system;

import java.util.Scanner;

public class Helper {
    public static int integerInput(Scanner scanner, String prompt) {
        int number;

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                number = Integer.parseInt(input);
                return number; // keluar kalau input valid
            } catch (NumberFormatException e) {
                System.out.println("Input not valid!! Input must be an integer!!");
            }
        }
    }
}