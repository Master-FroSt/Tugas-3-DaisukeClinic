package entity;

import handler.AppointmentQueueService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Doctor {
    private int ID;
    private String name;
    private String specialty;
    private String password;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private boolean isLogin;
    private AppointmentQueueService queueApp;

    public Doctor(int id, String name, String specialty, String password) {
        this.ID = id;
        this.name = name;
        this.specialty = specialty;
        this.password = password;
        this.loginTime = null;
        this.logoutTime = null;
        this.queueApp = new AppointmentQueueService();  // Queue per doctor?
    }

    // ============================== Getter ==============================
    public int getId() { return ID; }
    public String getName() { return name; }
    public String getSpecialty() { return specialty; }
    public String getPassword() { return password; }
    public LocalDateTime getLoginTime() { return loginTime; }
    public LocalDateTime getLogoutTime() { return logoutTime; }
    public boolean getIsLogin() { return isLogin; }
    public AppointmentQueueService getQueueApp() { return queueApp; }

    // ============================== Setter ==============================
    public void setName(String name) { this.name = name; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public void setLoginTime(LocalDateTime loginTime) { this.loginTime = loginTime; }
    public void setLogoutTime(LocalDateTime logoutTime) { this.logoutTime = logoutTime; }
    public void setIsLogin(boolean isLogin) { this.isLogin = isLogin; }
    public void setQueueApp(AppointmentQueueService queueApp) { this.queueApp = queueApp;}

    // ============================== Display doctor ==============================
    public void displayDoctorInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedLoginTime = (loginTime == null) ? "Not logged in" : loginTime.format(formatter);
        String formattedLogoutTime = (logoutTime == null) ? "Not logged out" : logoutTime.format(formatter);

        System.out.println("ID\t\t: " + ID);
        System.out.println("Name\t\t: " + name);
        System.out.println("Specialty\t: " + specialty);
        if (loginTime != null) {
            System.out.printf("Last Login\t: %s\n", formattedLoginTime);
        }
        if (logoutTime != null) {
            System.out.printf("Last Logout\t: %s\n", formattedLogoutTime);
        }
    }

    //    Override untuk memformat pencetakan class
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String loginStatus = " (Logged in at: " + (loginTime != null ? loginTime.format(formatter) : "N/A") + ")";
        return String.format("ID: %03d | Dr. %-20s | Specialty: %s%s", ID, name, specialty, loginStatus);
    }
}
