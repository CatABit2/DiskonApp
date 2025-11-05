import java.awt.EventQueue;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ItemEvent;

public class DiscountCalculatorFrame extends javax.swing.JFrame {

    // Formatter uang sederhana
    private final DecimalFormat df = new DecimalFormat("#,##0.00");

    public DiscountCalculatorFrame() {
        initComponents();
        setTitle("Aplikasi Perhitungan Diskon");
        setLocationRelativeTo(null);

        // Siapkan combo diskon
        cbDiskon.setModel(new DefaultComboBoxModel<>(
                new String[]{"0%", "5%", "10%", "15%", "20%", "25%", "50%"}));

        // Siapkan slider (0–50, step 5)
        sliderDiskon.setMinimum(0);
        sliderDiskon.setMaximum(50);
        sliderDiskon.setMajorTickSpacing(5);
        sliderDiskon.setPaintTicks(true);
        sliderDiskon.setPaintLabels(true);

        // Output non-editable
        txtHargaAkhir.setEditable(false);
        txtPenghematan.setEditable(false);
        txtRiwayat.setEditable(false);

        // Sinkronisasi combo -> slider
        cbDiskon.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int persen = parsePersen((String) cbDiskon.getSelectedItem());
                sliderDiskon.setValue(persen);
            }
        });

        // Sinkronisasi slider -> combo
        sliderDiskon.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int val = sliderDiskon.getValue();
                // bulatkan ke kelipatan 5 biar konsisten dengan combo
                int rounded = Math.round(val / 5f) * 5;
                if (rounded != val) sliderDiskon.setValue(rounded);
                cbDiskon.setSelectedItem(rounded + "%");
            }
        });

        // Tombol Hitung
        btnHitung.addActionListener(e -> hitung());

        // Tombol Bersihkan
        btnBersihkan.addActionListener(e -> bersihkan());
    }

    // Parse "15%" -> 15
    private int parsePersen(String s) {
        try {
            return Integer.parseInt(s.replace("%", "").trim());
        } catch (Exception ex) {
            return 0;
        }
    }

    private double parseHarga() throws NumberFormatException {
        String teks = txtHargaAsli.getText().trim().replace(",", "");
        if (teks.isEmpty()) throw new NumberFormatException("Kosong");
        double val = Double.parseDouble(teks);
        if (val < 0) throw new NumberFormatException("Negatif");
        return val;
    }

    // Kupon sederhana: HEMAT10 = +10%, HEMAT5 = +5%
    private int kuponBonus(String kode) {
        if (kode == null) return 0;
        String k = kode.trim().toUpperCase();
        if (k.equals("HEMAT10")) return 10;
        if (k.equals("HEMAT5")) return 5;
        return 0;
    }

    private void hitung() {
        try {
            double hargaAsli = parseHarga();

            // Ambil persen dari slider (prioritas variasi)
            int persenCombo = parsePersen((String) cbDiskon.getSelectedItem());
            int persenSlider = sliderDiskon.getValue();
            int persen = persenSlider > 0 ? persenSlider : persenCombo;

            // Kupon
            int bonus = kuponBonus(txtKupon.getText());
            int totalPersen = Math.min(90, persen + bonus); // batasi 90% biar nggak absurd

            double penghematan = hargaAsli * totalPersen / 100.0;
            double hargaAkhir = hargaAsli - penghematan;

            txtPenghematan.setText(df.format(penghematan));
            txtHargaAkhir.setText(df.format(hargaAkhir));

            // Riwayat
            String baris = String.format(
                    "Harga: %s | Diskon: %d%% (kupon +%d%%) | Hemat: %s | Akhir: %s",
                    df.format(hargaAsli), persen, bonus, df.format(penghematan), df.format(hargaAkhir));
            txtRiwayat.append(baris + "\n");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Input harga tidak valid. Masukkan angka positif.\nDetail: " + ex.getMessage(),
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            txtHargaAsli.requestFocus();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Terjadi kesalahan tak terduga: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void bersihkan() {
        txtHargaAsli.setText("");
        cbDiskon.setSelectedIndex(0);
        sliderDiskon.setValue(0);
        txtKupon.setText("");
        txtHargaAkhir.setText("");
        txtPenghematan.setText("");
        // Riwayat sengaja tidak dihapus agar jejak perhitungan tetap ada
        txtHargaAsli.requestFocus();
    }

    // ---------- KODE AUTO-GENERATED NETBEANS ----------
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtHargaAsli = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbDiskon = new javax.swing.JComboBox<>();
        sliderDiskon = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        txtKupon = new javax.swing.JTextField();
        btnHitung = new javax.swing.JButton();
        btnBersihkan = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtHargaAkhir = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPenghematan = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtRiwayat = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Harga Asli");

        txtHargaAsli.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Diskon");

        cbDiskon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0%", "5%", "10%", "15%", "20%", "25%", "50%" }));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Kode Kupon");

        txtKupon.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnHitung.setText("Hitung");

        btnBersihkan.setText("Bersihkan");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Harga Akhir");

        txtHargaAkhir.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Hemat");

        txtPenghematan.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtRiwayat.setColumns(20);
        txtRiwayat.setRows(5);
        jScrollPane1.setViewportView(txtRiwayat);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPenghematan))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHargaAkhir))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHargaAsli, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnHitung, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtKupon)
                                .addComponent(cbDiskon, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnBersihkan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sliderDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                .addContainerGap(158, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtHargaAsli, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(sliderDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKupon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnHitung, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBersihkan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtHargaAkhir, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPenghematan, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addContainerGap(84, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
 public static void main(String[] args) {
        EventQueue.invokeLater(() -> new DiscountCalculatorFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBersihkan;
    private javax.swing.JButton btnHitung;
    private javax.swing.JComboBox<String> cbDiskon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider sliderDiskon;
    private javax.swing.JTextField txtHargaAkhir;
    private javax.swing.JTextField txtHargaAsli;
    private javax.swing.JTextField txtKupon;
    private javax.swing.JTextField txtPenghematan;
    private javax.swing.JTextArea txtRiwayat;
    // End of variables declaration//GEN-END:variables
}
