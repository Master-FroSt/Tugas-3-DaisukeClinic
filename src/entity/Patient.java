package entity;

import datastructure.LinkedList;

public class Patient {
    private int ID;
    private String name;
    private int age;
    private String address;
    private String phoneNumber;
    private LinkedList<String> illnessHistory; // used later

    public Patient(int id, String name, int age, String address, String phoneNumber) {
        this.ID = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.illnessHistory = new LinkedList<>();
        illnessHistory.addFirst(" ");
    }

    // ==================================== Getter ====================================
    public int getID() { return ID; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public LinkedList<String> getIllnessHistory() { return illnessHistory; }

    // ==================================== Setter ====================================
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setIlnessHistory(LinkedList<String> illnessHistory) {
        this.illnessHistory = illnessHistory;
    }

    // ==================================== Display patient ====================================
    public void displayPatientInfo() {
        System.out.printf("ID\t\t: %04d\n", ID);
        System.out.println("Name\t\t: " + name);
        System.out.println("Age\t\t: " + age);
        System.out.println("Address\t\t: " + address);
        System.out.println("Phone Number\t: " + phoneNumber);
        System.out.println("Ilness History\t: " + illnessHistory.toString());
    }
}
