package com.system;

import com.entity.Appointment;
import com.entity.Doctor;
import com.entity.Patient;
import com.handler.AppointmentQueueService;
import com.handler.DoctorLoginService;
import com.handler.PatientRecordService;
import com.handler.PatientSearchTreeService;

import java.util.Scanner;

public class MainMenu {
    private static Scanner scanner = new Scanner(System.in);

    public static void showMenu() {
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

    public static void run(Scanner s) {
        scanner = s;
        String choice;
        do {
            showMenu();
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
                    System.out.println("Masukkan ID Dokter: ");
                    int idDokter = s.nextInt();
                    System.out.println("Masukkan nama Dokter: ");
                    String namaDokter = s.nextLine();
                    System.out.println("Masukkan Spesialisasi Dokter: ");
                    String spesialisasi = s.nextLine();
                    System.out.println("Masukkan loginTime Dokter: ");
                    String loginTime = s.nextLine();
                    Doctor newDoctor = new Doctor(idDokter, namaDokter, spesialisasi, loginTime);
                    DoctorLoginService.loginDoctor(newDoctor);
                    break;
                    break;
                }
                case "6" -> {
                    System.out.println("Masukkan ID dokter");
                    DoctorLoginService.logoutDoctor(s.nextInt());
                    break;
                }
                case "7" -> {
                    DoctorLoginService.getAllLoggedInDoctors();
                    break;
                }
                case "8" -> {
                    AppointmentQueueService.viewUpcomingAppointments();
                    break;
                }
                case "9" -> {
                    AppointmentQueueService.processNextAppointment();
                    break;
                }
                case "10" -> {
                    System.out.println("Masukkan ID code.entity.Appointment: ");
                    int idAppointment = s.nextInt();
                    System.out.println("Masukkan ID Pasien: ");
                    int idJadwalPatient = s.nextInt();
                    System.out.println("Masukkan ID Dokter: ");
                    int idJadwaldokter = s.nextInt();
                    System.out.println("Masukkan time: ");
                    String time = s.nextLine();
                    Appointment newAppointment = new Appointment(idAppointment,  idJadwalPatient, idJadwaldokter, time);
                    AppointmentQueueService.scheduleAppointment(newAppointment);
                    break;
                }
                case "11" -> {
                    System.out.println("Masukkan ID Pasien: ");
                    int idPasien = s.nextInt();
                    PatientSearchTreeService.searchPatient(id);
                    break;
                }
                case "12" -> {
                    PatientSearchTreeService.inOrderDisplay();
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
        } while (!(choice.equals("0")));
    }

    // =============================================================================================================================================================
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
    public static void doctorLogin() {

    }

    public static void doctorRegister() {

    }

    // =========================== funtion 6 ===========================

    // =========================== funtion 7 ===========================

    // =========================== funtion 8 ===========================

    // =========================== funtion 9 ===========================

    // =========================== funtion 10 ===========================

    // =========================== funtion 11 ===========================

    // =========================== funtion 12 ===========================
}