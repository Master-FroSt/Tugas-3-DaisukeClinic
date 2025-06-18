package handler;

import datastructure.LinkedList;
import entity.Patient;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//class patient record dengan fungsi tambah, hapus, cari, dan display
public class PatientRecordService {
    private static LinkedList<Patient> patientList;

    public static void makePatientRecord() {
        patientList = new LinkedList<>();
    }

    public static LinkedList<Patient> getPatientList() {
        return patientList;
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
        PatientSearchTreeService.insertPatient(patient);
        System.out.printf("\nID\t\t: %d\n", patient.getID());
        System.out.println("Name\t\t: " + patient.getName());
        System.out.println("Age\t\t: " + patient.getAge());
        System.out.println("Address\t\t: " + patient.getAddress());
        System.out.println("Phone Number\t: " + patient.getPhoneNumber());
        System.out.println("Patient with the data above added to records.");
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
                PatientSearchTreeService.removePatient(patientToRemove);
                System.out.printf("Patient '%s' (ID: %d) removed.\n", patientToRemove.getName(), id);
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
    public static void findPatientByName(String name) {
        // Cek jika nama tertulis
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be null or empty for search.");
            return;
        }
        // Mencari object berdasarkan nama
        LinkedList<Patient> foundPatient = new LinkedList<>();
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getName().toLowerCase().contains(name.toLowerCase())) {
                Patient patient = patientList.get(i);
                foundPatient.addFirst(patient);
                System.out.println("Found patient by name '" + patient.getName() + "' is found");
            }
        }
        if (!foundPatient.isEmpty()) {
            System.out.println("Patients found:");
            for (int n = 0; n < foundPatient.size(); n++) {
                foundPatient.get(n).displayPatientInfo();
                System.out.println();
            }
        }
        else {
            System.out.println("Patient with name '" + name + "' not found.");
        }
    }


    // displayAllPatients()
    public static void displayAllPatients() {
        if (patientList.isEmpty()) {
            System.out.println("No patients in records.");
            return;
        }

        System.out.println("\n------ All Patient Records (Linked List) ------");
        System.out.println("-----------------------------------------");

        System.out.printf("%-10s %-25s %-5s %-35s %-15s\n", "ID", "Name", "Age", "Address", "Phone Number");
        for (int i = 0; i < patientList.size(); i++) {
            System.out.printf("%-10d %-25s %-5d %-35s %-15s\n", patientList.get(i).getID(),
                    patientList.get(i).getName(),
                    patientList.get(i).getAge(),
                    patientList.get(i).getAddress(),
                    patientList.get(i).getPhoneNumber());
        }
    }

    public static void loadPatientTXT() {
        if (patientList == null) {
            makePatientRecord();
        } else {
            patientList.clear();
        }
        PatientSearchTreeService.getPatientTree().clear();

        try (BufferedReader br = new BufferedReader(new FileReader("src/data/patient.txt"))) {
            String line;
            boolean isFirstline = true;

            while ((line = br.readLine()) != null) {
                // Skip header
                if (isFirstline) {
                    isFirstline = false;
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length < 10) continue;

                int ID = Integer.parseInt(parts[0].trim());
                String pass = parts[1].trim();
                String Name = parts[2].trim();
                int age = Integer.parseInt(parts[3].trim());
                String address = parts[4].trim();
                String number = parts[5].trim();

                Patient newPatient = new Patient(ID, pass, Name, age, address, number);
                addPatient(newPatient);

                // Set symptom
                String[] tempSymptom = parts[6].trim().split("\\,");
                if (tempSymptom.length < 1) continue;
                LinkedList<String> symptom = new LinkedList<>();
                for (String s : tempSymptom) {
                    symptom.addLast(s.trim());
                }
                newPatient.setSymptom(symptom);

                // Set diagnosis
                String[] tempDiagnosis = parts[7].trim().split("\\,");
                if (tempDiagnosis.length < 1) continue;
                LinkedList<String> diagnosis = new LinkedList<>();
                for (String s : tempDiagnosis) {
                    diagnosis.addLast(s.trim());
                }
                newPatient.setDiagnosis(diagnosis);

                // Set Medication
                String[] tempMedication = parts[8].trim().split("\\,");
                if (tempMedication.length < 1) continue;
                LinkedList<String> medication = new LinkedList<>();
                for (String s : tempMedication) {
                    medication.addLast(s.trim());
                }
                newPatient.setMedication(medication);

                // Set Notes
                String notes = parts[9].trim();
                newPatient.setNotes(notes);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void updatePatientTXT() {
        try {
            FileWriter myWriter = new FileWriter("src/data/patient.txt");
            myWriter.write("Patient ID | Password | Name | Age | Address | Phone Number | Symptom | Diagnosis | Medication | Notes\n");
            for (int i = 0; i < patientList.size(); i++) {
                Patient localPatient = patientList.get(i);
                if (localPatient != null) {
                    String localSymptom = localPatient.getSymptom().toString();
                    String localDiagnosis = localPatient.getDiagnosis().toString();
                    String localMedication = localPatient.getMedication().toString();
                    myWriter.write(localPatient.getID() + " | " + localPatient.getPassword() + " | " + localPatient.getName() + " | " + localPatient.getAge() + " | " + localPatient.getAddress() + " | " + localPatient.getPhoneNumber() + " | " + localSymptom + " | " + localDiagnosis + " | " + localMedication + " | " + localPatient.getNotes() + "\n");
                }
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}