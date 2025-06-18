package system;

import datastructure.LinkedList;
import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import entity.Schedule;
import handler.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

/**
 * This class handles the menu and actions available to a Patient.
 */
public class PatientMenu {
    private static Patient currentPatient;

    public static void run(Scanner scanner, Patient nowPatient) {
        String choice;
        do {
            currentPatient = nowPatient;
            showPatientMenu();
            System.out.print("Select patient menu: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1" -> searchPatientSelf(scanner);
                case "2" -> scheduleApp(scanner);
                case "3" -> ScheduleRecord.printAllSchedule();
                case "0" -> System.out.println("\nReturning to main login portal...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
            if (!choice.equals("0")) {
                Helper.pressEnter(scanner);
            }
        } while (!choice.equals("0"));
    }

    private static void showPatientMenu() {
        Helper.clearScreen();
        System.out.println("\n==== Patient Portal ====\n");
        System.out.println("1. View My Patient Record");
        System.out.println("2. Schedule an Appointment");
        System.out.println("3. See All Doctor Schedule");
        System.out.println("0. Back to Main Portal");
    }

    private static void searchPatientSelf(Scanner scanner) {
        Helper.clearScreen();
        System.out.println("\n--- View My Patient Record ---\n");
        PatientSearchTreeService.searchPatient(currentPatient.getID());
    }

    private static void scheduleApp(Scanner scanner) {
        Helper.clearScreen();
        System.out.println("\n======= Schedule Appointment =======\n");

        // 1. Pilih spesialisasi
        System.out.println("Daftar Spesialisasi yang Tersedia:");
        for (int i = 0; i < DoctorRecordService.getSpecialistList().size(); i++) {
            System.out.println((i + 1) + ". " + DoctorRecordService.getSpecialistList().get(i));
        }

        int selectedSpecialtyIndex;
        do {
            selectedSpecialtyIndex = Helper.integerInput(scanner, "Pilih nomor spesialisasi: ") - 1;
        } while (selectedSpecialtyIndex < 0 || selectedSpecialtyIndex >= DoctorRecordService.getSpecialistList().size());

        String chosenSpecialty = DoctorRecordService.getSpecialistList().get(selectedSpecialtyIndex);

        // 2. Filter dan tampilkan dokter sesuai spesialisasi
        System.out.println("\nDaftar Dokter dengan Spesialisasi: " + chosenSpecialty);
        LinkedList<Doctor> filteredDoctors = new LinkedList<>();
        for (int i = 0; i < DoctorRecordService.getAllDoctors().size(); i++) {
            Doctor d = DoctorRecordService.getAllDoctors().get(i);
            if (d.getSpecialty().equalsIgnoreCase(chosenSpecialty)) {
                filteredDoctors.addLast(d);
                System.out.println("ID: " + d.getId() + " - " + d.getName());
            }
        }

        if (filteredDoctors.isEmpty()) {
            System.out.println("Tidak ada dokter untuk spesialisasi ini.");
            return;
        }

        // 3. Pilih ID dokter dari daftar yang difilter
        int idDoctor;
        Doctor selectedDoctor = null;
        boolean isDoctorID = false;
        do {
            idDoctor = Helper.integerInput(scanner, "Masukkan ID Dokter: ");
            for (int i = 0; i < filteredDoctors.size(); i++) {
                if (filteredDoctors.get(i).getId() == idDoctor) {
                    selectedDoctor = filteredDoctors.get(i);
                    isDoctorID = true;
                    break;
                }
            }
            if (!isDoctorID) {
                System.out.println("ID dokter tidak valid.");
            }
        } while (!isDoctorID);

        // 4. Generate ID janji temu dari waktu sekarang
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime base = LocalDateTime.of(2025, 6, 1, 0, 0);
        int idAppointment = (int) ChronoUnit.SECONDS.between(base, now);

        // 5. Tampilkan jadwal dokter
        System.out.println("\nJadwal Dokter:");
        LinkedList<Schedule> schedules = ScheduleRecord.getScheduleListByDoctor(idDoctor);
        for (int i = 0; i < schedules.size(); i++) {
            Schedule s = schedules.get(i);
            String namaHari = ScheduleRecord.getDayName(s.getDay()); // s.getDay() mengembalikan int
            System.out.println("- Hari " + namaHari + " dari " + s.getBeginTime() + " sampai " + s.getFinishTime());
        }

        // 7. Input tanggal dan waktu appointment
        LocalDate appointmentDate;
        LocalTime appointmentTime;
        LocalDateTime appointmentDateTime = null;
        boolean validTime = false;

        do {
            System.out.print("Masukkan tanggal appointment (format: yyyy-MM-dd): ");
            String inputDate = scanner.nextLine();

            System.out.print("Masukkan waktu appointment (format: HH:mm): ");
            String inputTime = scanner.nextLine();
            try {
                appointmentDate = LocalDate.parse(inputDate);
                appointmentTime = LocalTime.parse(inputTime);
                appointmentDateTime = LocalDateTime.of(appointmentDate, appointmentTime);

                if (appointmentDateTime.isAfter(LocalDateTime.now().plusMonths(1))) {
                    System.out.println("Appointment tidak bisa lebih dari 1 bulan dari sekarang. Coba lagi.");
                    continue;
                }

                if (appointmentDateTime.isBefore(LocalDateTime.now())) {
                    System.out.println("Waktu appointment tidak boleh di masa lalu. Coba lagi.");
                    continue;
                }

                int dayOfWeek = appointmentDate.getDayOfWeek().getValue(); // 1=Senin, 7=Minggu

                // 8. Validasi apakah waktu ada di dalam salah satu schedule
                for (int i = 0; i < schedules.size(); i++) {
                    Schedule s = schedules.get(i);
                    if (s.getDay() == dayOfWeek &&
                        !appointmentTime.isBefore(s.getBeginTime()) &&
                        !appointmentTime.isAfter(s.getFinishTime())) {
                        validTime = true;
                        break;
                    }
                }

                if (!validTime) {
                    System.out.println("Waktu tidak sesuai dengan jadwal dokter. Coba lagi.");
                }
            } catch (Exception e) {
                System.out.println("Format input tidak valid. Ulangi.");
            }

        } while (!validTime);

        AppointmentQueueService.loadAppointmentData(idDoctor);
        // 8. Buat dan jadwalkan janji temu
        Appointment appointment = new Appointment(idAppointment, currentPatient.getID(), idDoctor, appointmentDateTime);
        System.out.println();
        AppointmentQueueService.scheduleAppointment(appointment);
    }
}