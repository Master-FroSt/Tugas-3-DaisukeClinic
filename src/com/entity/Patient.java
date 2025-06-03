package com.entity;

public class Patient {
    private int ID;
    private String name;
    private int age;
    private String address;
    private String phoneNumber;

    public Patient(int id, String name, int age, String address, String phoneNumber) {
        this.ID = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // ==================================== Getter ====================================
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

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

    // ==================================== Display patient ====================================
    public void displayPatientInfo() {
        System.out.println("ID\t\t: " + ID);
        System.out.println("Nama\t\t: " + name);
        System.out.println("Umur\t\t: " + age);
        System.out.println("Alamat\t\t: " + address);
        System.out.println("No. Telepon\t: " + phoneNumber);
    }
}
