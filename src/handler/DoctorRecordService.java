package handler;

import datastructure.LinkedList;
import entity.Doctor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DoctorRecordService {
    private static LinkedList<Doctor> allDoctors;
    private static LinkedList<String> specialistList;
    private static LinkedList<Integer> specialistCount;

    private DoctorRecordService() {} // Private constructor for utility class

    public static void makeDoctorRecord() {
        allDoctors = new LinkedList<>();
        makeSpecialistList();
        makeSpecialistCount();
    }

    // Buat nyimpen ada specialist apa aja
    public static void makeSpecialistList() { // txt
        specialistList = new LinkedList<>();
        // open txt
        try (BufferedReader br = new BufferedReader(new FileReader("src/data/specialist.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                specialistList.addLast(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void updateSpecialist() {
        try {
            FileWriter myWriter = new FileWriter("src/data/specialist.txt");
            for (int i = 0; i < specialistList.size(); i++) {
                String localSpecialist = specialistList.get(i);
                if (localSpecialist != null) {
                    myWriter.write(localSpecialist + "\n");
                }
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void makeAppointmentTXT(int doctorId) {
        String fileName = "src/data/appointment/" + doctorId + ".txt";
        String defaultContent = "Appointment ID | Patient ID | Doctor ID | Time";

        File file = new File(fileName);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write(defaultContent.trim());
            } catch (IOException e) {
                System.out.println("Error creating appointment data for doctor file.");
                e.printStackTrace();
            }
        }
    }

    // Buat auto increment nanti pas register doctor
    private static void makeSpecialistCount() {
        specialistCount = new LinkedList<>();
        for (int i = 0; i < specialistList.size(); i++) {
            specialistCount.addLast(0);
        }
    }

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

    public static LinkedList<String> getSpecialistList() {
        return specialistList;
    }

    public static LinkedList<Integer> getSpecialistCount() {
        return specialistCount;
    }


    // ====================================== TXT ==============================
    // load txt
    public static void loadDoctorTXT() {
        if (allDoctors == null) {
            makeDoctorRecord();
        } else {
            allDoctors.clear();
        }

        try (BufferedReader br = new BufferedReader(new FileReader("src/data/doctor.txt"))) {
            String line;
            boolean isFirstline = true;

            while ((line = br.readLine()) != null) {
                // Skip header
                if (isFirstline) {
                    isFirstline = false;
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length < 5) continue;

                int ID = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String password = parts[2].trim();
                String reLogin = parts[3].trim();
                String reLogout = parts[4].trim();

                String specialist = DoctorRecordService.getSpecialistList().get(((ID % 10000) / 100) - 1);

                LocalDateTime login = reLogin.equals("null") ? null : LocalDateTime.parse(reLogin);
                LocalDateTime logout = reLogout.equals("null") ? null : LocalDateTime.parse(reLogout);

                Doctor newDoctor = new Doctor(ID, name, specialist, password);
                newDoctor.setLoginTime(login);
                newDoctor.setLogoutTime(logout);
                registerDoctor(newDoctor);

                if (newDoctor.getLoginTime() != null && newDoctor.getLoginTime().toLocalDate().equals(LocalDate.now()) && newDoctor.getLogoutTime() == null) {
                    newDoctor.setIsLogin(true);
                    DoctorLoginService.loginDoctor(newDoctor);
                }
                else {
                    newDoctor.setIsLogin(false);
                }

                AppointmentQueueService.loadAppointmentData(ID);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // save data to txt
    public static void updateDoctorTXT() {
        try {
            FileWriter myWriter = new FileWriter("src/data/doctor.txt");
            myWriter.write("Doctor ID | Name | Password | Login Time | Logout Time\n");
            for (int i = 0; i < allDoctors.size(); i++) {
                Doctor localDoctor = allDoctors.get(i);
                if (localDoctor != null) {
                    String loginTime = (localDoctor.getLoginTime() == null) ? "null" : localDoctor.getLoginTime().toString();
                    String logoutTime = (localDoctor.getLogoutTime() == null) ? "null" : localDoctor.getLogoutTime().toString();
                    myWriter.write(localDoctor.getId() + " | " + localDoctor.getName() + " | " + localDoctor.getPassword() + " | " + loginTime + " | " + logoutTime + "\n");
                }
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}