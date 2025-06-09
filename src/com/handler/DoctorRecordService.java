package com.handler;

import com.datastructure.LinkedList;
import com.entity.Doctor;

public class DoctorRecordService {

    private static final LinkedList<Doctor> allDoctors = new LinkedList<>();

    private DoctorRecordService() {} // Private constructor for utility class
    public static void registerDoctor(Doctor doctor) {
        if (doctor == null) {
            System.err.println("Cannot register a null doctor.");
            return;
        }

        // Check if a doctor with the same ID already exists
        for (int i = 0; i < allDoctors.size(); i++) {
            if (allDoctors.get(i).getId() == doctor.getId()) {
                System.err.println("Error: A doctor with ID " + doctor.getId() + " is already registered.");
                return;
            }
        }

        allDoctors.addLast(doctor);
        System.out.println("\nRegistration successful!");
        doctor.displayDoctorInfo();
    }

    public static Doctor findDoctorById(int doctorId) {
        for (int i = 0; i < allDoctors.size(); i++) {
            if (allDoctors.get(i).getId() == doctorId) {
                return allDoctors.get(i);
            }
        }
        return null; // Return null if not found
    }

    public static Doctor authenticateDoctor(int doctorId, String password) {
        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null && doctor.getPassword().equals(password)) {
            return doctor; // Authentication successful
        }
        return null; // Authentication failed
    }

    public static LinkedList<Doctor> getAllDoctors() {
        return allDoctors;
    }
}
