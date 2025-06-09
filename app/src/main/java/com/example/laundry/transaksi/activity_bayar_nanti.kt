package com.example.laundry.transaksi

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.laundry.R
import com.example.laundry.adapter.adapter_Konfirmasi_data
import com.example.laundry.modeldata.ModelTambah

class activity_bayar_nanti : AppCompatActivity() {

    private lateinit var tvIdTransaksi: TextView
    private lateinit var tvTanggal: TextView
    private lateinit var tvNamaPelanggan: TextView
    private lateinit var tvNamaPegawai: TextView
    private lateinit var tvNamaLayanan: TextView
    private lateinit var tvHargaLayanan: TextView
    private lateinit var tvSubtotalTambahan: TextView
    private lateinit var tvTotalBayar: TextView
    private lateinit var rvTambahan: RecyclerView

    private lateinit var tambahanAdapter: adapter_Konfirmasi_data
    private var listTambahan: ArrayList<ModelTambah> = arrayListOf()
    private var subtotalTambahan = 0
    private var hargaLayanan = 0
    private var totalBayar = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bayar_nanti)

        // Inisialisasi view
        tvIdTransaksi = findViewById(R.id.TV_IDTransaksi)
        tvTanggal = findViewById(R.id.TV_Tgl_terdaftar)
        tvNamaPelanggan = findViewById(R.id.TV_namapelanggan)
        tvNamaPegawai = findViewById(R.id.TV_namapegawai)
        tvNamaLayanan = findViewById(R.id.TV_NamaLayanan)      // ditambahkan
        tvHargaLayanan = findViewById(R.id.TV_Hargalayanan)
        tvSubtotalTambahan = findViewById(R.id.subtotal)
        tvTotalBayar = findViewById(R.id.subtotalharga)
        rvTambahan = findViewById(R.id.rv_data_Tambahan)

        // Ambil data dari intent
        val idTransaksi = intent.getStringExtra("idTransaksi") ?: "-"
        val tanggal = intent.getStringExtra("tanggal") ?: "-"
        val namaPelanggan = intent.getStringExtra("namaPelanggan") ?: "-"
        val namaPegawai = intent.getStringExtra("namaPegawai") ?: "-"
        val namaLayanan = intent.getStringExtra("namaLayanan") ?: "-"  // ditambahkan
        hargaLayanan = intent.getIntExtra("hargaLayanan", 0)
        listTambahan = intent.getParcelableArrayListExtra<ModelTambah>("listTambahan") ?: arrayListOf()

        // Tampilkan data
        tvIdTransaksi.text = idTransaksi
        tvTanggal.text = tanggal
        tvNamaPelanggan.text = namaPelanggan
        tvNamaPegawai.text = namaPegawai
        tvNamaLayanan.text = namaLayanan // set teks layanan
        tvHargaLayanan.text = formatRupiah(hargaLayanan)

        // Hitung subtotal tambahan
        subtotalTambahan = listTambahan.sumOf {
            it.harga_tambahan
                ?.replace(".", "")
                ?.replace(",", "")
                ?.trim()
                ?.toIntOrNull() ?: 0
        }

        tvSubtotalTambahan.text = formatRupiah(subtotalTambahan)

        // Hitung total bayar
        totalBayar = hargaLayanan + subtotalTambahan
        tvTotalBayar.text = formatRupiah(totalBayar)

        // Setup adapter tambahan
        tambahanAdapter = adapter_Konfirmasi_data(listTambahan)
        rvTambahan.layoutManager = LinearLayoutManager(this)
        rvTambahan.adapter = tambahanAdapter
    }

    private fun formatRupiah(number: Int): String {
        return "Rp%,d".format(number).replace(",", ".")
    }
}
