package code.main;

import code.system.Helper;
import code.system.PatientRecordService;
import code.datastructure.BST;
import code.datastructure.LinkedList;
import code.datastructure.Queue;
import code.entity.Appointment;
import code.entity.Doctor;
import code.entity.Patient;

import java.util.Scanner;

class DoctorLoginService {
    private LinkedList<Doctor> loggedInDoctors;

    public DoctorLoginService() {
        this.loggedInDoctors = new LinkedList<>();
    }

    // loginDoctor(code.entity.Doctor doctor) → add to list (front)
    public void loginDoctor(Doctor doctor) {
        if (doctor == null) {
            System.err.println("Cannot login null doctor.");
            return;
        }
//        [BELOM KEPIKIRAN]doctor.setLoginTime(LocalTime.now());
        loggedInDoctors.addFirst(doctor); // Assuming addFirst for LIFO behavior
        System.out.println("Dr. " + doctor.getName() + " logged in at " + doctor.getLoginTime());
    }

    // logoutDoctor(int doctorId) → remove by ID
    public boolean logoutDoctor(int doctorId) {
//        Cek jika ID valid
        if (doctorId < 0) {
            System.err.println("Invalid code.entity.Doctor ID for logout.");
            return false;
        }
//mencari object berdasarkan ID
        Doctor doctorToLogout = null;
        for (int i = 0; i < loggedInDoctors.size(); i++) {
            if (loggedInDoctors.get(i).getId() == doctorId) {
                doctorToLogout = loggedInDoctors.get(i);
                break;
            }
        }
//        Cek jika penghapusan berhasil
        if (doctorToLogout != null) {
            if (loggedInDoctors.removeByValue(doctorToLogout)) {
                System.out.println("Dr. " + doctorToLogout.getName() + " (ID: " + doctorId + ") logged out.");
                return true;
            } else {
                System.out.println("Failed to logout Dr. with ID " + doctorId + " from list internals.");
                return false;
            }
        }
        System.out.println("Logged in doctor with ID " + doctorId + " not found for logout.");
        return false;
    }

    // getAllLoggedInDoctors() → traverse and display
    public void getAllLoggedInDoctors() {
        if (loggedInDoctors.isEmpty()) {
            System.out.println("No doctors currently logged in.");
            return;
        }

        System.out.println("\n--- Currently Logged-In Doctors ---");
        loggedInDoctors.printForward();
        System.out.println("-----------------------------------");
    }
}

class AppointmentQueueService {
    private Queue<Appointment> appointmentQueue;

    public AppointmentQueueService() {
        this.appointmentQueue = new Queue<>(100);
    }


    // scheduleAppointment(code.entity.Appointment a) → enqueue
    public void scheduleAppointment(Appointment appointment) {
        if (appointment == null) {
            System.err.println("Cannot schedule null appointment.");
            return;
        }
        appointmentQueue.enqueue(appointment);
        System.out.println("code.entity.Appointment ID " + appointment.getAppointmentId() + " scheduled.");

    }

    // processNextAppointment() → dequeue
    public Appointment processNextAppointment() {
        if (appointmentQueue.isEmpty()) { // Delegate
            System.out.println("No appointments in queue to process.");
            return null;
        }
        Appointment processedAppointment = appointmentQueue.dequeue();
        System.out.println("Processing appointment: " + processedAppointment);
        return processedAppointment;

    }

    // viewUpcomingAppointments() → display queue
    public void viewUpcomingAppointments() {
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

class PatientSearchTreeService {
    private BST<Patient> patientTree;

    public PatientSearchTreeService() {
        this.patientTree = new BST();

    }

    // insertPatient(code.entity.Patient p)
    public void insertPatient(Patient patient) {
        if (patient == null) {
            System.err.println("Cannot insert null patient into code.datastructure.BST.");
            return;
        }
        if (patient.getID() < 0) {
            System.err.println("code.entity.Patient ID cannot be negative. Not inserting into code.datastructure.BST.");
            return;
        }

        patientTree.add(patient.getID());
    }

    // searchPatient(int id) -> returns code.entity.Patient object
    public Patient searchPatient(int id) {
        if (id < 0) {
            System.err.println("Cannot search for negative code.entity.Patient ID in code.datastructure.BST.");
            return null;
        }
//(PERLU REVISI)
        Patient foundPatient = patientTree.search(id);
        if (foundPatient != null) {
            System.out.println("Found patient ID " + id + " in code.datastructure.BST: " + foundPatient);
        } else {
            System.out.println("code.entity.Patient ID " + id + " not found in code.datastructure.BST.");
        }
        return foundPatient;
    }

    // inOrderDisplay()
    public void inOrderDisplay() {
        System.out.println("\n--- Patients In-Order (CustomBST by ID) ---");
        patientTree.inOrder();
        System.out.println("-------------------------------------------");

    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PatientRecordService patientRecord = new PatientRecordService();
        DoctorLoginService doctorList = new DoctorLoginService();
        AppointmentQueueService appointmentQueue = new AppointmentQueueService();
        PatientSearchTreeService patientTree = new PatientSearchTreeService();

        run(scanner);
    }


    private static void menuUtama() {
        System.out.println("\n==== Data Manager Pro ====");
        System.out.println("1. Add New Patient");
        System.out.println("2. Remove Patient by ID");
        System.out.println("3. Search Patient by Name");
        System.out.println("4. Display All Patient");
        System.out.println("5. Doctor Login");
        System.out.println("6. Doctor Logout");
        System.out.println("7. View Last Logged-in Doctor");
        System.out.println("8. Schedule Appointment");
        System.out.println("9. Process Appointment");
        System.out.println("10. Display Upcoming Appointments");
        System.out.println("11. Search Patient by ID (BST)");
        System.out.println("12. Display All Patients (BST Inorder)");
        // System.out.println("13. Register New Doctor");
        System.out.println("0. Exit");
        System.out.print("Option: ");
    }

    private static void run (Scanner s) {
        String choice;
        do {
            menuUtama();
            System.out.println("Pilih menu\t: ");
            choice = s.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.println("======= Add New Patient =======\n");
                    int newId = Helper.integerInput(s, "Masukkan ID Pasien: "); // input ID
                    System.out.print("Masukkan Nama Pasien: "); // input name
                    String newName = s.nextLine();
                    int newAge = Helper.integerInput(s, "Masukkan Umur Pasien: "); // input age
                    System.out.print("Masukkan Alamat Pasien: "); // input address
                    String newAddress = s.nextLine();
                    System.out.print("Masukkan Nomor Telepon Pasien (Contoh 0812-3456-7890): "); // input phone number
                    String newPhoneNumber = s.nextLine();

                    Patient newPatient = new Patient(newId, newName, newAge, newAddress, newPhoneNumber);
                    PatientRecordService.addPatient(newPatient);
                    break;
                }
                case "2" -> {
                    System.out.println("======= Remove Patient by ID =======\n");
                    int toRemoveID = Helper.integerInput(s, "Masukkan ID Patient: ");

                    PatientRecordService.removePatientById(toRemoveID);
                    break;
                }
                case "3" -> {
                    System.out.println("======= Search Patient by Name =======\n");
                    System.out.println("Masukkan nama Patient: ");
                    String patientName = s.nextLine();
                    PatientRecordService.findPatientByName(patientName);
                    break;
                }
                case "4" -> {
                    System.out.println("======= Display All Patient =======\n");
                    PatientRecordService.displayAllPatients();
                    break;
                }
                case "5" -> {
                    System.out.println("Masukkan ID Dokter: ");
                    int idDokter = s.nextInt();
                    System.out.println("Masukkan nama Dokter: ");
                    String namaDokter = s.nextLine();
                    System.out.println("Masukkan Spesialisasi Dokter: ");
                    String spesialisasi = s.nextLine();
                    System.out.println("Masukkan loginTime Dokter: ");
                    String loginTime = s.nextLine();
                    Doctor newDoctor = new Doctor(idDokter, namaDokter, spesialisasi, loginTime);
                    doctorList.loginDoctor(newDoctor);
                    break;
                    break;
                }
                case "6" -> {
                    System.out.println("Masukkan ID dokter");
                    doctorList.logoutDoctor(s.nextInt());
                    break;
                }
                case "7" -> {
                    doctorList.getAllLoggedInDoctors
                    break;
                }
                case "8" -> {
                    appointmentQueue.viewUpcomingAppointments();
                    break;
                }
                case "9" -> {
                    appointmentQueue.processNextAppointment();
                    break;
                }
                case "10" -> {
                    System.out.println("Masukkan ID code.entity.Appointment: ");
                    int idAppointment = s.nextInt();
                    System.out.println("Masukkan ID Pasien: ");
                    int idJadwalPatient = s.nextInt();
                    System.out.println("Masukkan ID Dokter: ");
                    int idJadwaldokter = s.nextInt();
                    System.out.println("Masukkan time: ");
                    String time = s.nextLine();
                    Appointment newAppointment = new Appointment(idAppointment,  idJadwalPatient, idJadwaldokter, time);
                    appointmentQueue.scheduleAppointment(newAppointment);
                    break;
                }
                case "11" -> {
                    System.out.println("Masukkan ID Pasien: ");
                    int idPasien = s.nextInt();
                    patientTree.searchPatient(id);
                    break;
                }
                case "12" -> {
                    patientTree.inOrderDisplay();
                    break;
                }
                case "0" -> {
                    System.out.println("Thanks for using Daisuke CLinic!");
                    System.out.println("Exiting.....");
                    break;
                }
                default -> {
                    System.out.println("Pilihan tidak valid");
                }
            }
        } while (!(choice.equals("0")));
    }
}
