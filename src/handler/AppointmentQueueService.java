package handler;

import datastructure.LinkedList;
import datastructure.PriorityQueue;
import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import system.Helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class AppointmentQueueService {
    // scheduleAppointment(Appointment a) → enqueue
    public static void scheduleAppointment(Appointment appointment) {
        int doctorId = appointment.getDoctorId();
        Doctor nowDoctor = DoctorRecordService.findDoctorById(doctorId);
        PriorityQueue<Appointment> queue = nowDoctor.getAppointmentQueue();

        queue.enqueue(appointment); // gunakan insert milik PriorityQueue
        updateAppointmentTXT(doctorId);
        loadAppointmentData(doctorId);
        System.out.println("Appointment ID " + appointment.getAppointmentId() + " scheduled for Doctor ID " + doctorId);
    }

    // processNextAppointment() → dequeue
    public static void processNextAppointment(Scanner scanner) {
        Helper.clearScreen();
        System.out.println("\n======= Process Appointment =======\n");

        // 1. Tampilkan daftar spesialisasi
        System.out.println("Daftar Spesialisasi yang Tersedia:");
        LinkedList<String> specialistList = DoctorRecordService.getSpecialistList();
        for (int i = 0; i < specialistList.size(); i++) {
            System.out.println((i + 1) + ". " + specialistList.get(i));
        }

        int selectedSpecialtyIndex;
        do {
            selectedSpecialtyIndex = Helper.integerInput(scanner, "Pilih nomor spesialisasi: ") - 1;
        } while (selectedSpecialtyIndex < 0 || selectedSpecialtyIndex >= specialistList.size());

        String chosenSpecialty = specialistList.get(selectedSpecialtyIndex);

        // 2. Filter dokter yang login dan sesuai spesialisasi
        LinkedList<Doctor> filteredDoctors = new LinkedList<>();
        System.out.println("\nDokter Login dengan Spesialisasi: " + chosenSpecialty);
        for (int i = 0; i < DoctorRecordService.getAllDoctors().size(); i++) {
            Doctor d = DoctorRecordService.getAllDoctors().get(i);
            if (d.getIsLogin() && d.getSpecialty().equalsIgnoreCase(chosenSpecialty)) {
                filteredDoctors.addLast(d);
                System.out.println("ID: " + d.getId() + " - " + d.getName());
            }
        }

        if (filteredDoctors.isEmpty()) {
            System.out.println("Tidak ada dokter login dalam spesialisasi ini.");
            return;
        }

        // 3. Pilih dokter dari daftar login
        int doctorId;
        Doctor selectedDoctor = null;
        boolean validDoctor = false;
        do {
            doctorId = Helper.integerInput(scanner, "Masukkan ID Dokter: ");
            for (int i = 0; i < filteredDoctors.size(); i++) {
                Doctor d = filteredDoctors.get(i);
                if (d.getId() == doctorId) {
                    selectedDoctor = d;
                    validDoctor = true;
                    break;
                }
            }
            if (!validDoctor) {
                System.out.println("ID dokter tidak valid atau dokter tidak sedang login.");
            }
        } while (!validDoctor);

        // 4. Cek antrian appointment
        PriorityQueue<Appointment> queue = selectedDoctor.getAppointmentQueue();
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
            AppointmentQueueService.updateAppointmentTXT(doctorId);
            AppointmentQueueService.loadAppointmentData(doctorId);
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
                        if (patient.getDiagnosis().size() <= 1) {
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
        AppointmentQueueService.loadAppointmentData(doctorId);
    }

    // viewUpcomingAppointments() → display queue
    public static void viewUpcomingAppointments(int doctorId) {
        Doctor nowDoctor = DoctorRecordService.findDoctorById(doctorId);
        loadAppointmentData(doctorId);
        PriorityQueue<Appointment> queue = nowDoctor.getAppointmentQueue();
        if (queue.isEmpty()) {
            System.out.println("No upcoming appointments in the queue.");
            return;
        }
        System.out.println("\n--- Upcoming Appointments (Priority Queue) ---");
        LinkedList<Appointment> sortedAppointments = queue.toSortedList();
        for (int i = 0; i < queue.size(); i++) {
            System.out.println(sortedAppointments.get(i));
        }
        System.out.println("------------------------------------------------");
    }

    // TXT
    public static void loadAppointmentData(int doctorId) {
        String filename = "src/data/appointment/" + doctorId + ".txt";

        if (DoctorRecordService.findDoctorById(doctorId).getAppointmentQueue() != null) {
            DoctorRecordService.findDoctorById(doctorId).getAppointmentQueue().clear();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstline = true;

            while ((line = br.readLine()) != null) {
                // Skip header
                if (isFirstline) {
                    isFirstline = false;
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length < 4) continue;

                int appointmentId = Integer.parseInt(parts[0].trim());
                int patientId = Integer.parseInt(parts[1].trim());
                int docId = Integer.parseInt(parts[2].trim());
                String timeString = parts[3].trim();
                LocalDateTime time = LocalDateTime.parse(timeString);

                Appointment newAppointment = new Appointment(appointmentId, patientId, docId, time);
                DoctorRecordService.findDoctorById(doctorId).getAppointmentQueue().enqueue(newAppointment);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void updateAppointmentTXT(int doctorId) {
        String filename = "src/data/appointment/" + doctorId + ".txt";
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write("Appointment ID | Patient ID | Doctor ID | Time\n");
            int size = DoctorRecordService.findDoctorById(doctorId).getAppointmentQueue().size();
            for (int i = 0; i < size; i++) {
                Appointment localAppointment = DoctorRecordService.findDoctorById(doctorId).getAppointmentQueue().dequeue();
                if (localAppointment != null) {
                    String time = localAppointment.getTime().toString();
                    myWriter.write(localAppointment.getAppointmentId() + " | " + localAppointment.getPatientId() + " | " + localAppointment.getDoctorId() + " | " + time + "\n");
                }
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}