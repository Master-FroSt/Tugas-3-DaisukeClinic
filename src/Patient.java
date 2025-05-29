class PatientData { // class data patient
    private int ID;
    private String name;
    private String address;
    private String phoneNum;
    public PatientData(int ID, String Name, String Address, String phoneNum){
        this.ID = ID;
        this.name = Name;
        this.address = Address;
        this.phoneNum = phoneNum;
    }

    public int getId() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getphoneNum() {
        return phoneNum;
    }
}

public class Patient {
    BST patientBST = new BST();

    //Patient Record Management

    //addPatient()

    //removePatientById(int id)

    //findPatientByName(String name)

    //displayAllPatients()


    //Patient Tree
    public boolean insertPatient(PatientData p){
        // Cek jika object berupa null
        if(p == null){
            System.err.println("Error: Patient ID must be provided.");
            return false;
        }
        return patientBST.add(p.getId());
    }

    public int searchPatient(PatientData p){
        // Cek jika object berupa null
        if(p == null){
            System.err.println("Error: Patient ID must be provided.");
            return -1;
        }
        return patientBST.search(p.getId());
    }

    public void inOrderDisplay(){
        patientBST.inOrder();
    }
}
