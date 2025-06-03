package code.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Doctor {
    private int id;
    private String name;
    private String specialty;
    private LocalDateTime loginTime;

    public Doctor(int id, String name, String specialty, LocalDateTime loginTime) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.loginTime = loginTime;
    }

    // ============================== Getter ==============================
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    // ============================== Setter ==============================
    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    // ============================== Display doctor ==============================
    public void displayDoctorInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedLoginTime = loginTime.format(formatter);

        System.out.println("ID\t\t\t: " + id);
        System.out.println("Nama\t\t\t: " + name);
        System.out.println("Spesialisasi\t: " + specialty);
        System.out.println("Login\t\t\t: " + formattedLoginTime);
    }
}