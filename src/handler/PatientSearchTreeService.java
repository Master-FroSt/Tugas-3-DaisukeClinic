package handler;

import datastructure.BST;
import entity.Patient;

public class PatientSearchTreeService {
    private static BST patientTree;
    private static int nextIdAdded;

    public static void makePatientTree() {
        patientTree = new BST();
        setNextIdAdded();
    }

    private static void setNextIdAdded() {
        if (patientTree.isEmpty()) {
            nextIdAdded = 20001;
        }
        else {
            int currentMaxId = patientTree.getMax().getID();
            int localNumber = currentMaxId % 10000 + 1; // Naik satu dari bagian belakang
            int front = currentMaxId / 10000;           // Awalan '2', '21', dst.
            
            // Jika sudah penuh 9999, naik awalan-nya
            if (localNumber > 9999) {
                front += 1;
                localNumber = 0;
            }

            nextIdAdded = front * 10000 + localNumber;
        }
    }

    // insertPatient(code.entity.Patient p)
    public static void insertPatient(Patient patient) {
        if (patient == null) {
            System.out.println("Cannot insert null patient into datastructure.BST.");
            return;
        }
        if (patient.getID() < 0) {
            System.out.println("code.entity.Patient ID cannot be negative. Not inserting into datastructure.BST.");
            return;
        }

        patientTree.add(patient);
        setNextIdAdded();
    }

    // Method to remove patient ID to tree
    public static void removePatient(Patient patient) {
        if (patient == null) {
            System.out.println("Cannot remove null patient from datastructure.BST.");
            return;
        }
        if (patient.getID() < 0) {
            System.out.println("code.entity.Patient ID cannot be negative. Not removing from code.datastructure.BST.");
            return;
        }

        patientTree.remove(patient);
        setNextIdAdded();
    }

    public static int getNextIdAdd() {
        return nextIdAdded;
    }

    public static BST getPatientTree() {
        return patientTree;
    }

    // searchPatient by id, if found then patient details will be printed
    public static void searchPatient(int id) {
        Patient foundPatient = patientTree.containsID(id);
        // check if the ID exists.
        if (foundPatient == (null)) {
            System.out.println("\nPatient with ID " + id + " not found in records (checked BST).");
            return;
        }
        // print patient info
        foundPatient.displayPatientInfo();
    }

    // inOrderDisplay()
    public static void inOrderDisplay() {
        System.out.println("\n----------- Patients In-Order -----------");
        patientTree.inOrder();
        System.out.println("-------------------------------------------");
    }
}
