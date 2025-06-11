package handler;

import datastructure.Queue;
import entity.Appointment;

public class AppointmentQueueService {
    // Make the queue instance non-static to avoid shared state issues
    private final Queue<Appointment> appointmentQueue;

    public AppointmentQueueService() {
        appointmentQueue = new Queue<Appointment>(100);
    }

    // scheduleAppointment(Appointment a) → enqueue
    public void scheduleAppointment(Appointment appointment) {
        if (appointment == null) {
            System.err.println("Cannot schedule null appointment.");
            return;
        }
        appointmentQueue.enqueue(appointment);
        System.out.println("Appointment ID " + appointment.getAppointmentId() + " scheduled.");
    }

    // processNextAppointment() → dequeue
    public Appointment processNextAppointment() {
        if (appointmentQueue.isEmpty()) {
            System.out.println("No appointments in queue to process.");
            return null;
        }
        Appointment processedAppointment = appointmentQueue.dequeue();
        System.out.println("Processing appointment: " + processedAppointment);
        return processedAppointment;
    }

    // viewUpcomingAppointments() → display queue
    public void viewUpcomingAppointments() {
        if (appointmentQueue.isEmpty()) {
            System.out.println("No upcoming appointments in the queue.");
            return;
        }
        System.out.println("\n--- Upcoming Appointments (Queue) ---");
        // Assuming Queue has a toString() or similar method, otherwise iterate and print
        System.out.println(appointmentQueue);
        System.out.println("-------------------------------------");
    }

    public boolean isQueueEmpty() {
        return appointmentQueue.isEmpty();
    }
}