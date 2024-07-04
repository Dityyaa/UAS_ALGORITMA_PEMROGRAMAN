import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

class Mahasiswa {
    String nama;
    long pendapatanOrangTua;
    int jumlahKendaraan;
    int tanggunganOrangTua;
    long biayaListrik;
    long biayaUKT;
    String golonganUKT;
    boolean sudahDibayar;

    Mahasiswa(String nama, long pendapatanOrangTua, int jumlahKendaraan, int tanggunganOrangTua, long biayaListrik) {
        this.nama = nama;
        this.pendapatanOrangTua = pendapatanOrangTua;
        this.jumlahKendaraan = jumlahKendaraan;
        this.tanggunganOrangTua = tanggunganOrangTua;
        this.biayaListrik = biayaListrik;
        this.sudahDibayar = false;

        long biayaUKTSementara = hitungBiayaUKT();
        String[] hasil = tentukanGolonganDanBiayaUKT(biayaUKTSementara);
        this.golonganUKT = hasil[0];
        this.biayaUKT = Long.parseLong(hasil[1]);
    }

    private long hitungBiayaUKT() {
        long biayaDasar = 100000000; // Rp 1.000.000 dalam sen
        long multiplierPendapatan = 3; // 0,3% = 3 per 1000
        long multiplierKendaraan = 5000000; // Rp 50.000 dalam sen
        long multiplierTanggungan = 25000000; // Rp 250.000 dalam sen
        long multiplierListrik = 1; // 0,1% = 1 per 1000

        long biayaDariPendapatan = (pendapatanOrangTua * multiplierPendapatan) / 10;
        long biayaDariKendaraan = jumlahKendaraan * multiplierKendaraan;
        long biayaDariTanggungan = tanggunganOrangTua * multiplierTanggungan;
        long biayaDariListrik = (biayaListrik * multiplierListrik) / 1000;

        return biayaDasar + biayaDariPendapatan + biayaDariKendaraan - biayaDariTanggungan + biayaDariListrik;
    }

    private static String[] tentukanGolonganDanBiayaUKT(long biayaUKT) {
        if (biayaUKT <= 50000000) { // Rp 500.000
            return new String[] { "Golongan 1", "50000000" };
        } else if (biayaUKT <= 100000000) { // Rp 1.000.000
            return new String[] { "Golongan 2", "100000000" };
        } else if (biayaUKT <= 150000000) { // Rp 1.500.000
            return new String[] { "Golongan 3", "150000000" };
        } else if (biayaUKT <= 200000000) { // Rp 2.000.000
            return new String[] { "Golongan 4", "200000000" };
        } else if (biayaUKT <= 250000000) { // Rp 2.500.000
            return new String[] { "Golongan 5", "250000000" };
        } else {
            return new String[] { "Golongan 6", "300000000" }; // Anggap Golongan 6 maksimal Rp 3.000.000
        }
    }
}

public class project {
    private static Scanner scanner = new Scanner(System.in);
    private static NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    private static List<Mahasiswa> mahasiswaList = new ArrayList<>();

    public static void main(String[] args) {
        int pilihan;

        do {
            tampilkanMenu();
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    tambahMahasiswa();
                    break;
                case 2:
                    lihatMahasiswa();
                    break;
                case 3:
                    pembayaranUKT();
                    break;
                case 4:
                    riwayatPembayaran();
                    break;
                case 5:
                    System.out.println("Terima kasih telah menggunakan sistem ini.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (pilihan != 5);
    }

    private static void tampilkanMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Tambah Mahasiswa");
        System.out.println("2. Lihat Mahasiswa");
        System.out.println("3. Pembayaran UKT");
        System.out.println("4. Riwayat Pembayaran");
        System.out.println("5. Keluar");
        System.out.print("Pilih opsi: ");
    }

    private static void tambahMahasiswa() {
        System.out.print("Masukkan nama mahasiswa: ");
        String nama = scanner.nextLine();

        System.out.print("Masukkan pendapatan orang tua (per bulan dalam Rupiah): ");
        long pendapatanOrangTua = scanner.nextLong() * 100; // Konversi ke sen

        System.out.print("Masukkan jumlah kendaraan yang dimiliki keluarga: ");
        int jumlahKendaraan = scanner.nextInt();

        System.out.print("Masukkan jumlah tanggungan orang tua: ");
        int tanggunganOrangTua = scanner.nextInt();

        System.out.print("Masukkan biaya listrik rumah (per bulan dalam Rupiah): ");
        long biayaListrik = scanner.nextLong() * 100; // Konversi ke sen
        scanner.nextLine(); // Membersihkan buffer scanner

        Mahasiswa mahasiswa = new Mahasiswa(nama, pendapatanOrangTua, jumlahKendaraan, tanggunganOrangTua,
                biayaListrik);
        mahasiswaList.add(mahasiswa);

        System.out.println("Mahasiswa berhasil ditambahkan.");
    }

    private static void lihatMahasiswa() {
        if (mahasiswaList.isEmpty()) {
            System.out.println("Belum ada mahasiswa yang ditambahkan.");
            return;
        }

        for (int i = 0; i < mahasiswaList.size(); i++) {
            Mahasiswa mhs = mahasiswaList.get(i);
            System.out.println("Mahasiswa ke-" + (i + 1));
            System.out.println("Nama: " + mhs.nama);
            System.out.println("Pendapatan Orang Tua: " + rupiahFormat.format(mhs.pendapatanOrangTua / 100));
            System.out.println("Jumlah Kendaraan: " + mhs.jumlahKendaraan);
            System.out.println("Tanggungan Orang Tua: " + mhs.tanggunganOrangTua);
            System.out.println("Biaya Listrik: " + rupiahFormat.format(mhs.biayaListrik / 100));
            System.out.println("Golongan UKT: " + mhs.golonganUKT);
            System.out.println("Biaya UKT: " + rupiahFormat.format(mhs.biayaUKT / 100));
            System.out.println("Status Pembayaran: " + (mhs.sudahDibayar ? "Sudah dibayar" : "Belum dibayar"));
            System.out.println();
        }
    }

    private static void pembayaranUKT() {
        System.out.print("Masukkan nama mahasiswa yang akan membayar UKT: ");
        String nama = scanner.nextLine();

        for (Mahasiswa mhs : mahasiswaList) {
            if (mhs.nama.equalsIgnoreCase(nama)) {
                if (mhs.sudahDibayar) {
                    System.out.println("UKT mahasiswa ini sudah dibayar.");
                } else {
                    System.out.println(
                            "Biaya UKT untuk " + mhs.nama + " adalah: " + rupiahFormat.format(mhs.biayaUKT / 100));
                    System.out.print("Apakah Anda yakin ingin membayar (y/n)? ");
                    String konfirmasi = scanner.nextLine();

                    if (konfirmasi.equalsIgnoreCase("y")) {
                        mhs.sudahDibayar = true;
                        System.out.println("Pembayaran UKT berhasil.");
                    } else {
                        System.out.println("Pembayaran dibatalkan.");
                    }
                }
                return;
            }
        }
        System.out.println("Mahasiswa dengan nama " + nama + " tidak ditemukan.");
    }

    private static void riwayatPembayaran() {
        if (mahasiswaList.isEmpty()) {
            System.out.println("Belum ada mahasiswa yang ditambahkan.");
            return;
        }

        boolean adaPembayaran = false;

        for (Mahasiswa mhs : mahasiswaList) {
            if (mhs.sudahDibayar) {
                System.out.println("Nama: " + mhs.nama);
                System.out.println("Golongan UKT: " + mhs.golonganUKT);
                System.out.println("Biaya UKT: " + rupiahFormat.format(mhs.biayaUKT / 100));
                System.out.println();
                adaPembayaran = true;
            }
        }

        if (!adaPembayaran) {
            System.out.println("Belum ada mahasiswa yang membayar UKT.");
        }
    }
}
