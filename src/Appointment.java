public class Appointment {
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private String time; // Or String time;

    //constructor
    public Appointment(int appointmentId, int patientId, int doctorId, String time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.time = time;
    }

    // Getter methods
    public int getAppointmentId() { return appointmentId; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public String getTime() { return time; }

    @Override
    public String toString() {
        return "Appointment[AppID=" + appointmentId + ", PatientID=" + patientId +
                ", DoctorID=" + doctorId + ", Time=" + time + "]";
    }

    //scheduleAppointment(Appointment a) → enqueue

    //processNextAppointment() → dequeue

    //viewUpcomingAppointments() → display queue

}
