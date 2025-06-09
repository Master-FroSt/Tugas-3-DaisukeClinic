package com.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Doctor {
    private int ID;
    private String name;
    private String specialty;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private boolean isLogin;

    public Doctor(int id, String name, String specialty) {
        this.ID = id;
        this.name = name;
        this.specialty = specialty;
    }

    // ============================== Getter ==============================
    public int getId() { return ID; }
    public String getName() { return name; }
    public String getSpecialty() { return specialty; }
    public LocalDateTime getLoginTime() { return loginTime; }
    public LocalDateTime getLogoutTime() { return logoutTime; }
    public boolean getIsLogin() { return isLogin; }

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

    public void setLogoutTime(LocalDateTime logoutTime) {
        this.logoutTime = logoutTime;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    // ============================== Display doctor ==============================
    public void displayDoctorInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedLoginTime = loginTime.format(formatter);

        System.out.println("ID\t\t\t: " + ID);
        System.out.println("Nama\t\t\t: " + name);
        System.out.println("Spesialisasi\t: " + specialty);
        System.out.println("Login\t\t\t: " + formattedLoginTime);
    }
}
