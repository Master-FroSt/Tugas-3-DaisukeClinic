package com.handler;

import com.datastructure.Queue;
import com.entity.Appointment;

public class AppointmentQueueService {
    private static Queue<Appointment> appointmentQueue;

    public AppointmentQueueService() {
        this.appointmentQueue = new Queue<>(100);
    }


    // scheduleAppointment(code.entity.Appointment a) → enqueue
    public static void scheduleAppointment(Appointment appointment) {
        if (appointment == null) {
            System.err.println("Cannot schedule null appointment.");
            return;
        }
        appointmentQueue.enqueue(appointment);
        System.out.println("code.entity.Appointment ID " + appointment.getAppointmentId() + " scheduled.");

    }

    // processNextAppointment() → dequeue
    public static Appointment processNextAppointment() {
        if (appointmentQueue.isEmpty()) { // Delegate
            System.out.println("No appointments in queue to process.");
            return null;
        }
        Appointment processedAppointment = appointmentQueue.dequeue();
        System.out.println("Processing appointment: " + processedAppointment);
        return processedAppointment;

    }

    // viewUpcomingAppointments() → display queue
    public static void viewUpcomingAppointments() {
        if (appointmentQueue.isEmpty()) { // Delegate
            System.out.println("No upcoming appointments in the queue.");
            return;
        }
        System.out.println("\n--- Upcoming Appointments (code.datastructure.Queue) ---");
        appointmentQueue.getDisplay();
        System.out.println("-------------------------------------");
    }

    public boolean isQueueEmpty() { // Renamed for clarity
        return appointmentQueue.isEmpty(); // Delegate
    }
}
