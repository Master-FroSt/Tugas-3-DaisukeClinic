package handler;

import datastructure.LinkedList;
import entity.Doctor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DoctorLoginService {
    private static LinkedList<Doctor> loggedInDoctors;

    private DoctorLoginService() {}

    public static void makeDoctorLogin() {
        loggedInDoctors = new LinkedList<>();
    }

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

        loggedInDoctors.addFirst(doctor); // Add to the front of linked list

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("Dr. " + doctor.getName() + " logged in successfully at " + doctor.getLoginTime().format(formatter));
    }

    // logoutDoctor(int doctorId) → remove by ID
    public static void logoutDoctor(int doctorId) {
        // Cek jika ID valid
        if (doctorId < 0) {
            System.err.println("Invalid code.entity.Doctor ID for logout.");
            return;
        }
        // mencari object berdasarkan ID
        int indexToRemove = -1;
        Doctor doctorToLogout = null;

        for (int i = 0; i < loggedInDoctors.size(); i++) {
            if (loggedInDoctors.get(i).getId() == doctorId) {
                doctorToLogout = loggedInDoctors.get(i);
                indexToRemove = i;
                break; // Exit the loop once found
            }
        }
        // Cek jika penghapusan berhasil
        if (indexToRemove != -1) {
            //  logout time
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
        System.out.println("-----------------------------------");
        System.out.printf("%-8s %-25s %-20s %-15s\n", "ID", "Name" , "Login Time", "Specialist");
        for (int i = 0; i < loggedInDoctors.size(); i++) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formattedLoginTime = loggedInDoctors.get(i).getLoginTime().format(formatter);
            System.out.printf("%-8d %-25s %-20s %-15s\n", loggedInDoctors.get(i).getId(), 
                                                                    loggedInDoctors.get(i).getName(),
                                                                    formattedLoginTime,
                                                                    loggedInDoctors.get(i).getSpecialty());
        }
        System.out.println("-----------------------------------");
    }
}
