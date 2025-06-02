import java.util.Scanner;

//class patient record dengan fungsi tambah, hapus, cari, dan display
class PatientRecordService {
    private LinkedList<Patient> patientList;

    public PatientRecordService() {
        this.patientList = new LinkedList<>();
    }

    // menambah patient ke list
    public void addPatient(Patient patient) {
//        cek jika object berisi data
        if (patient == null) {
            System.err.println("Cannot add null patient.");
            return;
        }
        patientList.addLast(patient);
        System.out.println("Patient '" + patient.getName() + "' added to records.");
    }

    // menghapus patient dengan id
    public boolean removePatientById(int id) {
//        Cek jika ID valid
        if (id < 0) {
            System.out.println("Records are empty. Cannot remove.");
            return false;
        }
//        mencari object berdasarkan id
        Patient patientToRemove = null;
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getID() == id) {
                patientToRemove = patientList.get(i);
                break;
            }
        }
//        cek jika penghapusan data berhasil
        if (patientToRemove != null) {
            if (patientList.removeByValue(patientToRemove)) {
                System.out.println("Patient '" + patientToRemove.getName() + "' (ID: " + id + ") removed.");
                return true;
            } else {
                System.out.println("Failed to remove patient with ID " + id + " from list internals.");
                return false;
            }
        }
        System.out.println("Patient with ID " + id + " not found for removal.");
        return false;

    }

    // findPatientByName(String name)
    public Patient findPatientByName(String name) {
//        Cek jika nama tertulis
        if (name == null || name.trim().isEmpty()) {
            System.err.println("Name cannot be null or empty for search.");
            return null;
        }
//        Mencari object berdasarkan nama
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getName().equalsIgnoreCase(name)) {
                System.out.println("Found patient by name '" + name + "': " + patientList.get(i).toString());
                return patientList.get(i);
            }
        }
        System.out.println("Patient with name '" + name + "' not found.");
        return null;
    }

    // displayAllPatients()
    public void displayAllPatients() {
        if (patientList.isEmpty()) {
            System.out.println("No patients in records.");
            return;
        }

        System.out.println("\n--- All Patient Records (Linked List) ---");
        patientList.printForward();
        System.out.println("-----------------------------------------");
    }
}

class DoctorLoginService {
    private LinkedList<Doctor> loggedInDoctors;

    public DoctorLoginService() {
        this.loggedInDoctors = new LinkedList<>();
    }

    // loginDoctor(Doctor doctor) → add to list (front)
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
            System.err.println("Invalid Doctor ID for logout.");
            return false;
        }
//mencari object berdasarkan ID
        Doctor doctorToLogout = null;
        for (int i = 0; i < loggedInDoctors.size(); i++) {
            if (loggedInDoctors.get(i).getID() == doctorId) {
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
        System.out.println("\n--- Upcoming Appointments (Queue) ---");
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

    // insertPatient(Patient p)
    public void insertPatient(Patient patient) {
        if (patient == null) {
            System.err.println("Cannot insert null patient into BST.");
            return;
        }
        if (patient.getID() < 0) {
            System.err.println("Patient ID cannot be negative. Not inserting into BST.");
            return;
        }

        patientTree.add(patient.getID());
    }

    // searchPatient(int id) -> returns Patient object
    public Patient searchPatient(int id) {
        if (id < 0) {
            System.err.println("Cannot search for negative Patient ID in BST.");
            return null;
        }
//(PERLU REVISI)
        Patient foundPatient = patientTree.search(id);
        if (foundPatient != null) {
            System.out.println("Found patient ID " + id + " in BST: " + foundPatient);
        } else {
            System.out.println("Patient ID " + id + " not found in BST.");
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

        while (true){
            menuUtama();
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    // method untuk mendaftar pasien
//                    daftarPatient(scanner, patientManager);
                    break;
                case 2:
                    // Logika untuk menghapus pasien
                    System.out.println("Menghapus Pasien...");
                    // Tambahkan kode untuk menghapus pasien
                    break;
                case 3:
                    // method mencari id pasien di patientBST

                    break;
                case 4:
//                    patientManager.inOrderDisplay();
                    break;
                case 5:
                    // Logika untuk login dokter
                    System.out.println("Login Dokter...");
                    // Tambahkan kode untuk login dokter
                    break;
                case 6:
                    // Logika untuk logout dokter
                    System.out.println("Logout Dokter...");
                    // Tambahkan kode untuk logout dokter
                    break;
                case 7:
                    // Logika untuk daftar dokter
                    System.out.println("Daftar Dokter...");
                    // Tambahkan kode untuk daftar dokter
                    break;
                case 8:
                    // Logika untuk jadwal reservasi
                    System.out.println("Jadwal Reservasi...");
                    // Tambahkan kode untuk jadwal reservasi
                    break;
                case 9:
                    // Logika untuk proses reservasi
                    System.out.println("Proses Reservasi...");
                    // Tambahkan kode untuk proses reservasi
                    break;
                case 10:
                    // Logika untuk daftar reservasi
                    System.out.println("Daftar Reservasi...");
                    // Tambahkan kode untuk daftar reservasi
                    break;
                case 11:
                    System.out.println("Keluar dari sistem.");
                    scanner.close();
                    return; // Keluar dari program
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
            scanner.close();
        }
    }

//    private static void daftarPatient(Scanner scanner, Patient tree) {
//        System.out.println("Mendaftar Pasien...");
//
//        // inisialisasi data Patient
//        tree.addPatient();
//
//        // method memasukkan id pasien ke patientBST
//        if(tree.insertPatient(newPatient)) {
//            System.out.println("Pasien terdaftar");
//        } else {
//            System.err.println("Pasien gagal terdaftar");
//        }
//    }

    private static void menuUtama() {
        System.out.println("=== Sistem Rumah Sakit ===");
        System.out.println("1. Mendaftar Pasien");
        System.out.println("2. Hapus Pasien: ");
        System.out.println("3. Cari Pasien: ");//ini juga sama aja ga sih? ntar kita kasih menu lagi?
        System.out.println("4. Tampilkan semua Pasien"); //mending disini aja ntar dikasih pilihan ga si, daripada ada 2 menu yang sama, jadi di case kita ada scanner buat menu pake BST inorder sama yang ga
        System.out.println("5. login Dokter");
        System.out.println("6. logout Dokter: ");// cari ID
        System.out.println("7. Daftar Dokter: ");//pake stack?
        System.out.println("8. Jadwal reservasi: ");
        System.out.println("9. Proses reservasi: ");
        System.out.println("10. Daftar reservasi: ");
        System.out.println("11. Keluar");
        System.out.print("Pilih menu: ");
    }
}
