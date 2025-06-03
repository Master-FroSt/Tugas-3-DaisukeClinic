package com.handler;

import com.datastructure.BST;
import com.entity.Patient;

public class PatientSearchTreeService {
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
    public static Patient searchPatient(int id) {
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
    public static void inOrderDisplay() {
        System.out.println("\n--- Patients In-Order (CustomBST by ID) ---");
        patientTree.inOrder();
        System.out.println("-------------------------------------------");

    }
}