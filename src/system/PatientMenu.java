package system;

import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import handler.*;
import java.util.Scanner;

/**
 * This class handles the menu and actions available to a Patient.
 */
public class PatientMenu {

    public static void run(Scanner scanner) {
        String choice;
        do {
            showPatientMenu();
            System.out.print("Select patient menu: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1" -> searchPatientSelf(scanner);
                case "2" -> AdminMenu.viewLoggedInDoctors();
                case "3" -> AdminMenu.scheduleAppointment();
                case "0" -> System.out.println("\nReturning to main login portal...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
            if (!choice.equals("0")) {
                Helper.pressEnter(scanner);
            }
        } while (!choice.equals("0"));
    }

    private static void showPatientMenu() {
        System.out.println("\n==== Patient Portal ====");
        System.out.println("1. View My Patient Record");
        System.out.println("2. View Available Doctors");
        System.out.println("3. Schedule an Appointment");
        System.out.println("0. Back to Main Portal");
    }

    private static void searchPatientSelf(Scanner scanner) {
        System.out.println("\n--- View My Patient Record ---");
        int id = Helper.integerInput(scanner, "Enter your Patient ID: ");
        PatientSearchTreeService.searchPatient(id);
    }
}