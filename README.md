# DiskonApp
Tugas 3 - M. Aldi Ripandi (221001610)

# Aplikasi Perhitungan Diskon (JFrame, NetBeans)

Aplikasi desktop sederhana berbasis **Java Swing (JFrame)** untuk menghitung harga akhir setelah diskon, dengan dukungan **pilihan diskon via JComboBox**, **JSlider**, **kode kupon**, dan **riwayat perhitungan**.

## Fitur
- Input **Harga Asli** (validasi angka dan non-negatif).
- Pilih **persentase diskon** melalui:
  - `JComboBox`: 0%, 5%, 10%, 15%, 20%, 25%, 50%  
  - `JSlider`: 0–50 dengan step 5 (sinkron dengan combo)
- **Kode Kupon** opsional:
  - `HEMAT10` tambahan 10%
  - `HEMAT5` tambahan 5%
- Tampilkan **Penghematan** dan **Harga Akhir** (non-editable).
- **Riwayat** perhitungan di `JTextArea`.
- Tombol **Hitung** dan **Bersihkan**.
- **Penanganan kesalahan** input (NumberFormatException) via `JOptionPane`.

## Teknologi
- Java 8+ (disarankan 11 atau 17)
- Swing (JFrame, JLabel, JTextField, JComboBox, JSlider, JButton, JTextArea, JScrollPane)
- NetBeans (versi apa pun yang mendukung GUI Builder)

## Struktur Proyek (contoh)
```
DiskonApp/
├─ src/
│  └─ DiscountCalculatorFrame.java
├─ README.md
└─ build/ (tergenerate)
```

## Menjalankan di NetBeans
1. **File → New Project** → Java Application.
2. Buat **JFrame Form**: `DiscountCalculatorFrame` pada package `com.aldi.diskon`.
3. **Design UI** (drag & drop):
   - `JLabel` Harga Asli, Diskon, Kode Kupon, Penghematan, Harga Akhir
   - `JTextField` `txtHargaAsli`, `txtKupon`, `txtPenghematan` (non-editable), `txtHargaAkhir` (non-editable)
   - `JComboBox<String>` `cbDiskon`
   - `JSlider` `sliderDiskon` (0..50, majorTickSpacing=5, paintTicks=true, paintLabels=true)
   - `JButton` `btnHitung`, `btnBersihkan`
   - `JTextArea` `txtRiwayat` di dalam `JScrollPane` (non-editable)
4. **Event**:
   - `btnHitung.actionPerformed` → `hitung()`
   - `btnBersihkan.actionPerformed` → `bersihkan()`
   - `cbDiskon.itemStateChanged` → sinkron ke slider
   - `sliderDiskon.changeListener` → sinkron ke combo
5. **Run File** pada `DiscountCalculatorFrame.java`.

> Catatan: Jika dosen mensyaratkan `JPanel`, tempatkan semua komponen di sebuah panel lalu set sebagai konten utama frame.

## Logika Perhitungan
- Validasi harga: wajib angka positif.
- Persentase diskon diambil dari **slider** jika > 0; jika 0 maka pakai **combo**.
- Kupon (`HEMAT10`/`HEMAT5`) menambah persen diskon. Disarankan **batas 90%** maksimum untuk menghindari hasil tidak masuk akal.
- Rumus:
  - `penghematan = hargaAsli * (totalPersen / 100)`
  - `hargaAkhir = hargaAsli - penghematan`

## Contoh Kode (ekstrak)
```java
int persenCombo = parsePersen((String) cbDiskon.getSelectedItem());
int persenSlider = sliderDiskon.getValue();
int persen = persenSlider > 0 ? persenSlider : persenCombo;

int bonus = kuponBonus(txtKupon.getText()); // HEMAT10=10, HEMAT5=5, selain itu 0
int totalPersen = Math.min(90, persen + bonus);

double penghematan = hargaAsli * totalPersen / 100.0;
double hargaAkhir = hargaAsli - penghematan;
```

## Penanganan Kesalahan
- **Input kosong/bukan angka**: tampilkan dialog error dan fokus kembali ke `txtHargaAsli`.
- **Angka negatif**: tolak dengan pesan yang jelas.
- Gunakan `try/catch NumberFormatException` di `hitung()`.

## Panduan Penggunaan
1. Isi **Harga Asli** (misal `2500000`).
2. Pilih diskon via **Combo** atau **Slider**.
3. (Opsional) Isi **Kode Kupon** (`HEMAT10` atau `HEMAT5`).
4. Klik **Hitung** → hasil tampil di **Penghematan** dan **Harga Akhir**, serta tercatat di **Riwayat**.
5. Klik **Bersihkan** untuk reset input/output (riwayat dibiarkan agar jejak perhitungan tetap ada).

## Kriteria Penilaian (Pemetaan Singkat)
- **Komponen GUI**: JFrame, JLabel, JTextField, JComboBox, JButton, JSlider, JTextArea ✔
- **Logika Program**: perhitungan aritmetika + validasi & eksepsi ✔
- **Events**: ActionListener, ItemListener, ChangeListener ✔
- **Kesesuaian UI**: label jelas, output non-editable, layout rapi ✔
- **Variasi**: kupon, slider alternatif, riwayat perhitungan ✔

## Tips Kualitas
- Format angka dengan `DecimalFormat` (`#,##0.00`).
- Konsisten huruf besar kecil pada kode kupon (pakai `.toUpperCase()`).
- Tambahkan `ToolTipText` pada input untuk membantu pengguna.
- Pertimbangkan **dark mode** via UIManager LookAndFeel (opsional).

## Lisensi
Proyek tugas pembelajaran. Gunakan seperlunya untuk keperluan akademik.
