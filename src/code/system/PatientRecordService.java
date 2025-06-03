package code.system;

import code.datastructure.LinkedList;
import code.entity.Patient;

//class patient record dengan fungsi tambah, hapus, cari, dan display
public class PatientRecordService {
    private static LinkedList<Patient> patientList;

    public PatientRecordService() {
        this.patientList = new LinkedList<>();
    }

    // menambah patient ke list
    public static void addPatient(Patient patient) {
        // cek jika object berisi data
        if (patient == null) {
            System.err.println("Cannot add null patient.");
            return;
        }
        if (patientList.contains(patient)) {
            System.out.println("Patient already added previously, you cannot added it again!");
            return;
        }
        patientList.addLast(patient);
        System.out.println("Patient '" + patient.getName() + " added to records.");
    }

    // menghapus patient dengan id
    public static boolean removePatientById(int id) {
        // Cek jika ID valid
        if (id < 0) {
            System.out.println("Records are empty. Cannot remove.");
            return false;
        }
        // mencari object berdasarkan id
        Patient patientToRemove = null;
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getID() == id) {
                patientToRemove = patientList.get(i);
                break;
            }
        }
        // cek jika penghapusan data berhasil
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
    public static Patient findPatientByName(String name) {
        // Cek jika nama tertulis
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be null or empty for search.");
            return null;
        }
        // Mencari object berdasarkan nama
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
    public static void displayAllPatients() {
        if (patientList.isEmpty()) {
            System.out.println("No patients in records.");
            return;
        }

        System.out.println("\n--- All Patient Records (Linked List) ---");
        patientList.printForward();
        System.out.println("-----------------------------------------");

        System.out.printf("%-10s %-15s %-5s %-15s %-15s", "ID", "Name", "Age", "Address", "Phone Number");
        for (int i = 0; i < patientList.size(); i++) {
            System.out.printf("%-10d %-15s %-5d %-15s %-15s", patientList.get(i).getID(),
                    patientList.get(i).getName(),
                    patientList.get(i).getAge(),
                    patientList.get(i).getAddress(),
                    patientList.get(i).getPhoneNumber());
        }
    }
}
