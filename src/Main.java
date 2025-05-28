import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("=== Sistem Rumah Sakit ===");
            System.out.println("1. Mendaftar Pasien");
            System.out.println("2. Hapus Pasien: ");
            System.out.println("3. Cari Pasien: ");//ini juga sama aja ga sih? ntar kita kasih menu lagi?
            System.out.println("4. Tampilkan semua Pasien"); //mending disini aja ntar dikasih pilihan ga si, daripada ada 2 menu yang sama, jadi di case kita ada scanner buat menu pake BST inorder sama yang ga
            System.out.println("5. login Dokter");
            System.out.println("6. logout Dokter: ");// cari ID
            System.out.println("7. Daftar Dokter: ");//pake stack?
            System.out.println("8. Jadwal reservasi: ");
            System.out.println("9. Proses reservasi: ");
            System.out.println("10. Daftar reservasi: ");
            System.out.println("11. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    // Logika untuk mendaftar pasien
                    System.out.println("Mendaftar Pasien...");
                    // Tambahkan kode untuk mendaftar pasien
                    break;
                case 2:
                    // Logika untuk menghapus pasien
                    System.out.println("Menghapus Pasien...");
                    // Tambahkan kode untuk menghapus pasien
                    break;
                case 3:
                    // ntar disini manggil satu class aja tapi didalam class itu ada pilihannya.
                    break;
                case 4:
                    //sama aja kek case 3.
                    break;
                case 5:
                    // Logika untuk login dokter
                    System.out.println("Login Dokter...");
                    // Tambahkan kode untuk login dokter
                    break;
                case 6:
                    // Logika untuk logout dokter
                    System.out.println("Logout Dokter...");
                    // Tambahkan kode untuk logout dokter
                    break;
                case 7:
                    // Logika untuk daftar dokter
                    System.out.println("Daftar Dokter...");
                    // Tambahkan kode untuk daftar dokter
                    break;
                case 8:
                    // Logika untuk jadwal reservasi
                    System.out.println("Jadwal Reservasi...");
                    // Tambahkan kode untuk jadwal reservasi
                    break;
                case 9:
                    // Logika untuk proses reservasi
                    System.out.println("Proses Reservasi...");
                    // Tambahkan kode untuk proses reservasi
                    break;
                case 10:
                    // Logika untuk daftar reservasi
                    System.out.println("Daftar Reservasi...");
                    // Tambahkan kode untuk daftar reservasi
                    break;
                case 11:
                    System.out.println("Keluar dari sistem.");
                    scanner.close();
                    return; // Keluar dari program
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
            scanner.close();
        }
    }
}
