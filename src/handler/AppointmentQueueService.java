package handler;

import datastructure.LinkedList;
import datastructure.PriorityQueue;
import entity.Appointment;
import entity.Doctor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AppointmentQueueService {
    // scheduleAppointment(Appointment a) → enqueue
    public static void scheduleAppointment(Appointment appointment) {
        int doctorId = appointment.getDoctorId();
        Doctor nowDoctor = DoctorRecordService.findDoctorById(doctorId);
        PriorityQueue<Appointment> queue = nowDoctor.getAppointmentQueue();
        queue.enqueue(appointment); // gunakan insert milik PriorityQueue
        System.out.println("Appointment ID " + appointment.getAppointmentId() + " scheduled for Doctor ID " + doctorId);
    }

    // processNextAppointment() → dequeue
    public static Appointment processNextAppointment(int doctorId) {
        Doctor nowDoctor = DoctorRecordService.findDoctorById(doctorId);
        PriorityQueue<Appointment> queue = nowDoctor.getAppointmentQueue();
        if (queue.isEmpty()) {
            System.out.println("No appointments in queue to process.");
            return null;
        }
        Appointment processedAppointment = queue.dequeue();
        System.out.println("Processing appointment: ");
        processedAppointment.displayAppointmentInfo();
        return processedAppointment;
    }

    // viewUpcomingAppointments() → display queue
    public static void viewUpcomingAppointments(int doctorId) {
        Doctor nowDoctor = DoctorRecordService.findDoctorById(doctorId);
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
