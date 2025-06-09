package com.handler;

import com.datastructure.BST;
import com.entity.Patient;

import java.util.Comparator;

public class PatientSearchTreeService {
    private static BST<Patient> patientTree;
    private static int nextIdAdded;

    public static void makePatientTree() {
        patientTree = new BST<>();
        setNextIdAdded();
    }

    private static void setNextIdAdded() {
        if (patientTree.isEmpty()) {
            nextIdAdded = 0;
        }
        else {
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

    public static BST<Patient> getPatientTree() {
        return patientTree;
    }

     // searchPatient(int id) -> returns code.entity.Patient object
     public static Patient searchPatient(int id) {
         if (id < 0) {
             System.err.println("Cannot search for negative code.entity.Patient ID in code.datastructure.BST.");
             return null;
         }
         Patient foundPatient = patientTree.search(id);
         if (foundPatient != null) {
             System.out.println("Found patient ID " + id + " in code.datastructure.BST: " + foundPatient);
         } else {
             System.out.println("code.entity.Patient ID " + id + " not found in code.datastructure.BST.");
         }
         return foundPatient;
     }

     // inOrderDisplay()
     public static void inOrderDisplay() {
         System.out.println("\n--- Patients In-Order (CustomBST by ID) ---");
         patientTree.inOrder();
         System.out.println("-------------------------------------------");

     }
}