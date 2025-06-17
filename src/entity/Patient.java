package entity;

import datastructure.LinkedList;

public class Patient {
    private int ID;
    private String password;
    private String name;
    private int age;
    private String address;
    private String phoneNumber;
    private LinkedList<String> symptom; // lebih ke keluhan
    private LinkedList<String> diagnosis;
    private LinkedList<String> medication;
    private String notes;


    public Patient(int id, String password, String name, int age, String address, String phoneNumber) {
        this.ID = id;
        this.password = password;
        this.name = name;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.symptom = new LinkedList<>();
        symptom.addFirst("-");
        this.diagnosis = new LinkedList<>();
        diagnosis.addFirst("-");
        this.medication = new LinkedList<>();
        medication.addFirst("-");
        this.notes = "-";
    }

    // ==================================== Getter ====================================
    public int getID() { return ID; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getPassword() { return password;}
    public LinkedList<String> getSymptom() { return symptom; }
    public LinkedList<String> getDiagnosis() { return diagnosis; }
    public LinkedList<String> getMedication() { return medication; }
    public String getNotes() { return notes;}

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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSymptom(LinkedList<String> symptom) {
        this.symptom = symptom;
    }

    public void setDiagnosis(LinkedList<String> diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setMedication(LinkedList<String> medication) {
        this.medication = medication;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // ==================================== Display patient ====================================
    public void displayPatientInfo() {
        System.out.printf("ID\t\t: %d\n", ID);
        System.out.println("Name\t\t: " + name);
        System.out.println("Age\t\t: " + age);
        System.out.println("Address\t\t: " + address);
        System.out.println("Phone Number\t: " + phoneNumber);
        System.out.println("Symptom\t: " + symptom.toString());
        System.out.println("Diagnosis\t: " + diagnosis.toString());
        System.out.println("Medication\t: " + medication.toString());
        System.out.println("Notes\t: " + notes);
    }

    @Override
    public String toString() {
        return String.format("ID: %04d | Name: %-25s | Age: %d", ID, name, age);
    }
}
