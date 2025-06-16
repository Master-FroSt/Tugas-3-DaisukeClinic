package system;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    public static void pressEnter(Scanner scanner) {
        System.out.print("\n\nPress Enter to Continue....");
        scanner.nextLine(); // Wait for Enter key
//         clearScreen();
    }

    public static LocalTime timeInput(Scanner scanner, String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return LocalTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Format waktu tidak valid. Gunakan format HH:mm (contoh: 08:30).");
            }
        }
    }
}
