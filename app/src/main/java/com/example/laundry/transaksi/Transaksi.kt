package com.example.laundry.transaksi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.laundry.R
import com.example.laundry.adapter.adapter_tambah_pilihan
import com.example.laundry.modeldata.ModelTambah
import java.text.NumberFormat
import java.util.*

class Transaksi : AppCompatActivity() {

    private lateinit var btnPilihPelanggan: Button
    private lateinit var btnPilihLayanan: Button
    private lateinit var btnTambahan: Button
    private lateinit var btnProses: Button

    private lateinit var tvIsi1: TextView
    private lateinit var tvIsi2: TextView
    private lateinit var tvIsi3: TextView
    private lateinit var tvIsi4: TextView

    private lateinit var rvTambahan: RecyclerView
    private lateinit var adapterTambahan: adapter_tambah_pilihan
    private val listTambahan = mutableListOf<ModelTambah>()

    private var namaPelanggan: String = "-"
    private var noHpPelanggan: String = "-"
    private var namaLayanan: String = "-"
    private var hargaLayanan: String = "-"

    companion object {
        const val REQUEST_PILIH_PELANGGAN = 1
        const val REQUEST_PILIH_LAYANAN = 2
        const val REQUEST_PILIH_TAMBAHAN = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi)

        btnPilihPelanggan = findViewById(R.id.btnPilihPelanggan)
        btnPilihLayanan = findViewById(R.id.btnPilihLayanan)
        btnTambahan = findViewById(R.id.btnTambahan)
        btnProses = findViewById(R.id.btnProses)

        tvIsi1 = findViewById(R.id.TvIsi1)
        tvIsi2 = findViewById(R.id.TvIsi2)
        tvIsi3 = findViewById(R.id.TvIsi3)
        tvIsi4 = findViewById(R.id.TvIsi4)

        rvTambahan = findViewById(R.id.rvItem_Tambahan)
        rvTambahan.layoutManager = LinearLayoutManager(this)
        adapterTambahan = adapter_tambah_pilihan(listTambahan) {
            listTambahan.remove(it)
            adapterTambahan.notifyDataSetChanged()
        }
        rvTambahan.adapter = adapterTambahan

        btnPilihPelanggan.setOnClickListener {
            startActivityForResult(Intent(this, activity_pilih_pelanggan::class.java), REQUEST_PILIH_PELANGGAN)
        }

        btnPilihLayanan.setOnClickListener {
            startActivityForResult(Intent(this, activity_pilih_layanan::class.java), REQUEST_PILIH_LAYANAN)
        }

        btnTambahan.setOnClickListener {
            startActivityForResult(Intent(this, activity_pilih_tambahan::class.java), REQUEST_PILIH_TAMBAHAN)
        }

        btnProses.setOnClickListener {
            when {
                namaPelanggan == "-" || noHpPelanggan == "-" -> {
                    Toast.makeText(this, "Silakan pilih pelanggan terlebih dahulu.", Toast.LENGTH_SHORT).show()
                }
                namaLayanan == "-" || hargaLayanan == "-" -> {
                    Toast.makeText(this, "Silakan pilih layanan terlebih dahulu.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val intent = Intent(this, KonfirmasiData::class.java).apply {
                        putExtra("NAMA_PELANGGAN", namaPelanggan)
                        putExtra("NOHP", noHpPelanggan)
                        putExtra("NAMA_LAYANAN", namaLayanan)
                        putExtra("HARGA_LAYANAN", hargaLayanan)
                        putParcelableArrayListExtra("TAMBAHAN_LIST", ArrayList(listTambahan))
                    }
                    startActivity(intent)
                }
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                REQUEST_PILIH_PELANGGAN -> {
                    namaPelanggan = data.getStringExtra("NAMA") ?: "-"
                    noHpPelanggan = data.getStringExtra("NOHP") ?: "-"
                    tvIsi1.text = "Nama Pelanggan: $namaPelanggan"
                    tvIsi2.text = "No HP: $noHpPelanggan"
                }
                REQUEST_PILIH_LAYANAN -> {
                    namaLayanan = data.getStringExtra("NAMA_LAYANAN") ?: "-"
                    hargaLayanan = data.getStringExtra("HARGA") ?: "-"
                    tvIsi3.text = "Nama Layanan: $namaLayanan"
                    tvIsi4.text = "Harga: ${formatHarga(hargaLayanan)}"
                }
                REQUEST_PILIH_TAMBAHAN -> {
                    val id = data.getStringExtra("idTambahan") ?: "-"
                    val nama = data.getStringExtra("nama") ?: "-"
                    val harga = data.getStringExtra("harga") ?: "-"

                    if (listTambahan.any { it.id_tambahan == id }) {
                        Toast.makeText(this, "Layanan tambahan sudah dipilih", Toast.LENGTH_SHORT).show()
                        return
                    }

                    val tambahan = ModelTambah(id, nama, harga, "")
                    listTambahan.add(tambahan)
                    adapterTambahan.notifyDataSetChanged()
                }
            }
        }
    }

    private fun formatHarga(harga: String): String {
        return try {
            val angka = harga.replace(Regex("[^\\d]"), "").toInt()
            NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(angka)
        } catch (e: Exception) {
            harga
        }
    }
}
