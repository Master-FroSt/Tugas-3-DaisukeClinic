# Deskripsi Program, fitur (+ SS)
## Login Page
SS\
User memasukkan ID dan password untuk memiliki akses dokter atau admin. Setiap dokter memiliki akun yang berbeda sesuai dengan ID. Pasien dapat login dengan [ID saja?]

## Menu dengan akses admin
SS\
Admin memiliki akses untuk semua menu dalam program DaisukeClinic, diantaranya sebagai berikut.
[Copas text menu 1-14 di sini]

[1. SS tampilan isi pasien hingga pesan "Patient with the data above added to records"]\
Fitur tambah pasien\
User memasukkan biodata pasien diantaranya Nama, umur, alamat, dan nomor telepon. Data disimpan pada record.

[2. SS tampilan isi id pasien hingga pesan "Patient 'm' (ID: 0001) removed."]\
Fitur hapus pasien\
User memasukkan ID pasien untuk menghapusnya dari record.

[3. SS tampilan isi nama pasien hingga pesan isi data record]\
Fitur cari pasien\
User memasukkan nama pasien untuk mencari datanya dalam record.

[4. SS tampilan patient record]\
Fitur tampilkan semua pasien\
Program menampilkan sebuah tabel berisi data lengkap pasien

[5. SS tampilan doctor login hingga pesan "logged in sucessfully"]\
Fitur login dokter\
User memilih ID dokter untuk menandainya telah login.

[6. SS tampilan doctor logout]\
Fitur logout dokter\
User memilih ID dokter untuk menandainya telah logout

[7. SS tampilan last logged in doctor]\
Fitur tampilkan dokter tersedia\
Program menampilkan dokter yang aktif dengan keterangan waktu login dan spesialisasinya

[8-10. masih dipikir]\
User ...

[11. SS tampilan isi nama pasien hingga pesan isi data record]\
Fitur cari pasien dengan BST\
User memasukkan nama pasien untuk mencari datanya dalam record.

[12. SS tampilan patient record]\
Fitur tampilkan semua pasien dengan BST\
Program menampilkan sebuah tabel berisi data pasien

[13. SS Tampilan register doctor hingga registration successful]\
Fitur mendaftarkan dokter baru\
User memasukkan nama dokter, password baru, dan pilihan spesialisasi untuk mendaftarkannya ke record.

[14. SS]\
Fitur membuat jadwal dokter\
User ...

## Menu dengan akses dokter
SS\
Ketika masuk sebagai dokter, ia akan ditandai telah login.\
Fitur pada akses dokter memiliki perilaku yang sama dengan akses admin, tetapi terbatas. Menu tersebut diantaranya: 
[Copas text menu dokter]

## Menu dengan akses pasien
SS\
Fitur pada akses pasien memiliki perilaku yang sama dengan akses admin, tetapi terbatas. Menu tersebut diantaranya: 
[Copas text menu pasien]

===================================================

# Langkah alur algoritma dan spesifikasi (+ SS)
## Algoritma pada sistem utama main + helper
[SS pemanggilan modul handler]\
File main.java menginisialisasi scanner untuk masukan. Pada method main, initializeSystem() dan runLoginPortal() dipanggil, kemudian scanner ditutup. Method initializeSystem digunakan untuk mendeklarasikan modul handler diantaranya,
1. Record untuk pasien dan dokter.
2. Sistem tree pasien.
3. Sistem login dokter.
4. Queue untuk appointment.
Selain itu, ia juga memuat data-data dari file TXT yaitu data pasien, dokter, dan jadwal dokter.
[SS conditional switch untuk login]\
Method runLoginPortal digunakan sebagai interface untuk memisahkan akses antara admin, dokter, dan pasien dengan sistem identifikasi ID dan password [belum ganti baru].
[SS file Helper.java]\
File Helper.java berisi dua method, yaitu method integerInput dengan argument Scanner dan String untuk memvalidasi masukan integer serta method pressEnter dengan argument Scanner untuk melakukan jeda pada setiap aksi di program.

## Algoritma pada record pasien dan dokter
### Object Patient
SS\
1. Int ID
2. String name
3. Int age
4. String address
5. String phoneNumber
6. Linked list bertipe String Symptom
7. Linked list bertipe String Diagnosis
8. Linked list bertipe String Medication
9. String Notes

Pada method Constructor, argumen yang digunakan untuk inisialisasi adalah ID, password, name, age, address, dan phoneNumber. Properti lain diinisialisasikan sebagai sebuah string “-”. Semua properti memiliki setter dan getter, kecuali setter untuk ID karena dibuat secara otomatis melalui autoincrement. Terdapat method displayPatientInfo dan toString bentuk override untuk menampilkan data secara terformat.

### Object doctor
SS\
1. Int ID
2. String name
3. String specialty
4. String password
5. LocalDateTime loginTime
6. LocalDateTime logoutTime
7. Boolean isLogin
8. AppointmentServiceQueue queueApp.


Pada method Constructor, argumen yang digunakan untuk inisialisasi adalah ID, name, specialty, dan password. Properti lain diinisialisasikan sebagai false untuk tipe data boolean dan  null untuk tipe data bentukan. Sama seperti object patient, object doctor memiliki setter, getter, displayDoctorInfo, dan toString bentuk override.

### File PatientRecordService.java
SS\
1. Constructor makePatientRecord
2. Getter linked list tipe patient getPatientList
3. Return-type void addPatient dengan argument object Patient
Method validasi error dengan mengecek jika patient yang diberikan argumen kosong atau sudah ada di linked list. Jika tidak error, ia memasukkan object tersebut ke linked list dan tree. Data dari object dicetak dengan format khusus.
Return-type boolean removePatientById dengan argument int
Method validasi error dengan mengecek jika ID yang diberikan argumen bernilai kurang dari nol. Jika tidak error, ia membuat peubah bertipe Patient patientToRemove berisi null. ID dari argumen diiterasi dalam linked list. Saat ditemukan, seluruh object dipanggil dengan method get linked list dan disimpan dalam peubah tersebut. Penghapusan dilakukan dengan method removeByValue linked list dan  removePatient tree. Setelah itu, dilakukan validasi dengan pemilihan if jika penghapusan berhasil.
4. Return-type void findPatientByName dengan argument String
Method validasi error dengan mengecek jika nama yang diberikan argumen null atau kosong. Jika tidak error, ia membuat linked list bertipe Patient foundPatient. Nama dari argumen diiterasi dalam linked list untuk dicek dengan method contains. Saat ditemukan, seluruh object dipanggil dengan method get linked list dan disimpan dalam linked list foundPatient. Setelah itu, data object dicetak dengan format khusus.
5. Return-type void displayAllPatients
6. Dua method return-type void lainnya untuk manipulasi file. 

### File DoctorRecordService.java
SS\
1. Constructor makeDoctorRecord
2. Method mirip constructor return-type void makeSpecialList dan makeSpecialistCount
3. Return-type void updateSpecialist
4. Return-type void registerDoctor dengan argument object Doctor
5. Return-type Doctor findDoctorByID dengan argument int
6. Return-type Doctor authenticateDoctor dengan argument int dan String
7. Getter return-type masing-masing Doctor, String, dan Integer untuk method getAllDoctors, getSpecialistList, dan getSpecialistCount
8. Dua method return-type void lainnya untuk manipulasi file.

## Algoritma pada sistem baca/tulis TXT
File PatientRecordService.java dan DoctorRecordService.java menggunakan beberapa method untuk manipulasi file. Pada PatientRecordService.java, terdapat dua method yang memanfaatkan baca/tulis TXT, yaitu loadPatientTXT dan updatePatientTXT.\
[SS loadpatient.txt?]\
Algoritma loadPatientTXT dimulai dengan mengosongkan record patient dalam linked list dan tree. Kemudian, file txt dibaca per baris dan dipisahkan ke peubah pada record. Peubah itu diinialisasi menjadi object patient untuk disimpan pada linked list dan tree. Method ini menggunakan library I/O Java untuk membaca file, yakni package FileReader dan class BufferedReader. FileReader mengambil file yang ingin dibaca, kemudian BufferedReader melakukan operasi baca efisien dengan membaca file ke buffer memori. Metode lebih baik daripada membaca file per karakter. Pada akhir algoritma, terdapat validasi IOexception untuk error pada pembacaan file.\
[SS updatePatient.txt?]\
Algoritma updatePatientTXT dimulai dengan menghapus isi TXT menggunakan FileWriter. Ia lalu menuliskan format TXT dan melakukan iterasi pada record pasien di linked list. Data pada record ditulis per baris pada TXT dengan format khusus dipisahkan simbol “|”. Method ini menggunakan library I/O Java untuk menulis file, yakni package Writer  dan class FileWriter. FileWriter membuka file dalam mode overwrite untuk menghapus isinya dan menulis data baru. Pada akhir algoritma, terdapat validasi IOexception untuk error pada pembacaan file.\
[SS loadDoctorTXT dan updateDoctorTXT]\
Algoritma yang sama digunakan untuk manipulasi file pada record dokter\


## Algoritma pada record pasien tree
File DoctorLoginService.java memiliki beberapa method diantaranya constructor makePatientTree, setNextIdAdded, insertPatient dan removePatient dengan argument object patient, getter getNextIdAdd dan getPatientTree, searchPatient dengan argument int, serta inOrderDisplay

## Algoritma pada login/logout dokter
File DoctorLoginService.java memiliki beberapa method diantaranya constructor makeDoctorLogin, loginDoctor dengan argument object Doctor, logoutDoctor dengan argument int, dan getAllLoggedInDoctors.

## Algoritma pada login page [belum ganti baru]

## Algoritma pada sistem appointment [belum jadi]

##Algoritma pada schedule doctor [belum jadi]

===================================================

# Biodata Kelompok:
1. L0124074_Rifqia Hani Milatunnisa
2. L0124105_Muhammad Alfin Hasan
3. L0124113_Musyaffa Falih Suryono

===================================================

## Pembagian tugas:
1. Struktur data: {double dan singly Linked List, Stack, AVL Balance Tree}, sistem masukan dan keluaran file (txt), implementasi scanner dan helper untuk mendukung main, implementasi fungsi untuk menu 1-13, format id dokter sesuai spesialis,
2. Struktur data: {Queue}, format menu dan validasi, 
3. Struktur data: {BST}, Login page, pemisahan tiga menu {admin, dokter, dan pasien},


