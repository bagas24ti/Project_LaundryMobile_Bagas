# Perbandingan dengan Grand Desain Kilau

Perbandingan berikut berdasarkan implementasi aktual pada project. Status **Sesuai** berarti fungsi dan susunan utama layar telah tersedia, **Berbeda** berarti layar ada tetapi masih memiliki perbedaan fitur/tampilan, dan **Belum dibuat** berarti belum ada layar khusus yang mengimplementasikannya.

| Layar grand desain | Implementasi aktual | Status | Perbedaan utama | Alasan |
|---|---|---|---|---|
| Splash | `SplashScreenActivity.kt`, `res/layout/activity_splash_screen.xml` | Sesuai | Menampilkan identitas Kilau dan tagline; setelah jeda 2 detik, aplikasi meneruskan pengguna berdasarkan status login/onboarding. | Desain splash tetap digunakan dan alur onboarding ditambahkan untuk pengguna baru. |
| Login | `AuthActivity.kt`, `res/layout/activity_auth.xml` | Sesuai | Login lokal memakai akun terdaftar atau akun demo dengan username dan kata sandi yang sama. | Fokus project adalah demonstrasi alur autentikasi tanpa backend. |
| Beranda | `BaseActivity.kt`, `home/HomeFragment.kt`, `res/layout/activity_base.xml`, `res/layout/fragment_home.xml`, `res/layout/item_layanan.xml` | Berbeda | Header gradient, empat tile layanan, promo, pesanan aktif, dan tombol pemesanan/tips tersedia. Bottom navigation memiliki tiga tab (Beranda, Riwayat, Profil), sedangkan desain awal menampilkan dua tab. | Riwayat dan Profil dipertahankan sebagai navigasi utama aplikasi yang sudah berjalan. |
| Buat Pesanan | `home/OrderActivity.kt`, `res/layout/activity_order.xml` | Berbeda | Layanan, jumlah/berat, alamat, jadwal tanggal dan jam, catatan, serta estimasi total tersedia. Tombol konfirmasi menyimpan pesanan lokal dan memicu notifikasi; belum ada alur checkout/pembayaran terpisah. | Pemesanan disesuaikan dengan fitur lokal yang tersedia dan tidak memerlukan backend transaksi. |
| Lacak Pesanan | Belum ada Activity/Fragment atau layout khusus. Status terakhir hanya ditampilkan pada kartu Pesanan Aktif di `HomeFragment.kt` dan data status disimpan oleh `OrderActivity.kt`. | Belum dibuat | Belum tersedia timeline tahapan pesanan maupun kartu informasi kurir. | Dibutuhkan layar dan data operasional kurir/status yang belum ada di aplikasi saat ini. |
| Pembayaran | Belum ada Activity/Fragment atau layout pembayaran. `ProfilFragment.kt` hanya memiliki item menu Metode Pembayaran. | Belum dibuat | Belum tersedia ringkasan pembayaran atau opsi QRIS, transfer, dan COD. | Integrasi pembayaran perlu ditambahkan sebagai tahap pengembangan tersendiri. |
| Riwayat Pesanan | `riwayat/RiwayatFragment.kt`, `riwayat/RiwayatAdapter.kt`, `riwayat/RiwayatModel.kt`, `res/layout/fragment_riwayat.xml`, `res/layout/item_riwayat.xml` | Berbeda | Daftar kartu pesanan dapat dibuka untuk melihat dialog detail dan memakai sumber JSON lokal. Tab Semua/Aktif/Selesai serta tombol Pesan Ulang belum disediakan. | Implementasi saat ini berfokus pada penerapan ListView dan CustomAdapter serta pemuatan data lokal. |

## Fitur tambahan di luar desain awal

- Registrasi pengguna dan verifikasi OTP simulasi.
- Date picker, time picker, validasi jadwal, dan notifikasi lokal.
- Onboarding tiga halaman berbasis ViewPager2 dengan indikator titik; onboarding dapat dibuka kembali dari Bantuan di Profil.
- Grid layanan Beranda memakai RecyclerView dan `LayananAdapter`.
- Daftar Riwayat memakai ListView dan `RiwayatAdapter` berbasis BaseAdapter.
- Sesi login, data akun, preferensi, dan pesanan disimpan secara lokal melalui SharedPreferences.

## Rencana pengembangan

1. Membuat layar **Lacak Pesanan** dengan timeline status dan informasi kurir, menggunakan sumber status yang konsisten.
2. Membuat layar **Pembayaran** dengan ringkasan pesanan serta opsi QRIS, transfer, dan COD; integrasi transaksi dapat ditambahkan setelah backend tersedia.
