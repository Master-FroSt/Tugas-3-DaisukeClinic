Deskripsi Program, fitur (+ SS)
SS
Login Page
User memasukkan ID dan password untuk memiliki akses dokter atau admin. Setiap dokter memiliki akun yang berbeda sesuai dengan ID. Pasien dapat login dengan [ID saja?]

SS
Menu dengan akses admin
Admin memiliki akses untuk semua menu dalam program DaisukeClinic, diantaranya sebagai berikut.
[Copas text menu 1-13 di sini]

[1. SS tampilan isi pasien hingga pesan "Patient with the data above added to records"]
Fitur tambah pasien
User memasukkan biodata pasien diantaranya Nama, umur, alamat, dan nomor telepon. Data disimpan pada record.

[2. SS tampilan isi id pasien hingga pesan "Patient 'm' (ID: 0001) removed."]
Fitur hapus pasien
User memasukkan ID pasien untuk menghapusnya dari record.

[3. SS tampilan isi nama pasien hingga pesan isi data record]
Fitur cari pasien
User memasukkan nama pasien untuk mencari datanya dalam record.

[4. SS tampilan patient record]
Fitur tampilkan semua pasien
Program menampilkan sebuah tabel berisi data lengkap pasien

[5. SS tampilan doctor login hingga pesan "logged in sucessfully"]
Fitur login dokter
User memilih ID dokter untuk menandainya telah login.

[6. SS tampilan doctor logout]
Fitur logout dokter
User memilih ID dokter untuk menandainya telah logout

[7. SS tampilan last logged in doctor]
Fitur tampilkan dokter tersedia
Program menampilkan dokter yang aktif dengan keterangan waktu login dan spesialisasinya

[8-10. masih dipikir]

[11. SS tampilan isi nama pasien hingga pesan isi data record]
Fitur cari pasien dengan BST
User memasukkan nama pasien untuk mencari datanya dalam record.

[12. SS tampilan patient record]
Fitur tampilkan semua pasien dengan BST
Program menampilkan sebuah tabel berisi data pasien

[13. SS Tampilan register doctor hingga registration successful]
Fitur mendaftarkan dokter baru
User memasukkan nama dokter, password baru, dan pilihan spesialisasi untuk mendaftarkannya ke record.

SS 
Menu dengan akses dokter
Ketika masuk sebagai dokter, ia akan ditandai telah login.
Fitur pada akses dokter memiliki perilaku yang sama dengan akses admin, tetapi terbatas. Menu tersebut diantaranya: 
[Copas text menu dokter]

Menu dengan akses pasien
Fitur pada akses pasien memiliki perilaku yang sama dengan akses admin, tetapi terbatas. Menu tersebut diantaranya: 
[Copas text menu pasien]

===================================================

Langkah alur algoritma dan spesifikasi (+ SS)

Algoritma pada sistem utama main + helper
[SS pemanggilan modul handler]
File main.java menginisialisasi scanner untuk masukan. Pada method main, initializeSystem() dan runLoginPortal() dipanggil, kemudian scanner ditutup. Method initializeSystem digunakan untuk mendeklarasikan modul handler diantaranya queue untuk appointment, record untuk pasien dan dokter, sistem login dokter, dan sistem tree pasien. Setelah itu, ia memuat data pasien pada file TXT.
[SS conditional switch untuk login]
Method runLoginPortal digunakan sebagai interface untuk memisahkan akses antara admin, dokter, dan pasien dengan sistem identifikasi ID dan password [belum ganti baru].
[SS file Helper.java]
File Helper.java berisi dua method, yaitu method integerInput dengan argument Scanner dan String untuk memvalidasi masukan integer serta method pressEnter dengan argument Scanner untuk melakukan jeda pada setiap aksi di program.

Algoritma pada record pasien dan dokter
SS
Object Patient memiliki enam properti diantaranya ID, name,age, address, phoneNumber, dan illnessHistory. Constructor menggunakan semua properti, kecuali illnessHistory. Semua properti memiliki setter dan getter. Terdapat method displayPatientInfo dan toString bentuk override untuk menampilkan data secara terformat.
SS
Object doctor memiliki delapan properti diantaranya ID, name, specialty, password, loginTime, logoutTime, isLogin, dan queueApp. Constructor menggunakan empat properti, yaitu ID, name, specialty, dan password. Sama seperti object patient, object doctor memiliki setter, getter, displayDoctorInfo, dan toString bentuk override.
SS
File PatientRecordService.java memiliki beberapa method diantaranya, constructor makePatientRecord, gettergetPatientList, addPatient dengan argument object Patient, removePatientById dengan argument int, findPatientByName dengan argument String, displayAllPatients, dan dua method untuk manipulasi file. 
SS
File DoctorRecordService.java memiliki beberapa method diantaranya, constructor {makeDoctorRecord, makeSpecialList, dan makeSpecialistCount}, registerDoctor dengan argument object Doctor, findDoctorByID dengan argument int, authenticateDoctor dengan argument int dan String, getter {getAllDoctors, getSpecialistList, dan getSpecialistCount}, dan dua method untuk manipulasi file.

Algoritma pada sistem baca/tulis TXT
File PatientRecordService.java dan DoctorRecordService.java menggunakan beberapa method untuk manipulasi file. Pada PatientRecordService.java, terdapat dua method yang memanfaatkan baca/tulis TXT, yaitu loadPatientTXT dan updatePatientTXT.
[SS loadpatient.txt?]
Algoritma loadPatientTXT dimulai dengan mengosongkan record patient dalam linked list dan tree. Kemudian, file txt dibaca per baris dan dipisahkan ke peubah pada record. Peubah itu diinialisasi menjadi object patient untuk disimpan pada linked list dan tree. Method ini menggunakan library I/O Java untuk membaca file, yakni package FileReader dan class BufferedReader. FileReader mengambil file yang ingin dibaca, kemudian BufferedReader melakukan operasi baca efisien dengan membaca file ke buffer memori. Metode lebih baik daripada membaca file per karakter. Pada akhir algoritma, terdapat validasi IOexception untuk error pada pembacaan file.
[SS updatePatient.txt?]
Algoritma updatePatientTXT dimulai dengan menghapus isi TXT menggunakan FileWriter. Ia lalu menuliskan format TXT dan melakukan iterasi pada record pasien di linked list. Data pada record ditulis per baris pada TXT dengan format khusus dipisahkan simbol “|”. Method ini menggunakan library I/O Java untuk menulis file, yakni package Writer  dan class FileWriter. FileWriter membuka file dalam mode overwrite untuk menghapus isinya dan menulis data baru. Pada akhir algoritma, terdapat validasi IOexception untuk error pada pembacaan file.
[SS loadDoctorTXT dan updateDoctorTXT]
Algoritma yang sama digunakan untuk manipulasi file pada record dokter


Algoritma pada record pasien tree
File DoctorLoginService.java memiliki beberapa method diantaranya constructor makePatientTree, setNextIdAdded, insertPatient dan removePatient dengan argument object patient, getter getNextIdAdd dan getPatientTree, searchPatient dengan argument int, serta inOrderDisplay

Algoritma pada login/logout dokter
File DoctorLoginService.java memiliki beberapa method diantaranya constructor makeDoctorLogin, loginDoctor dengan argument object Doctor, logoutDoctor dengan argument int, dan getAllLoggedInDoctors.

Algoritma pada login page [belum ganti baru]

Algoritma pada sistem appointment [belum jadi]

===================================================

Biodata Kelompok:
1. L0124074_Rifqia Hani Milatunnisa
2. L0124105_Muhammad Alfin Hasan
3. L0124113_Musyaffa Falih Suryono

===================================================

Pembagian tugas:
1. Struktur data: {double dan singly Linked List, Stack, AVL Balance Tree}, sistem masukan dan keluaran file (txt), implementasi scanner dan helper untuk mendukung main, implementasi fungsi untuk menu 1-13, format id dokter sesuai spesialis,
2. Struktur data: {Queue}, format menu dan validasi, 
3. Struktur data: {BST}, Login page, pemisahan tiga menu {admin, dokter, dan pasien},


