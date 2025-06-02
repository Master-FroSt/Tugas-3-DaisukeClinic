import java.util.Scanner;

public class Patient {
        private int ID;
        private String name;
        private int age;
        private String address;
        private String phoneNum;
        public Patient(int id, String name, int age, String address, String phoneNum) {
            this.ID = id;
            this.name = name;
            this.age = age;
            this.address = address;
            this.phoneNum = phoneNum;
        }

//        Getter methods
        public int getID() {
            return ID;
        }
        public String getName() {
            return name;
        }
        public String getAddress() {
            return address;
        }
        public String getPhoneNum() {
            return phoneNum;
        }
    @Override
    public String toString() {
        return "Patient[ID=" + ID + ", Name='" + name + "', Age=" + age +
                ", Address='" + address + "', Phone='" + phoneNum + "']";
    }
    BST patientBST = new BST();
    LinkedList patientRecord = new LinkedList();
    Scanner scanner = new Scanner(System.in);

    //Patient Record Management

    public void addPatient(){
        System.out.print("Enter Patient ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Patient Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter Patient Phone Number: ");
        String phoneNum = scanner.nextLine();
        patientRecord.addFirst(new Patient(id, name, age, address, phoneNum));
    }

    public void removePatientById(int id) {
        if (id < 0) {
            System.err.println("Invalid Patient ID");
        }
        patientRecord.removeAt(id);
    }

    public void findPatientByName(String name){
        if (name  == null) {
            System.err.println("Invalid Patient Name");
        }
        // some code to search for data in "patientRecord" linked list
//            if(data.getName().equals(name)){
//                System.out.println(data.getID());
//                System.out.println(data.getName());
//                System.out.println(data.getAddress());
//                System.out.println(data.getPhoneNum());
//            }

    }

    public void displayAllPatients() {
        patientRecord.printForward();
    }


//    //Patient Searchable Tree
//    public boolean insertPatient(Patient p){
//        // Cek jika object berupa null
//        if(p == null){
//            System.err.println("Error: Patient ID cannot be null..");
//            return false;
//        }
//        return patientBST.add(p.ID);
//    }
//
//    public void searchPatient(int id){
//        // Cek jika object berupa null
//        if(id < 0){
//            System.err.println("Error: Patient ID cannot be null.");
//            return;
//        }
//        patientBST.search(id);
//
//
//    }
//
//    public void inOrderDisplay(){
//        patientBST.inOrder();
//    }
}
