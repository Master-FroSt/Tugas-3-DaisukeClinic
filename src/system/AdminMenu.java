package system;

import datastructure.LinkedList;
import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import handler.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class AdminMenu {
    // Scanner tidak perlu dideklarasi baru, gunakan yang di-pass dari Main
    private static Scanner scanner;

    public static void run(Scanner s){
        scanner = s;
        if (!login(scanner)) {
            System.out.println("\nAdmin login failed. Returning to main portal.");
            return;
        }

        System.out.println("\nAdmin login successful!");
        String choice;
        do {
            showAdminMenu();
            System.out.print("Pilih menu\t: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    addNewPatient();
                    PatientRecordService.updatePatientTXT();
                    break;
                }
                case "2" -> {
                    removePatientId();
                    PatientRecordService.updatePatientTXT();
                    break;
                }
                case "3" -> {
                    searchPatientName();
                    break;
                }
                case "4" -> {
                    displayPatientList();
                    break;
                }
                case "5" -> {
                    doctorLogin();
                    DoctorRecordService.updateDoctorTXT();
                }
                case "6" -> {
                    doctorLogout();
                    DoctorRecordService.updateDoctorTXT();
                }
                case "7" -> viewLoggedInDoctors();
                case "8" -> scheduleAppointment();
                case "9" -> processAppointments();
                case "10" -> displayAppointments();
                case "11" -> searchPatientById();
                case "12" -> displayPatientsInOrder();
                case "13" -> {
                    doctorRegister();
                    DoctorRecordService.updateDoctorTXT();
                }
                case "0" -> {
                    System.out.println("Thanks for using Daisuke Clinic!");
                    System.out.println("Exiting.....");
                }
                default -> System.out.println("Invalid Input!");
            }
            if (!choice.equals("0")) {
                Helper.pressEnter(scanner);
            }
        } while (!choice.equals("0"));
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
        System.out.println("13. Register New Doctor");
        System.out.println("0. Exit");
    }

    // =========================== funtion 1 ===========================
    private static void addNewPatient() {
        System.out.println("======= Add New Patient =======\n");
        int newId = PatientSearchTreeService.getNextIdAdd(); // input ID (otomatis)
        System.out.print("Masukkan Password Pasien: "); // input password
        String newPass = scanner.nextLine();
        
        String newName; // input nama
        do { 
            System.out.print("Masukkan Nama Pasien (maksimal 25 karakter): ");
            newName = scanner.nextLine().trim();
            if (newName.length() > 25) {
                System.out.println("Nama terlalu panjang. Maksimal 25 karakter.");
            }
        } while (newName.length() > 25);
        
        int newAge = Helper.integerInput(scanner, "Masukkan Umur Pasien: "); // input age

        System.out.println("Masukkan Alamat Pasien: "); // input address
        System.out.print("\t>> Provinsi: ");
        String provinsi = scanner.nextLine();
        System.out.print("\t>> Kab/Kota: ");
        String kota = scanner.nextLine();
        System.out.print("\t>> Kecamatan: ");
        String kec = scanner.nextLine();
        String newAddress = provinsi + ", " + kota + ", " + kec;

        System.out.print("Masukkan Nomor Telepon Pasien (angka saja) (Contoh 081234567890): "); // input phone number
        String input = scanner.nextLine().trim();
        // Validasi hanya digit
        while (!input.matches("\\d+")) {
            System.out.print("Input tidak valid. Masukkan hanya angka: ");
            input = scanner.nextLine().trim();
        }
        // Format: pisahkan setiap 4 digit
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            formatted.append(input.charAt(i));
            if ((i + 1) % 4 == 0 && i != input.length() - 1) {
                formatted.append("-");
            }
        }
        String newPhoneNumber = formatted.toString();
        
        Patient newPatient = new Patient(newId, newPass, newName, newAge, newAddress, newPhoneNumber);
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
        System.out.print("Masukkan nama Patient: ");
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
    static void viewLoggedInDoctors() {
        System.out.println("\n======= View Logged-in Doctors =======");
        DoctorLoginService.getAllLoggedInDoctors();
    }

    // =========================== funtion 8, 9, 10 (optional, uncomment if needed) ===========================

    static void scheduleAppointment() {
        System.out.println("\n======= Schedule Appointment =======");
//         mencari ID pasien
        boolean isPatientID = false;
        int idPatient;
        Patient check;
        do {
            idPatient = Helper.integerInput(scanner, "Masukkan ID Patient: ");
            check = PatientSearchTreeService.getPatientTree().containsID(idPatient);
            if(!PatientRecordService.getPatientList().contains(check)) {
                continue;
            }
            isPatientID = true;
        } while(!isPatientID);
         // doctor
        int idDoctor;
        Doctor localDoctor;
        boolean isDoctorID = false;
        do {
            System.out.println("Available Doctors:");
            DoctorLoginService.getAllLoggedInDoctors();
            idDoctor = Helper.integerInput(scanner, "Masukkan ID Doctor: ");
            for (int i = 0; i <  DoctorRecordService.getAllDoctors().size(); i++) {
                if (DoctorRecordService.getAllDoctors().get(i).getId() == idDoctor) {
                    isDoctorID = true;
                    localDoctor = DoctorRecordService.getAllDoctors().get(i);
                } else {
                    continue;
                }
            }
        } while (!isDoctorID);
        idPatient = Helper.integerInput(scanner, "Masukkan ID Patient: ");
        LocalDateTime base = LocalDateTime.of(2025, 6, 1, 0, 0);
        LocalDateTime now = LocalDateTime.now();
        int idAppointment = (int) ChronoUnit.SECONDS.between(base, now);
//        Belum ada penentuan waktu appointment
        LocalDateTime timeAppointment = LocalDateTime.now();

        Appointment newAppointment = new Appointment(idAppointment, idPatient, idDoctor, timeAppointment);
        AppointmentQueueService.scheduleAppointment(newAppointment);
//        Belom ada cara bikin queue perdokter
        AppointmentQueueService queueApp = new AppointmentQueueService();
//        localDoctor.setQueueApp(queueApp);
    }

    static void processAppointments() {
        System.out.println("\n======= Process Appointment =======");
        AppointmentQueueService.processNextAppointment();
    }

    static void displayAppointments() {
        System.out.println("\n======= Display Upcoming Appointments =======");
        AppointmentQueueService.viewUpcomingAppointments();
    }

    // =========================== funtion 11 ===========================
    private static void searchPatientById() {
        System.out.println("\n======= Search Patient by ID =======");
        int idPasien = Helper.integerInput(scanner, "Enter Patient ID to search: ");
        PatientSearchTreeService.searchPatient(idPasien);
    }

    // =========================== funtion 12 ===========================
    private static void displayPatientsInOrder() {
        System.out.println("\n======= Display All Patients (Sorted by ID) =======");
        PatientSearchTreeService.inOrderDisplay();
    }

    // =========================== funtion 13 ===========================
    public static void doctorRegister() {
        System.out.println("======= Register New Doctor =======\n");
        System.out.print("Enter Doctor's Name : "); // input name
        String newName = scanner.nextLine();
        System.out.print("Enter new password : "); // input address
        String password = scanner.nextLine();

        boolean isGetSpecialist = false;
        String specialist = "";
        int newId = 0;
        int choosenOption;
        do {
            System.out.println("\nChoose doctor specialist");
            for (int i = 0; i < DoctorRecordService.getSpecialistList().size(); i++) {
                System.out.println("( " + (i + 1) + " ) " + DoctorRecordService.getSpecialistList().get(i));
            }
            System.out.println("( " + (DoctorRecordService.getSpecialistList().size() + 1) + " ) Add new specialist");
            choosenOption = Helper.integerInput(scanner, "\nOption choosen (1 - " + (DoctorRecordService.getSpecialistList().size() + 1) + " ) : ");
            if (choosenOption < 1 || choosenOption > (DoctorRecordService.getSpecialistList().size() + 1)) {
                System.out.println("Input invalid!!");
            }
            // spesialis baru
            else if (choosenOption == (DoctorRecordService.getSpecialistList().size() + 1)) {
                System.out.println("Input new specialist : ");
                String newSpecialist = scanner.nextLine();
                DoctorRecordService.getSpecialistList().addLast(newSpecialist);
                DoctorRecordService.getSpecialistCount().addLast(1);
                specialist = newSpecialist;
                String formattedId = String.format("1%02d%02d", (DoctorRecordService.getSpecialistList().size()), 1);
                newId = Integer.parseInt(formattedId);
                isGetSpecialist = true;
            }
            else {
                if (DoctorRecordService.getSpecialistCount().get(choosenOption - 1) > 99) {
                    System.out.println("Doctor in this specialist is already full!");
                }
                else {
                    DoctorRecordService.getSpecialistCount().set(choosenOption - 1, DoctorRecordService.getSpecialistCount().get(choosenOption - 1) + 1);
                    specialist = DoctorRecordService.getSpecialistList().get(choosenOption- 1);
                    int specialistIndex = choosenOption - 1;
                    int doctorNumber = DoctorRecordService.getSpecialistCount().get(specialistIndex);
                    String formattedId = String.format("1%02d%02d", specialistIndex + 1, doctorNumber);
                    newId = Integer.parseInt(formattedId);
                    isGetSpecialist = true;
                }
            }
        } while (!isGetSpecialist);

        Doctor newDoctor = new Doctor(newId, newName, specialist, password);
        DoctorRecordService.registerDoctor(newDoctor);
    }
}
