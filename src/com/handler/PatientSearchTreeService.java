package com.handler;

import com.datastructure.BST;
import com.datastructure.LinkedList;
import com.entity.Patient;

public class PatientSearchTreeService {
    private static BST patientTree;
    private static int nextIdAdded;

    public static void makePatientTree() {
        patientTree = new BST();
        setNextIdAdded();
    }

    private static void setNextIdAdded() {
        if (patientTree.isEmpty()) {
            nextIdAdded = 1; // Start IDs from 1
        } else {
            nextIdAdded = patientTree.getMax() + 1;
        }
    }

    // insertPatient(code.entity.Patient p)
    public static void insertPatient(Patient patient) {
        if (patient == null) {
            System.out.println("Cannot insert null patient into code.datastructure.BST.");
            return;
        }
        if (patient.getID() < 0) {
            System.out.println("code.entity.Patient ID cannot be negative. Not inserting into code.datastructure.BST.");
            return;
        }

        patientTree.add(patient.getID());
        setNextIdAdded();
    }

    // Method to remove patient ID to tree
    public static void removePatient(Patient patient) {
        if (patient == null) {
            System.out.println("Cannot remove null patient from code.datastructure.BST.");
            return;
        }
        if (patient.getID() < 0) {
            System.out.println("code.entity.Patient ID cannot be negative. Not removing from code.datastructure.BST.");
            return;
        }

        patientTree.remove(patient.getID());
        setNextIdAdded();
    }

    public static int getNextIdAdd() {
        return nextIdAdded;
    }

    public static BST getPatientTree() {
        return patientTree;
    }
//searchPatient dipanggil oleh Patient Record untuk masukan LinkedList patient
    public static Patient searchPatient(int id, LinkedList<Patient> allPatients) {
        // check if the ID exists.
        if (!patientTree.contains(id)) {
            System.out.println("\nPatient with ID " + id + " not found in records (checked BST).");
            return null;
        }

        // find the full patient object
        for (int i = 0; i < allPatients.size(); i++) {
            Patient patient = allPatients.get(i);
            if (patient.getID() == id) {
                System.out.println("\nFound patient by ID " + id + ".");
                patient.displayPatientInfo();
                return patient;
            }
        }
        // If data mismatch or not found
        System.err.println("Data inconsistency: ID " + id + " found in BST but not in the patient list.");
        return null;
    }

     // inOrderDisplay()
     public static void inOrderDisplay() {
         System.out.println("\n--- Patients In-Order (CustomBST by ID) ---");
         patientTree.inOrder();
         System.out.println("-------------------------------------------");

     }
}