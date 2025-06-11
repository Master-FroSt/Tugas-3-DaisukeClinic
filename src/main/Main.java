package main;

import entity.Doctor;
import handler.DoctorRecordService;
import handler.PatientRecordService;
import handler.PatientSearchTreeService;
import system.AdminMenu;
import system.DoctorMenu;
import system.Helper;
import system.PatientMenu;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main (String[]args){
        initializeSystem();

        runLoginPortal();

        scanner.close();
    }
    public static void initializeSystem() {
        System.out.println("Initializing Daisuke Clinic System...");

        // Initialize all services
        PatientRecordService.makePatientRecord();
        PatientSearchTreeService.makePatientTree();
//        AppointmentQueueService.makeAppointmentQueue();
        DoctorRecordService.makeDoctorRecord();

        // Pre-load patient data from file
        PatientRecordService.loadPatientTXT();

        // Pre-load some doctors into the new DoctorRecordService
        DoctorRecordService.registerDoctor(new Doctor(101, "Asep Surasep", "Cardiology", "pass101"));
        DoctorRecordService.registerDoctor(new Doctor(102, "Budi Budiman", "Neurology", "pass102"));
        DoctorRecordService.registerDoctor(new Doctor(103, "Cici Lestari", "Pediatrics", "pass103"));
        System.out.println("System ready. Welcome!");
    }

    public static void runLoginPortal() {
        String choice;
        do {
            System.out.println("\n==== Welcome to Daisuke Clinic ====");
            System.out.println("Please select your role:");
            System.out.println("1. Admin");
            System.out.println("2. Doctor");
            System.out.println("3. Patient");
            System.out.println("0. Exit Program");
            System.out.print("Enter your choice: ");

            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // Run the Admin Menu
                    AdminMenu.run(scanner);
                    break;
                case "2":
                    // Run the Doctor Menu
                    DoctorMenu.run(scanner);
                    break;
                case "3":
                    // Run the Patient Menu
                    PatientMenu.run(scanner);
                    break;
                case "0":
                    System.out.println("Thank you for using our system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    Helper.pressEnter(scanner);
                    break;
            }
        } while (!choice.equals("0"));
    }
}
