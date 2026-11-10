# Kilau Laundry

Kilau adalah aplikasi Android untuk membuat pesanan laundry antar-jemput. Aplikasi dibuat dengan Kotlin, ViewBinding, Material Components, dan penyimpanan lokal SharedPreferences.

## Fitur

- Splash screen dan onboarding tiga halaman.
- Registrasi, verifikasi OTP simulasi, login, dan sesi pengguna lokal.
- Beranda dengan empat layanan dalam grid RecyclerView, promo, pesanan aktif, dan tips.
- Pemesanan dengan pilihan layanan, jumlah, alamat, date picker, time picker, catatan, estimasi total, serta notifikasi lokal.
- Riwayat pesanan dengan ListView dan BaseAdapter, data JSON di SharedPreferences, serta dialog detail.
- Profil, pengaturan notifikasi, bantuan untuk memutar ulang onboarding, dan logout.

## Cara menjalankan

1. Buka project ini di Android Studio.
2. Pastikan Android SDK untuk `compileSdk` project terpasang dan lakukan Gradle Sync.
3. Jalankan konfigurasi aplikasi pada emulator atau perangkat Android.
4. Pada instalasi baru, ikuti onboarding. Untuk akun demo, username dan kata sandi dapat diisi dengan nilai yang sama; akun hasil registrasi juga dapat digunakan.

## Daftar halaman

| Halaman | Implementasi |
|---|---|
| Splash | `SplashScreenActivity` |
| Onboarding | `OnboardingActivity` dengan `ViewPager2` |
| Masuk / Registrasi / Verifikasi | `AuthActivity`, `RegisterActivity`, `OtpActivity` |
| Beranda | `BaseActivity` dan `HomeFragment` |
| Buat pesanan | `OrderActivity` |
| Riwayat pesanan | `RiwayatFragment` |
| Profil | `ProfilFragment` |
| Tips merawat pakaian | `WebViewActivity` |

## GitHub

Repository Git sudah tersedia pada project ini. Setelah membuat repository kosong di GitHub, gunakan URL repository milik Anda:

```bash
git remote add origin https://github.com/USERNAME/NAMA-REPOSITORY.git
git branch -M main
git push -u origin main
```

Jangan tambahkan `local.properties`, file keystore, atau kredensial ke repository.
