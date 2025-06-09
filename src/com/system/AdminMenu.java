package com.system;

import com.datastructure.LinkedList;
import com.entity.Doctor;
import com.entity.Patient;
import com.handler.*;

import java.util.Scanner;

public class AdminMenu {
    private static Scanner scanner = new Scanner(System.in);

    public static void run(Scanner s){
        if (!login(scanner)) {
            System.out.println("\nAdmin login failed. Returning to main portal.");
            return;
        }

        System.out.println("\nAdmin login successful!");
        scanner = s;
        String choice;
        do {
            showAdminMenu();
            System.out.print("Pilih menu\t: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    addNewPatient();
                    PatientRecordService.updatePatientTXT();
                    Helper.pressEnter();
                    break;
                }
                case "2" -> {
                    removePatientId();
                    PatientRecordService.updatePatientTXT();
                    Helper.pressEnter();
                    break;
                }
                case "3" -> {
                    searchPatientName();
                    Helper.pressEnter();
                    break;
                }
                case "4" -> {
                    displayPatientList();
                    Helper.pressEnter();
                    break;
                }
                case "5" -> {
                    doctorLogin();
                    Helper.pressEnter();
                    break;
                }
                case "6" -> {
                    doctorLogout();
                    Helper.pressEnter();
                    break;
                }
                case "7" -> {
                    viewLoggedInDoctors();
                    Helper.pressEnter();
                    break;
                }
//                case "8" -> {
//                    AppointmentQueueService.viewUpcomingAppointments();
//                    break;
//                }
//                case "9" -> {
//                    AppointmentQueueService.processNextAppointment();
//                    break;
//                }
//                case "10" -> {
//                    displayAppointments();
//                    Helper.pressEnter();
//                    break;
//                }
                case "11" -> {
                    searchPatientById();
                    Helper.pressEnter();
                    break;
                }
                case "12" -> {
                    displayPatientsInOrder();
                    Helper.pressEnter();
                    break;
                }
                case "13" -> {
                    doctorRegister();
                    Helper.pressEnter();
                    break;
                }
                case "0" -> {
                    System.out.println("Thanks for using Daisuke Clinic!");
                    System.out.println("Exiting.....");
                    break;
                }
                default -> {
                    System.out.println("Pilihan tidak valid");
                }
            }
            if (!choice.equals("0")) {
                Helper.pressEnter();
            }
        } while (!(choice.equals("0")));
    }

    // ==================================================================
    private static boolean login(Scanner scanner) {
        System.out.println("\n--- Admin Login ---");
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        // cek jika berisi admin
        return username.equals("admin") && password.equals("admin");
    }
    public static void showAdminMenu() {
        System.out.println("\n==== Data Manager Pro ====");
        System.out.println("1. Add New Patient");
        System.out.println("2. Remove Patient by ID");
        System.out.println("3. Search Patient by Name");
        System.out.println("4. Display All Patient");
        System.out.println("5. Doctor Login");
        System.out.println("6. Doctor Logout");
        System.out.println("7. View Last Logged-in Doctor");
        System.out.println("8. Schedule Appointment");
        System.out.println("9. Process Appointment");
        System.out.println("10. Display Upcoming Appointments");
        System.out.println("11. Search Patient by ID (BST)");
        System.out.println("12. Display All Patients (BST Inorder)");
        // System.out.println("13. Register New Doctor");
        System.out.println("0. Exit");
    }

    // =========================== funtion 1 ===========================
    public static void addNewPatient() {
        System.out.println("======= Add New Patient =======\n");
        int newId = Helper.integerInput(scanner, "Masukkan ID Pasien: "); // input ID
        System.out.print("Masukkan Nama Pasien: "); // input name
        String newName = scanner.nextLine();
        int newAge = Helper.integerInput(scanner, "Masukkan Umur Pasien: "); // input age
        System.out.print("Masukkan Alamat Pasien: "); // input address
        String newAddress = scanner.nextLine();
        System.out.print("Masukkan Nomor Telepon Pasien (Contoh 0812-3456-7890): "); // input phone number
        String newPhoneNumber = scanner.nextLine();

        Patient newPatient = new Patient(newId, newName, newAge, newAddress, newPhoneNumber);
        PatientRecordService.addPatient(newPatient);
    }

    // =========================== funtion 2 ===========================
    public static void removePatientId() {
        System.out.println("======= Remove Patient by ID =======\n");
        int toRemoveID = Helper.integerInput(scanner, "Masukkan ID Patient: ");

        PatientRecordService.removePatientById(toRemoveID);
    }

    // =========================== funtion 3 ===========================
    public static void searchPatientName() {
        System.out.println("======= Search Patient by Name =======\n");
        System.out.println("Masukkan nama Patient: ");
        String patientName = scanner.nextLine();
        PatientRecordService.findPatientByName(patientName);
    }

    // =========================== funtion 4 ===========================
    public static void displayPatientList() {
        System.out.println("======= Display All Patient =======\n");
        PatientRecordService.displayAllPatients();
    }

    // =========================== funtion 5 ===========================
    private static void doctorLogin() {
        System.out.println("\n======= Doctor Login =======");
        System.out.println("All Registered Doctors:");
        LinkedList<Doctor> allDoctors = DoctorRecordService.getAllDoctors();
        for(int i = 0; i < allDoctors.size(); i++) {
            Doctor d = allDoctors.get(i);
            System.out.printf("  ID: %d, Name: Dr. %s, Specialty: %s\n", d.getId(), d.getName(), d.getSpecialty());
        }

        int idDokter = Helper.integerInput(scanner, "\nEnter Doctor ID to login: ");

        // Find the doctor from the master record service
        Doctor doctorToLogin = DoctorRecordService.findDoctorById(idDokter);

        if (doctorToLogin != null) {
            DoctorLoginService.loginDoctor(doctorToLogin);
        } else {
            System.out.println("Doctor with ID " + idDokter + " not found.");
        }
    }

    // =========================== funtion 6 ===========================
    private static void doctorLogout() {
        System.out.println("\n======= Doctor Logout =======");
        int doctorId = Helper.integerInput(scanner, "Enter Doctor ID to logout: ");
        DoctorLoginService.logoutDoctor(doctorId);
    }

    // =========================== funtion 7 ===========================
    private static void viewLoggedInDoctors() {
        System.out.println("\n======= View Logged-in Doctors =======");
        DoctorLoginService.getAllLoggedInDoctors();
    }

    // =========================== funtion 8 ===========================

    // =========================== funtion 9 ===========================

    // =========================== funtion 10 ===========================
//    private static void displayAppointments() {
//        System.out.println("\n======= Display Upcoming Appointments =======");
//        AppointmentQueueService.viewUpcomingAppointments();
//    }

    // =========================== funtion 11 ===========================
    private static void searchPatientById() {
        System.out.println("\n======= Search Patient by ID =======");
        int idPasien = Helper.integerInput(scanner, "Enter Patient ID to search: ");
        PatientRecordService.findPatientById(idPasien);
    }

    // =========================== funtion 12 ===========================
    private static void displayPatientsInOrder() {
        System.out.println("\n======= Display All Patients (Sorted by ID) =======");
        PatientSearchTreeService.inOrderDisplay();
    }

    // =========================== funtion 13 ===========================
    public static void doctorRegister() {
        System.out.println("\n======= Register New Doctor =======");
        int newId = Helper.integerInput(scanner, "Enter new Doctor ID: ");
        System.out.print("Enter Doctor Name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter Doctor Specialty: ");
        String newSpecialty = scanner.nextLine();
        System.out.print("Enter a temporary Password for the doctor: ");
        String newPassword = scanner.nextLine();

        Doctor newDoctor = new Doctor(newId, newName, newSpecialty, newPassword);
        DoctorRecordService.registerDoctor(newDoctor);
    }

}