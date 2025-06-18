//kelompok 6
//L0124074_Rifqia Hani Milatunnisa
//L0124105_Muhammad Alfin Hasan
//L0124113_Musyaffa Falih Suryono

package main;

import entity.Admin;
import entity.Doctor;
import entity.Patient;
import handler.*;
import java.util.Scanner;
import system.AdminMenu;
import system.DoctorMenu;
import system.Helper;
import system.PatientMenu;


public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Doctor nowDoctor;
    private static Patient nowPatient;
    public static void main (String[]args){
        initializeSystem();

        mainRun();

        scanner.close();
    }

    public static void initializeSystem() {
        System.out.println("Initializing Daisuke Clinic System...");

        // Initialize all services
        PatientRecordService.makePatientRecord();
        PatientSearchTreeService.makePatientTree();
        DoctorRecordService.makeDoctorRecord();
        DoctorLoginService.makeDoctorLogin();
        AdminRecordService.makeAdminList();

        nowDoctor = null;
        nowPatient = null;

        // Pre-load patient data from file
        DoctorLoginService.getLoginList().clear();
        PatientRecordService.loadPatientTXT();
        DoctorRecordService.loadDoctorTXT();
        ScheduleRecord.loadScheduleTXT();
        AdminRecordService.loadAdminTXT();

        System.out.println("System ready. Welcome!");
    }

    public static void mainRun() {
        String choice;
        do {
            Helper.clearScreen();
            System.out.println("\n==== Welcome to Daisuke Clinic ====\n");
            System.out.println("Please enter what you want to do:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. End Program");
            System.out.print("Enter your choice: ");

            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    runLoginPortal();
                    break;
                case "2":
                    runRegisterProgram(scanner);
                    break;
                case "0":
                    Helper.exitProgram();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    Helper.pressEnter(scanner);
                    break;
            }
            Helper.pressEnter(scanner);
        } while (!choice.equals("0"));
    }

    public static void runLoginPortal() {
        String choice = "";
        do {
            Helper.clearScreen();
            System.out.println("\n==== Login Portal ====\n");
            int id = Helper.integerInput(scanner, "Input ID (0 to exit): ");

            if (id == 0) {
                break;
            }

            String idStr = String.valueOf(id);
            if (idStr.length() == 0) {
                System.out.println("Invalid ID format.");
                Helper.pressEnter(scanner);
                continue;
            }

            char firstChar = idStr.charAt(0);
            System.out.print("Input Password: ");
            String password = scanner.nextLine();
            
            switch (firstChar) {
                case '1': { // Doctor
                    Doctor loginDoctor = DoctorRecordService.findDoctorById(id);
                    if (loginDoctor != null && loginDoctor.getPassword().equals(password)) {
                        DoctorLoginService.loginDoctor(loginDoctor);
                        System.out.println("Login successful as Doctor " + loginDoctor.getName());
                        nowDoctor = loginDoctor;
                        DoctorMenu.run(scanner, loginDoctor);
                    } else {
                        System.out.println("Invalid Doctor ID or Password.");
                    }
                    break;
                }
                case '2': { // Patient
                    Patient loginPatient = PatientSearchTreeService.getPatientTree().containsID(id);
                    if (loginPatient != null && loginPatient.getPassword().equals(password)) {
                        System.out.println("Login successful as Patient " + loginPatient.getName());
                        nowPatient = loginPatient;
                        PatientMenu.run(scanner, loginPatient);
                    } else {
                        System.out.println("Invalid Patient ID or Password.");
                    }
                    break;
                }
                case '3': { // Admin
                    Admin loginAdmin = AdminRecordService.findAdminById(id);
                    if (loginAdmin != null && loginAdmin.getPassword().equals(password)) {
                        System.out.println("Login successful as Admin " + loginAdmin.getName());
                        AdminMenu.run(scanner);
                    } else {
                        System.out.println("Invalid Admin ID or Password.");
                    }
                    break;
                }
                default:
                    System.out.println("Invalid ID type. Must begin with 1 (Doctor), 2 (Patient), or 3 (Admin).");
                    Helper.pressEnter(scanner);
                    break;
            }
            Helper.pressEnter(scanner);
        } while (true);
    }

    public static void runRegisterProgram(Scanner scanner) {
        int roleChoice;
        do {
            Helper.clearScreen();
            System.out.println("\n==== Register New Account ====\n");
            System.out.println("1. Register as Doctor");
            System.out.println("2. Register as Patient");
            System.out.println("3. Register as Admin");
            System.out.println("0. Back to Previous Menu");

            roleChoice = Helper.integerInput(scanner, "\nChoose role (0-3): ");

            switch (roleChoice) {
                case 1 -> {
                    AdminMenu.doctorRegister(scanner);
                    Helper.pressEnter(scanner);
                }
                case 2 -> {
                    AdminMenu.addNewPatient(scanner); // pastikan ini menerima scanner
                    Helper.pressEnter(scanner);
                }
                case 3 -> {
                    int newAdminId = AdminRecordService.getNextAdminId();
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();

                    Admin newAdmin = new Admin(newAdminId, name, password);
                    AdminRecordService.addAdmin(newAdmin);

                    System.out.println("Admin registered successfully!");
                    System.out.println("Your Admin ID: " + newAdminId);
                    Helper.pressEnter(scanner);
                }
                case 0 -> {
                    System.out.println("Returning to previous menu...");
                    break; // keluar dari switch dan do-while
                }
                default -> {
                    System.out.println("Invalid role selected.");
                    Helper.pressEnter(scanner);
                }
            }
        } while (roleChoice != 0);
    }

}