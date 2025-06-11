package com.main;

import com.entity.Doctor;
import com.handler.DoctorRecordService;
import com.handler.PatientRecordService;
import com.handler.PatientSearchTreeService;
import com.system.AdminMenu;
import com.system.DoctorMenu;
import com.system.Helper;
import com.system.PatientMenu;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main (String[]args){
        // Step 1: Initialize all system services and data ONCE.
        initializeSystem();

        // Step 2: Run the main login loop.
        runLoginPortal();

        // Step 3: Close the scanner when the program truly exits.
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
                    Helper.pressEnter();
                    break;
            }
        } while (!choice.equals("0"));
    }
}
