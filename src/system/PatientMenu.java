package system;

// import entity.Appointment;
import entity.Doctor;
import entity.Patient;
// import handler.AppointmentQueueService;
import handler.DoctorLoginService;
import handler.DoctorRecordService;
import handler.PatientRecordService;
import java.util.Scanner;

/**
 * This class handles the menu and actions available to a Patient.
 */
public class PatientMenu {

    public static void run(Scanner scanner) {
        String choice;
        do {
            showPatientMenu();
            System.out.print("Select patient menu: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1" -> searchPatientSelf(scanner);
                case "2" -> viewLoggedInDoctors();
                case "3" -> scheduleAppointment(scanner);
                case "0" -> System.out.println("\nReturning to main login portal...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
            if (!choice.equals("0")) {
                Helper.pressEnter(scanner);
            }
        } while (!choice.equals("0"));
    }

    private static void showPatientMenu() {
        System.out.println("\n==== Patient Portal ====");
        System.out.println("1. View My Patient Record");
        System.out.println("2. View Available Doctors");
        System.out.println("3. Schedule an Appointment");
        System.out.println("0. Back to Main Portal");
    }

    private static void searchPatientSelf(Scanner scanner) {
        System.out.println("\n--- View My Patient Record ---");
        int id = Helper.integerInput(scanner, "Enter your Patient ID: ");
        PatientRecordService.findPatientById(id);
    }

    private static void viewLoggedInDoctors() {
        System.out.println("\n--- Available Doctors ---");
        DoctorLoginService.getAllLoggedInDoctors();
    }

    private static void scheduleAppointment(Scanner scanner) {
        System.out.println("\n--- Schedule an Appointment ---");
        int patientId = Helper.integerInput(scanner, "Enter your Patient ID: ");
        Patient patient = PatientRecordService.findPatientById(patientId);

        if (patient == null) {
            System.out.println("Could not find a patient with that ID.");
            return;
        }

        System.out.println("\nThese doctors are currently logged in:");
        DoctorLoginService.getAllLoggedInDoctors();
        int doctorId = Helper.integerInput(scanner, "\nEnter the ID of the doctor you wish to see: ");
        Doctor doctor = DoctorRecordService.findDoctorById(doctorId);

        if (doctor == null) {
            System.out.println("Could not find a doctor with that ID.");
            return;
        }

//         Appointment newAppointment = new Appointment(patient, doctor);
//         AppointmentQueueService.scheduleAppointment(newAppointment);
        System.out.println("Feature not implemented yet.");
    }
}