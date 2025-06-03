package com.handler;

import com.datastructure.LinkedList;
import com.entity.Doctor;

import java.time.LocalTime;

public class DoctorLoginService {
    private LinkedList<Doctor> loggedInDoctors;

    public DoctorLoginService() {
        this.loggedInDoctors = new LinkedList<>();
    }

    // loginDoctor(code.entity.Doctor doctor) → add to list (front)
    public static void loginDoctor(Doctor doctor) {
        if (doctor == null) {
            System.err.println("Cannot login null doctor.");
            return;
        }
       doctor.setLoginTime(LocalTime.now());
        loggedInDoctors.addFirst(doctor); // Assuming addFirst for LIFO behavior
        System.out.println("Dr. " + doctor.getName() + " logged in at " + doctor.getLoginTime());
    }

    // logoutDoctor(int doctorId) → remove by ID
    public static boolean logoutDoctor(int doctorId) {
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
    public static void getAllLoggedInDoctors() {
        if (loggedInDoctors.isEmpty()) {
            System.out.println("No doctors currently logged in.");
            return;
        }

        System.out.println("\n--- Currently Logged-In Doctors ---");
        loggedInDoctors.printForward();
        System.out.println("-----------------------------------");
    }
}
