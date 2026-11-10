# Naskah Video Preview Kilau

**Judul YouTube:** Preview Aplikasi Kilau Laundry - Kotlin, RecyclerView, CustomAdapter, dan Onboarding  
**Durasi target:** 7 menit  
**Deskripsi:** Demo aplikasi Kilau untuk pemesanan laundry antar-jemput. Video menunjukkan onboarding, autentikasi, Beranda dengan grid RecyclerView, pemesanan dengan date/time picker dan notifikasi, Riwayat dengan ListView CustomAdapter, Profil, serta perbandingan desain dan bukti Git.  
**Daftar timestamp:** 00:00 Pembuka · 00:30 Splash dan onboarding · 01:20 Registrasi, OTP, dan login · 02:35 Beranda · 03:25 Buat pesanan · 04:40 Riwayat · 05:20 Profil dan logout · 05:50 Perbandingan desain dan Git · 06:30 Penutup

## Rundown dan naskah

| Waktu | Durasi | Yang ditampilkan | Narasi | Materi yang diterapkan |
|---|---:|---|---|---|
| 00:00–00:30 | 0:30 | Judul video dan ikon aplikasi Kilau. | “Halo, saya akan memperkenalkan Kilau, aplikasi laundry antar-jemput yang dibuat menggunakan Kotlin dan ViewBinding. Kita akan melihat alur utama dari onboarding sampai pembuatan pesanan.” | Pengenalan aplikasi Android dan tujuan project. |
| 00:30–01:20 | 0:50 | Jalankan aplikasi dari Splash, geser tiga halaman onboarding, tekan Lanjut hingga Mulai. Tunjukkan indikator titik mengikuti halaman. | “Splash mengarahkan pengguna baru ke onboarding. Tiga halaman menjelaskan pemesanan, kurir antar-jemput, dan pemantauan status. Tombol Lewati atau Mulai menyimpan penanda onboarding agar tidak diulang pada pembukaan berikutnya.” | Splash, SharedPreferences, FragmentStateAdapter, ViewPager2, DotsIndicator. |
| 01:20–02:35 | 1:15 | Buka Registrasi, isi data, tunjukkan validasi kolom/OTP kosong, masukkan kode OTP untuk menyelesaikan registrasi. Coba login dengan kata sandi salah, lalu login dengan akun terdaftar. | “Form registrasi mengirim data ke halaman verifikasi. Input OTP kosong ditolak; pada demo ini nilai OTP yang tidak kosong menyelesaikan simulasi verifikasi. Saya tunjukkan juga login gagal ketika kredensial tidak cocok, lalu masuk memakai akun yang terdaftar.” | Activity dan Intent, validasi input, dialog Material, SharedPreferences, simulasi OTP. |
| 02:35–03:25 | 0:50 | Tampilkan Beranda; gulir empat tile layanan dan promo, kartu pesanan aktif, tombol Buat Pesanan Baru dan Tips. | “Beranda memakai header gradient Kilau dan sheet konten membulat. Empat layanan ditampilkan dalam dua kolom; pilihan kartu meneruskan nama dan harga layanan ke halaman pemesanan.” | Drawable XML, RecyclerView, GridLayoutManager, ViewBinding, Intent extras. |
| 03:25–04:40 | 1:15 | Buka satu layanan, ubah jumlah, pilih tanggal dan jam, tunjukkan validasi jadwal belum lengkap, lalu lengkapi dan buat pesanan. Tampilkan notifikasi jika izin tersedia. | “Pada Buat Pesanan, jumlah dan estimasi total berubah saat stepper digunakan. Jadwal dipilih lewat Material Date Picker dan Time Picker. Jika belum dipilih, aplikasi menampilkan validasi. Setelah dikonfirmasi, pesanan disimpan sebagai JSON di SharedPreferences dan aplikasi mencoba menampilkan notifikasi lokal.” | DatePicker, TimePicker, validasi, kalkulasi harga, JSON, SharedPreferences, notifikasi dan izin Android. |
| 04:40–05:20 | 0:40 | Buka tab Riwayat, tampilkan daftar, ketuk satu kartu untuk dialog detail. Jelaskan pesan kosong apabila daftar memang kosong. | “Riwayat membaca daftar pesanan yang tersimpan dan menampilkan contoh awal hanya saat belum ada data. Setiap baris menggunakan CustomAdapter dengan ViewHolder untuk penggunaan ulang tampilan. Ketukan pada baris membuka detail.” | ListView, BaseAdapter, ViewHolder, JSON, empty view, MaterialAlertDialog. |
| 05:20–05:50 | 0:30 | Buka Profil, tampilkan preferensi notifikasi dan Bantuan, kemudian tunjukkan konfirmasi logout. | “Di Profil, preferensi notifikasi dapat diubah dan menu Bantuan memutar ulang onboarding tanpa mengarahkan kembali ke login. Logout tetap meminta konfirmasi sebelum mengakhiri sesi.” | Fragment, SharedPreferences, Intent, dialog konfirmasi, alur logout. |
| 05:50–06:30 | 0:40 | Tampilkan tabel `docs/PERBANDINGAN_DESAIN.md`, kemudian terminal dengan `git log --oneline --graph --decorate`, `git status`, dan `git diff --stat`. | “Dibandingkan grand desain, splash, login, beranda, pemesanan, dan riwayat sudah tersedia dengan beberapa perbedaan yang tercatat. Lacak pesanan dan pembayaran menjadi rencana pengembangan. Berikut bukti riwayat commit dan status repository.” | Dokumentasi perbandingan, Git commit, status dan ringkasan perubahan. |
| 06:30–07:00 | 0:30 | Kembali ke Beranda atau tampilkan nama aplikasi sebagai penutup. | “Itulah preview Kilau. Fitur inti dibuat untuk menunjukkan alur pemesanan laundry, navigasi, penyimpanan lokal, serta penerapan RecyclerView dan CustomAdapter. Terima kasih sudah menonton.” | Ringkasan fitur dan penutup presentasi. |

## Catatan perekaman

- Total rundown adalah 7 menit.
- Agar skenario instalasi baru terlihat, hapus data aplikasi sebelum merekam bagian splash/onboarding.
- Minta izin notifikasi pada perangkat agar notifikasi dapat diperlihatkan; jika izin tidak diberikan, lanjutkan demo tanpa notifikasi.
- Untuk menampilkan keadaan daftar kosong, gunakan data uji dengan `order_list` berisi array JSON kosong; instalasi baru menampilkan tiga pesanan contoh sesuai requirement.
- Jangan menampilkan kata sandi atau data pribadi sungguhan dalam rekaman.
