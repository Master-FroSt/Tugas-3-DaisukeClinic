package system;

import datastructure.PriorityQueue;
import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import handler.AppointmentQueueService;
import handler.DoctorLoginService;
import handler.PatientSearchTreeService;
import handler.ScheduleRecord;
import java.util.Scanner;

public class DoctorMenu {
    private static Doctor currentDoctor;

    public static void run(Scanner scanner, Doctor nowDoctor) {
        currentDoctor = nowDoctor;
        String choice;
        do {
            showDoctorMenu();
            System.out.print("Select doctor menu: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1" -> viewUpcomingApp();
                case "2" -> processApp(scanner);
                case "3" -> currentDoctor.displayDoctorInfo();
                case "4" -> ScheduleRecord.printAllSchedule();
                case "0" -> logout();
                default -> System.out.println("Invalid choice. Please try again.");
            }
            if (!choice.equals("0")) {
                Helper.pressEnter(scanner);
            }
        } while (!choice.equals("0"));
    }

    private static void showDoctorMenu() {
        Helper.clearScreen();
        System.out.println("\n==== Doctor Portal ====\n");
        System.out.println("Logged in as: Dr. " + currentDoctor.getName() + " (ID: " + currentDoctor.getId() + ")\n");
        System.out.println("1. View Upcoming Appointments");
        System.out.println("2. Process Next Appointment");
        System.out.println("3. View Details");
        System.out.println("4. See Doctor Schedule");
        System.out.println("0. Logout");
    }

    private static void logout() {
        if (currentDoctor != null) {
            System.out.println("\n");
            DoctorLoginService.logoutDoctor(currentDoctor.getId());
            currentDoctor = null;
            System.out.println("You have been logged out. Returning to main portal...");
        }
    }

    private static void viewUpcomingApp() {
        Helper.clearScreen();
        System.out.println("\n======= Display Upcoming Appointments =======\n");
        AppointmentQueueService.viewUpcomingAppointments(currentDoctor.getId());
    }

    private static void processApp(Scanner scanner) {
        Helper.clearScreen();
        System.out.println("\n======= Process Appointment =======\n");
        PriorityQueue<Appointment> queue = currentDoctor.getAppointmentQueue();
        if (queue.isEmpty()) {
            System.out.println("Tidak ada appointment dalam antrian.");
            return;
        }

        Appointment next = queue.peek();
        System.out.println("\nAppointment Berikutnya:");
        next.displayAppointmentInfo();

        System.out.println("\n1. Layani Appointment");
        System.out.println("2. Kembali");
        int option = Helper.integerInput(scanner, "Pilih opsi: ");

        if (option == 1) {
            // Proses appointment
            Appointment servedAppointment = queue.dequeue();
            AppointmentQueueService.updateAppointmentTXT(currentDoctor.getId());
            AppointmentQueueService.loadAppointmentData(currentDoctor.getId());
            System.out.println("\nAppointment ID " + servedAppointment.getAppointmentId() + " sedang dilayani.");
            Patient patient = PatientSearchTreeService.getPatientTree().containsID(servedAppointment.getPatientId());

            System.out.println("Tambahkan data untuk pasien:");
            System.out.println("1. Symptom");
            System.out.println("2. Diagnosis");
            System.out.println("3. Medication");
            System.out.println("4. Notes");
            System.out.println("5. Selesai");

            while (true) {
                int choice = Helper.integerInput(scanner, "\nPilih input yang ingin ditambahkan (1-5): ");

                if (choice == 5) break;

                System.out.print("Isi: ");
                String value = scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        if (patient.getSymptom().size() <= 1) {
                            patient.getSymptom().set(0, value);
                        }
                        else {
                            patient.getSymptom().addLast(value);
                        }
                    }
                    case 2 -> {
                        if (patient.getDiagnosis().size() < 1) {
                            patient.getDiagnosis().set(0, value);
                        }
                        else {
                            patient.getDiagnosis().addLast(value);
                        }
                    }
                    case 3 -> {
                        if (patient.getMedication().size() <= 1) {
                            patient.getMedication().set(0, value);
                        }
                        else {
                            patient.getMedication().addLast(value);
                        }
                    }
                    case 4 -> patient.setNotes(value);
                    default -> System.out.println("Pilihan tidak valid.");
                }
            }

            System.out.println("Data pasien berhasil diperbarui.");
        } else {
            System.out.println("Kembali ke menu sebelumnya.");
        }
        AppointmentQueueService.loadAppointmentData(currentDoctor.getId());
    }
}