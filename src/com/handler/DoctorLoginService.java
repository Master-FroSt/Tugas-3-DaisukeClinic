package com.handler;

import com.datastructure.LinkedList;
import com.entity.Doctor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DoctorLoginService {
    private static final LinkedList<Doctor> loggedInDoctors = new LinkedList<>();

    private DoctorLoginService() {}

    public static void loginDoctor(Doctor doctor) {
        if (doctor == null) {
            System.err.println("Cannot login null doctor.");
            return;
        }

        // Check if the doctor is already logged in.
        if (loggedInDoctors.contains(doctor)) {
            System.err.println("Dr. " + doctor.getName() + " is already logged in.");
            return;
        }

        // Set login time and status
        doctor.setLoginTime(LocalDateTime.now());
        doctor.setIsLogin(true);
        doctor.setLogoutTime(null); // Clear any previous logout time.

        loggedInDoctors.addFirst(doctor); // Add to the front of the queue

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("Dr. " + doctor.getName() + " logged in successfully at " + doctor.getLoginTime().format(formatter));
    }

    // logoutDoctor(int doctorId) → remove by ID
    public static void logoutDoctor(int doctorId) {
//        Cek jika ID valid
        if (doctorId < 0) {
            System.err.println("Invalid code.entity.Doctor ID for logout.");
            return;
        }
//mencari object berdasarkan ID
        int indexToRemove = -1;
        Doctor doctorToLogout = null;

        for (int i = 0; i < loggedInDoctors.size(); i++) {
            if (loggedInDoctors.get(i).getId() == doctorId) {
                doctorToLogout = loggedInDoctors.get(i);
                indexToRemove = i;
                break; // Exit the loop once found
            }
        }
//        Cek jika penghapusan berhasil
        if (indexToRemove != -1) {
//            Set logout time
            doctorToLogout.setLogoutTime(LocalDateTime.now());
            doctorToLogout.setIsLogin(false);

            loggedInDoctors.removeAt(indexToRemove); // Use removeAt(index)
            System.out.println("Dr. " + doctorToLogout.getName() + " (ID: " + doctorId + ") logged out successfully.");
        } else {
            System.out.println("Logged in doctor with ID " + doctorId + " not found.");
        }
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
