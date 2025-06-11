package com.system;

import com.handler.DoctorLoginService;
import com.handler.DoctorRecordService;
import com.entity.Doctor;
import java.util.Scanner;

public class DoctorMenu {

    private static Doctor currentDoctor = null;

    public static void run(Scanner scanner) {
        if (!login(scanner)) {
            System.out.println("\nLogin failed. Returning to main portal.");
            return;
        }

        String choice;
        do {
            showDoctorMenu();
            System.out.print("Select doctor menu: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1" -> viewUpcomingAppointments();
                case "2" -> processNextAppointment();
                case "0" -> logout();
                default -> System.out.println("Invalid choice. Please try again.");
            }
            if (!choice.equals("0")) {
                Helper.pressEnter(scanner);
            }
        } while (!choice.equals("0"));
    }

    private static void showDoctorMenu() {
        System.out.println("\n==== Doctor Portal ====");
        System.out.println("Logged in as: Dr. " + currentDoctor.getName() + " (ID: " + currentDoctor.getId() + ")");
        System.out.println("1. View Upcoming Appointments");
        System.out.println("2. Process Next Appointment");
        System.out.println("0. Logout");
    }

    private static boolean login(Scanner scanner) {
        System.out.println("\n--- Doctor Login ---");
        int id = Helper.integerInput(scanner, "Please enter your Doctor ID: ");
        System.out.print("Please enter your password: ");
        String password = scanner.nextLine();

        Doctor authenticatedDoctor = DoctorRecordService.authenticateDoctor(id, password);

        if (authenticatedDoctor != null) {
            DoctorLoginService.loginDoctor(authenticatedDoctor);
            currentDoctor = authenticatedDoctor;
            return true;
        }

        return false;
    }

    private static void logout() {
        if (currentDoctor != null) {
            DoctorLoginService.logoutDoctor(currentDoctor.getId());
            currentDoctor = null;
            System.out.println("You have been logged out. Returning to main portal...");
        }
    }

    private static void viewUpcomingAppointments() {
        // Uncomment and implement if AppointmentQueueService is available
        // AppointmentQueueService.viewUpcomingAppointments();
        System.out.println("Feature not implemented yet.");
    }

    private static void processNextAppointment() {
        // Uncomment and implement if AppointmentQueueService is available
        // AppointmentQueueService.processNextAppointment();
        System.out.println("Feature not implemented yet.");
    }
}