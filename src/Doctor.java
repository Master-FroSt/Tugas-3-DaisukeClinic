

public class Doctor {
    private int ID;
    private String name;
    private String specialty;
    private String loginTime; // Or String loginTime;

    public Doctor(int ID, String name, String specialty) {
        this.ID = ID;
        this.name = name;
        this.specialty = specialty;
        // loginTime can be set during login operation
    }

    // Getter methods
    public int getID() { return ID; }
    public String getName() { return name; }
    public String getSpecialty() { return specialty; }
    public String getLoginTime() { return loginTime; }

    // Setter for login time
    public void setLoginTime(String loginTime) { this.loginTime = loginTime; }

    @Override
    public String toString() {
        return "Doctor[ID=" + ID + ", Name='" + name + "', Specialty='" + specialty +
                (loginTime != null ? "', LoginTime=" + loginTime : "' Not Logged In") + "]";
    }
}
