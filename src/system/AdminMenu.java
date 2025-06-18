package system;

import datastructure.LinkedList;
import datastructure.PriorityQueue;
import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import entity.Schedule;
import handler.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class AdminMenu {

    public static void run(Scanner s){
        String choice;
        do {
            showAdminMenu();
            System.out.print("Pilih menu\t: ");
            choice = s.nextLine();

            switch (choice) {
                case "1" -> {
                    addNewPatient(s);
                    PatientRecordService.updatePatientTXT();
                }
                case "2" -> {
                    removePatientId(s);
                    PatientRecordService.updatePatientTXT();
                }
                case "3" -> {
                    searchPatientName(s);
                }
                case "4" -> {
                    displayPatientList();
                }
                case "5" -> {
                    doctorLogin(s);
                    DoctorRecordService.updateDoctorTXT();
                }
                case "6" -> {
                    doctorLogout(s);
                    DoctorRecordService.updateDoctorTXT();
                }
                case "7" -> viewLoggedInDoctors();
                case "8" -> scheduleAppointment(s);
                case "9" -> { AppointmentQueueService.processNextAppointment(s); PatientRecordService.updatePatientTXT(); }
                case "10" -> displayAppointments(s);
                case "11" -> searchPatientById(s);
                case "12" -> displayPatientsInOrder();
                case "13" -> {
                    doctorRegister(s);
                    DoctorRecordService.updateDoctorTXT();
                }
                case "14" -> {
                    makeDoctorSchedule(s);
                }
                case "15" -> {
                    ScheduleRecord.printAllSchedule();
                }
                case "0" -> {
                    System.out.println("Thanks for using Daisuke Clinic!");
                    System.out.println("Exiting.....");
                }
                default -> System.out.println("Invalid Input!");
            }
            if (!choice.equals("0")) {
                Helper.pressEnter(s);
            }
        } while (!choice.equals("0"));
    }

    private static void showAdminMenu() {
        Helper.clearScreen();
        System.out.println("\n==== Data Manager Pro ====\n");
        System.out.println("1. Add New Patient");
        System.out.println("2. Remove Patient by ID");
        System.out.println("3. Search Patient by Name");
        System.out.println("4. Display All Patient");
        System.out.println("5. Doctor Login");
        System.out.println("6. Doctor Logout");
        System.out.println("7. View Last Logged-in Doctor");
        System.out.println("8. Schedule Appointment");
        System.out.println("9. Process Appointment");
        System.out.println("10. Display Upcoming Appointments");
        System.out.println("11. Search Patient by ID (BST)");
        System.out.println("12. Display All Patients (BST Inorder)");
        System.out.println("13. Register New Doctor");
        System.out.println("14. Make Doctor Schedule");
        System.out.println("15. See All Doctor Schedule");
        System.out.println("0. Exit");
    }

    // =========================== function 1 ===========================
    public static void addNewPatient(Scanner scanner) {
        Helper.clearScreen();
        System.out.println("\n======= Add New Patient =======\n");
        int newId = PatientSearchTreeService.getNextIdAdd(); // input ID (otomatis)
        
        String newName; // input nama
        do { 
            System.out.print("Masukkan Nama Pasien (maksimal 25 karakter): ");
            newName = scanner.nextLine().trim();
            if (newName.length() > 25) {
                System.out.println("Nama terlalu panjang. Maksimal 25 karakter.");
            }
        } while (newName.length() > 25);

        System.out.print("Masukkan Password Pasien: "); // input password
        String newPass = scanner.nextLine();
        
        int newAge = Helper.integerInput(scanner, "Masukkan Umur Pasien: "); // input age

        System.out.println("Masukkan Alamat Pasien: "); // input address
        System.out.print("\t>> Provinsi: ");
        String provinsi = scanner.nextLine();
        System.out.print("\t>> Kab/Kota: ");
        String kota = scanner.nextLine();
        System.out.print("\t>> Kecamatan: ");
        String kec = scanner.nextLine();
        String newAddress = provinsi + ", " + kota + ", " + kec;

        System.out.print("Masukkan Nomor Telepon Pasien (angka saja) (Contoh 081234567890): "); // input phone number
        String input = scanner.nextLine().trim();
        // Validasi hanya digit
        while (!input.matches("\\d+")) {
            System.out.print("Input tidak valid. Masukkan hanya angka: ");
            input = scanner.nextLine().trim();
        }
        // Format: pisahkan setiap 4 digit
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            formatted.append(input.charAt(i));
            if ((i + 1) % 4 == 0 && i != input.length() - 1) {
                formatted.append("-");
            }
        }
        String newPhoneNumber = formatted.toString();
        
        Patient newPatient = new Patient(newId, newPass, newName, newAge, newAddress, newPhoneNumber);
        PatientRecordService.addPatient(newPatient);
    }

    // =========================== function 2 ===========================
    public static void removePatientId(Scanner scanner) {
        Helper.clearScreen();
        System.out.println("\n======= Remove Patient by ID =======\n");
        int toRemoveID = Helper.integerInput(scanner, "Masukkan ID Patient: ");

        PatientRecordService.removePatientById(toRemoveID);
    }

    // =========================== function 3 ===========================
    public static void searchPatientName(Scanner scanner) {
        Helper.clearScreen();
        System.out.println("\n======= Search Patient by Name =======\n");
        System.out.print("Masukkan nama Patient: ");
        String patientName = scanner.nextLine();
        PatientRecordService.findPatientByName(patientName);
    }

    // =========================== function 4 ===========================
    public static void displayPatientList() {
        Helper.clearScreen();
        System.out.println("\n======= Display All Patient =======\n");
        PatientRecordService.displayAllPatients();
    }

    // =========================== function 5 ===========================
    private static void doctorLogin(Scanner scanner) {
        Helper.clearScreen();
        System.out.println("\n======= Doctor Login =======\n");
        System.out.println("Doctors Available Now (With same schedule, You may login another doctor that not present):");

        int today = LocalDate.now().getDayOfWeek().getValue(); // 1=Mon, ..., 7=Sun
        LocalTime now = LocalTime.now();

        LinkedList<Doctor> allDoctors = DoctorRecordService.getAllDoctors();
        LinkedList<Schedule> allSchedules = ScheduleRecord.getScheduleList();

        for (int i = 0; i < allDoctors.size(); i++) {
            Doctor d = allDoctors.get(i);

            for (int j = 0; j < allSchedules.size(); j++) {
                Schedule s = allSchedules.get(j);

                if (s.getDoctorId() == d.getId() &&
                    s.getDay() == today &&
                    !now.isBefore(s.getBeginTime()) &&
                    !now.isAfter(s.getFinishTime())) {

                    System.out.printf("  ID: %d, Name: Dr. %s, Specialty: %s\n", d.getId(), d.getName(), d.getSpecialty());
                    break; // avoid printing duplicates
                }
            }
        }

        int idDokter = Helper.integerInput(scanner, "\nEnter Doctor ID to login: ");

        // Find the doctor from the master record service
        Doctor doctorToLogin = DoctorRecordService.findDoctorById(idDokter);

        if (doctorToLogin != null) {
            DoctorLoginService.loginDoctor(doctorToLogin);
        } else {
            System.out.println("Doctor with ID " + idDokter + " not found.");
        }
    }

    // =========================== function 6 ===========================
    private static void doctorLogout(Scanner scanner) {
        Helper.clearScreen();
        System.out.println("\n======= Doctor Logout =======\n");
        for (int i = 0; i < DoctorLoginService.getLoginList().size(); i++) {
            Doctor d = DoctorLoginService.getLoginList().get(i);
            System.out.printf("  ID: %d, Name: Dr. %s, Specialty: %s\n", d.getId(), d.getName(), d.getSpecialty());
        }
        int doctorId = Helper.integerInput(scanner, "Enter Doctor ID to logout: ");
        DoctorLoginService.logoutDoctor(doctorId);
    }

    // =========================== function 7 ===========================
    static void viewLoggedInDoctors() {
        Helper.clearScreen();
        System.out.println("\n======= View Logged-in Doctors =======\n");
        DoctorLoginService.getAllLoggedInDoctors();
    }

    // =========================== function 8, 9, 10 (optional, uncomment if needed) ===========================
    // =========================== function 8 ===========================
    static void scheduleAppointment(Scanner scanner) {
        Helper.clearScreen();
        System.out.println("\n======= Schedule Appointment =======\n");

        // 1. Pilih spesialisasi
        System.out.println("Daftar Spesialisasi yang Tersedia:");
        for (int i = 0; i < DoctorRecordService.getSpecialistList().size(); i++) {
            System.out.println((i + 1) + ". " + DoctorRecordService.getSpecialistList().get(i));
        }

        int selectedSpecialtyIndex;
        do {
            selectedSpecialtyIndex = Helper.integerInput(scanner, "Pilih nomor spesialisasi: ") - 1;
        } while (selectedSpecialtyIndex < 0 || selectedSpecialtyIndex >= DoctorRecordService.getSpecialistList().size());

        String chosenSpecialty = DoctorRecordService.getSpecialistList().get(selectedSpecialtyIndex);

        // 2. Filter dan tampilkan dokter sesuai spesialisasi
        System.out.println("\nDaftar Dokter dengan Spesialisasi: " + chosenSpecialty);
        LinkedList<Doctor> filteredDoctors = new LinkedList<>();
        for (int i = 0; i < DoctorRecordService.getAllDoctors().size(); i++) {
            Doctor d = DoctorRecordService.getAllDoctors().get(i);
            if (d.getSpecialty().equalsIgnoreCase(chosenSpecialty)) {
                filteredDoctors.addLast(d);
                System.out.println("ID: " + d.getId() + " - " + d.getName());
            }
        }

        if (filteredDoctors.isEmpty()) {
            System.out.println("Tidak ada dokter untuk spesialisasi ini.");
            return;
        }

        // 3. Pilih ID dokter dari daftar yang difilter
        int idDoctor;
        Doctor selectedDoctor = null;
        boolean isDoctorID = false;
        do {
            idDoctor = Helper.integerInput(scanner, "Masukkan ID Dokter: ");
            for (int i = 0; i < filteredDoctors.size(); i++) {
                if (filteredDoctors.get(i).getId() == idDoctor) {
                    selectedDoctor = filteredDoctors.get(i);
                    isDoctorID = true;
                    break;
                }
            }
            if (!isDoctorID) {
                System.out.println("ID dokter tidak valid.");
            }
        } while (!isDoctorID);

        // 4. Masukkan ID pasien & validasi
        int idPatient;
        boolean isPatientValid = false;
        do {
            idPatient = Helper.integerInput(scanner, "Masukkan ID Pasien: ");
            Patient p = PatientSearchTreeService.getPatientTree().containsID(idPatient);
            if (p != null && PatientRecordService.getPatientList().contains(p)) {
                isPatientValid = true;
            } else {
                System.out.println("ID pasien tidak ditemukan.");
            }
        } while (!isPatientValid);

        // 5. Generate ID janji temu dari waktu sekarang
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime base = LocalDateTime.of(2025, 6, 1, 0, 0);
        int idAppointment = (int) ChronoUnit.SECONDS.between(base, now);

        // 6. Tampilkan jadwal dokter
        System.out.println("\nJadwal Dokter:");
        LinkedList<Schedule> schedules = ScheduleRecord.getScheduleListByDoctor(idDoctor);
        for (int i = 0; i < schedules.size(); i++) {
            Schedule s = schedules.get(i);
            String namaHari = ScheduleRecord.getDayName(s.getDay()); // s.getDay() mengembalikan int
            System.out.println("- Hari " + namaHari + " dari " + s.getBeginTime() + " sampai " + s.getFinishTime());
        }

        // 7. Input tanggal dan waktu appointment
        LocalDate appointmentDate;
        LocalTime appointmentTime;
        LocalDateTime appointmentDateTime = null;
        boolean validTime = false;

        do {
            System.out.print("Masukkan tanggal appointment (format: yyyy-MM-dd): ");
            String inputDate = scanner.nextLine();

            System.out.print("Masukkan waktu appointment (format: HH:mm): ");
            String inputTime = scanner.nextLine();
            try {
                appointmentDate = LocalDate.parse(inputDate);
                appointmentTime = LocalTime.parse(inputTime);
                appointmentDateTime = LocalDateTime.of(appointmentDate, appointmentTime);

                if (appointmentDateTime.isAfter(LocalDateTime.now().plusMonths(1))) {
                    System.out.println("Appointment tidak bisa lebih dari 1 bulan dari sekarang. Coba lagi.");
                    continue;
                }

                if (appointmentDateTime.isBefore(LocalDateTime.now())) {
                    System.out.println("Waktu appointment tidak boleh di masa lalu. Coba lagi.");
                    continue;
                }

                int dayOfWeek = appointmentDate.getDayOfWeek().getValue(); // 1=Senin, 7=Minggu

                // 8. Validasi apakah waktu ada di dalam salah satu schedule
                for (int i = 0; i < schedules.size(); i++) {
                    Schedule s = schedules.get(i);
                    if (s.getDay() == dayOfWeek &&
                        !appointmentTime.isBefore(s.getBeginTime()) &&
                        !appointmentTime.isAfter(s.getFinishTime())) {
                        validTime = true;
                        break;
                    }
                }

                if (!validTime) {
                    System.out.println("Waktu tidak sesuai dengan jadwal dokter. Coba lagi.");
                }
            } catch (Exception e) {
                System.out.println("Format input tidak valid. Ulangi.");
            }

        } while (!validTime);

        AppointmentQueueService.loadAppointmentData(idDoctor);
        // 9. Buat dan jadwalkan janji temu
        Appointment appointment = new Appointment(idAppointment, idPatient, idDoctor, appointmentDateTime);
        System.out.println("");
        AppointmentQueueService.scheduleAppointment(appointment);
    }

    // =========================== function 10 ===========================
    private static void displayAppointments(Scanner scanner) {
        Helper.clearScreen();
        System.out.println("\n======= Display Upcoming Appointments =======\n");

        // 1. Tampilkan daftar spesialisasi
        System.out.println("Daftar Spesialisasi yang Tersedia:");
        LinkedList<String> specialistList = DoctorRecordService.getSpecialistList();
        for (int i = 0; i < specialistList.size(); i++) {
            System.out.println((i + 1) + ". " + specialistList.get(i));
        }

        int selectedSpecialtyIndex;
        do {
            selectedSpecialtyIndex = Helper.integerInput(scanner, "Pilih nomor spesialisasi: ") - 1;
        } while (selectedSpecialtyIndex < 0 || selectedSpecialtyIndex >= specialistList.size());

        String chosenSpecialty = specialistList.get(selectedSpecialtyIndex);

        // 2. Tampilkan daftar dokter untuk spesialisasi tersebut
        LinkedList<Doctor> filteredDoctors = new LinkedList<>();
        System.out.println("\nDaftar Dokter dengan Spesialisasi: " + chosenSpecialty);
        for (int i = 0; i < DoctorRecordService.getAllDoctors().size(); i++) {
            Doctor d = DoctorRecordService.getAllDoctors().get(i);
            if (d.getSpecialty().equalsIgnoreCase(chosenSpecialty)) {
                filteredDoctors.addLast(d);
                System.out.println("ID: " + d.getId() + " - " + d.getName());
            }
        }

        if (filteredDoctors.isEmpty()) {
            System.out.println("Tidak ada dokter dalam spesialisasi ini.");
            return;
        }

        // 3. Pilih dokter
        int doctorId;
        boolean validDoctor = false;
        do {
            doctorId = Helper.integerInput(scanner, "Masukkan ID Dokter: ");
            for (int i = 0; i < filteredDoctors.size(); i++) {
                Doctor d = filteredDoctors.get(i);
                if (d.getId() == doctorId) {
                    validDoctor = true;
                    break;
                }
            }
            if (!validDoctor) {
                System.out.println("ID dokter tidak valid.");
            }
        } while (!validDoctor);

        // proses
        AppointmentQueueService.viewUpcomingAppointments(doctorId);
    }

    // =========================== function 11 ===========================
    private static void searchPatientById(Scanner scanner) {
        Helper.clearScreen();
        System.out.println("\n======= Search Patient by ID =======\n");
        int idPasien = Helper.integerInput(scanner, "Enter Patient ID to search: ");
        PatientSearchTreeService.searchPatient(idPasien);
    }

    // =========================== function 12 ===========================
    private static void displayPatientsInOrder() {
        Helper.clearScreen();
        System.out.println("\n======= Display All Patients (Sorted by ID) =======\n");
        PatientSearchTreeService.inOrderDisplay();
    }

    // =========================== function 13 ===========================
    public static void doctorRegister(Scanner scanner) {
        Helper.clearScreen();
        System.out.println("\n======= Register New Doctor =======\n");
        System.out.print("Enter Doctor's Name : "); // input name
        String newName = scanner.nextLine();
        System.out.print("Enter new password : "); // input address
        String password = scanner.nextLine();

        boolean isGetSpecialist = false;
        String specialist = "";
        int newId = 0;
        int choosenOption;
        do {
            System.out.println("\nChoose doctor specialist");
            for (int i = 0; i < DoctorRecordService.getSpecialistList().size(); i++) {
                System.out.println("( " + (i + 1) + " ) " + DoctorRecordService.getSpecialistList().get(i));
            }
            System.out.println("( " + (DoctorRecordService.getSpecialistList().size() + 1) + " ) Add new specialist");
            choosenOption = Helper.integerInput(scanner, "\nOption choosen (1 - " + (DoctorRecordService.getSpecialistList().size() + 1) + " ) : ");
            if (choosenOption < 1 || choosenOption > (DoctorRecordService.getSpecialistList().size() + 1)) {
                System.out.println("Input invalid!!");
            }
            // spesialis baru
            else if (choosenOption == (DoctorRecordService.getSpecialistList().size() + 1)) {
                System.out.print("Input new specialist : ");
                String newSpecialist = scanner.nextLine();
                DoctorRecordService.getSpecialistList().addLast(newSpecialist);
                DoctorRecordService.getSpecialistCount().addLast(1);
                specialist = newSpecialist;
                String formattedId = String.format("1%02d%02d", (DoctorRecordService.getSpecialistList().size()), 1);
                newId = Integer.parseInt(formattedId);
                isGetSpecialist = true;

                // buat nyimpen
                DoctorRecordService.updateSpecialist();
                DoctorRecordService.makeSpecialistList();

            }
            else {
                if (DoctorRecordService.getSpecialistCount().get(choosenOption - 1) > 99) {
                    System.out.println("Doctor in this specialist is already full!");
                }
                else {
                    DoctorRecordService.getSpecialistCount().set(choosenOption - 1, DoctorRecordService.getSpecialistCount().get(choosenOption - 1) + 1);
                    specialist = DoctorRecordService.getSpecialistList().get(choosenOption- 1);
                    int specialistIndex = choosenOption - 1;
                    int doctorNumber = DoctorRecordService.getSpecialistCount().get(specialistIndex) + 1;
                    String formattedId = String.format("1%02d%02d", specialistIndex + 1, doctorNumber);
                    newId = Integer.parseInt(formattedId);
                    isGetSpecialist = true;
                }
            }
        } while (!isGetSpecialist);

        Doctor newDoctor = new Doctor(newId, newName, specialist, password);
        DoctorRecordService.registerDoctor(newDoctor);
    }

    // =========================== function 14 ===========================
    public static void makeDoctorSchedule(Scanner scanner) {
        Helper.clearScreen();
        System.out.println("\n======= Make Doctor Schedule =======\n");
        int idDoctor = Helper.integerInput(scanner, "Masukkan ID Doctor: ");
        Doctor doctor = DoctorRecordService.findDoctorById(idDoctor);
        if (doctor == null) {
            System.out.println("Doctor with ID " + idDoctor + " not found.");
            return;
        }

        int day = Helper.integerInput(scanner, "Masukkan hari (1=Senin => 7=Minggu) (masukkan angka): ");
        LocalTime beginTime = Helper.timeInput(scanner, "Masukkan waktu mulai (HH:mm): ");
        LocalTime finishTime = Helper.timeInput(scanner, "Masukkan waktu selesai (HH:mm): ");

        Schedule newSchedule = new Schedule(idDoctor, day, beginTime, finishTime);
        ScheduleRecord.addSchedule(newSchedule);

        // Simpan ke file dengan format: ID|day|beginTime|finishTime
        // String newSchedule = idDoctor + "|" + day + "|" + beginTime + "|" + finishTime;
        ScheduleRecord.saveScheduleToTXT();
    }
}