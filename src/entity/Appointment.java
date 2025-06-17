package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment implements Comparable<Appointment> {
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private LocalDateTime time;

    public Appointment(int appointmentId, int patientId, int doctorId, LocalDateTime time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.time = time;
    }

    // ==================================== Getter ====================================
    public int getAppointmentId() { return appointmentId; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public LocalDateTime getTime() { return time; }

    // ==================================== Setter ====================================
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public int compareTo(Appointment other) {
        // We can delegate the comparison directly to the LocalDateTime objects.
        return this.time.compareTo(other.time);
    }
    
    // ============================== Display appointment ==============================
    public void displayAppointmentInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedTime = time.format(formatter);
        System.out.println("ID appointment\t: " + appointmentId);
        System.out.println("ID pasien\t: " + patientId);
        System.out.println("ID Dokter\t: " + doctorId);
        System.out.println("Waktu\t\t: " + formattedTime);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedTime = time.format(formatter);
        return String.format("ID: %03d | Dr. %-20s | Specialty: %s%s", appointmentId, patientId, doctorId, formattedTime);
    }
}
