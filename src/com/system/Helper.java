package com.system;

import com.entity.Appointment;
import com.entity.Doctor;
import com.entity.Patient;
import com.handler.PatientRecordService;

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

    public static void menuUtama() {
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
        System.out.print("Option: ");
    }

    public static void run (Scanner s) {
        String choice;
        do {
            menuUtama();
            System.out.println("Pilih menu\t: ");
            choice = s.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.println("======= Add New Patient =======\n");
                    int newId = Helper.integerInput(s, "Masukkan ID Pasien: "); // input ID
                    System.out.print("Masukkan Nama Pasien: "); // input name
                    String newName = s.nextLine();
                    int newAge = Helper.integerInput(s, "Masukkan Umur Pasien: "); // input age
                    System.out.print("Masukkan Alamat Pasien: "); // input address
                    String newAddress = s.nextLine();
                    System.out.print("Masukkan Nomor Telepon Pasien (Contoh 0812-3456-7890): "); // input phone number
                    String newPhoneNumber = s.nextLine();

                    Patient newPatient = new Patient(newId, newName, newAge, newAddress, newPhoneNumber);
                    PatientRecordService.addPatient(newPatient);
                    break;
                }
                case "2" -> {
                    System.out.println("======= Remove Patient by ID =======\n");
                    int toRemoveID = Helper.integerInput(s, "Masukkan ID Patient: ");

                    PatientRecordService.removePatientById(toRemoveID);
                    break;
                }
                case "3" -> {
                    System.out.println("======= Search Patient by Name =======\n");
                    System.out.println("Masukkan nama Patient: ");
                    String patientName = s.nextLine();
                    PatientRecordService.findPatientByName(patientName);
                    break;
                }
                case "4" -> {
                    System.out.println("======= Display All Patient =======\n");
                    PatientRecordService.displayAllPatients();
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
                    doctorList.loginDoctor(newDoctor);
                    break;
                    break;
                }
                case "6" -> {
                    System.out.println("Masukkan ID dokter");
                    doctorList.logoutDoctor(s.nextInt());
                    break;
                }
                case "7" -> {
                    doctorList.getAllLoggedInDoctors
                    break;
                }
                case "8" -> {
                    appointmentQueue.viewUpcomingAppointments();
                    break;
                }
                case "9" -> {
                    appointmentQueue.processNextAppointment();
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
                    appointmentQueue.scheduleAppointment(newAppointment);
                    break;
                }
                case "11" -> {
                    System.out.println("Masukkan ID Pasien: ");
                    int idPasien = s.nextInt();
                    patientTree.searchPatient(id);
                    break;
                }
                case "12" -> {
                    patientTree.inOrderDisplay();
                    break;
                }
                case "0" -> {
                    System.out.println("Thanks for using Daisuke CLinic!");
                    System.out.println("Exiting.....");
                    break;
                }
                default -> {
                    System.out.println("Pilihan tidak valid");
                }
            }
        } while (!(choice.equals("0")));
    }

}