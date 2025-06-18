package entity;

public class Admin {
    private int id;
    private String password;
    private String name;

    public Admin(int id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    // ============================== Getter ==============================
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
}
