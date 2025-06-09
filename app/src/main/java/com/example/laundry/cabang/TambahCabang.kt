package com.example.laundry.cabang

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase
import com.example.laundry.R
import com.example.laundry.modeldata.ModelCabang

class TambahCabang : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Cabang")

    lateinit var tvTitle: TextView
    lateinit var etNamaCabang: EditText
    lateinit var etAlamatCabang: EditText
    lateinit var etNomorTelepon: EditText
    lateinit var etLayanan: EditText
    lateinit var btnSimpan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_cabang)
        init()

        btnSimpan.setOnClickListener {
            cekValidasi()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun init() {
        tvTitle = findViewById(R.id.tv1_TambahCabang)
        etNamaCabang = findViewById(R.id.ET1_NamaLengkap)
        etAlamatCabang = findViewById(R.id.ET2_Alamat)
        etNomorTelepon = findViewById(R.id.ET3_NoHP)
        etLayanan = findViewById(R.id.ET4_Layanan_Cabang)
        btnSimpan = findViewById(R.id.btn_Simpan)
    }

    fun cekValidasi() {
        val nama = etNamaCabang.text.toString()
        val alamat = etAlamatCabang.text.toString()
        val telepon = etNomorTelepon.text.toString()
        val layanan = etLayanan.text.toString()

        if (nama.isEmpty()) {
            etNamaCabang.error = "Nama cabang tidak boleh kosong"
            etNamaCabang.requestFocus()
            return
        }

        if (alamat.isEmpty()) {
            etAlamatCabang.error = "Alamat cabang tidak boleh kosong"
            etAlamatCabang.requestFocus()
            return
        }

        if (telepon.isEmpty()) {
            etNomorTelepon.error = "Nomor telepon tidak boleh kosong"
            etNomorTelepon.requestFocus()
            return
        }

        if (layanan.isEmpty()) {
            etLayanan.error = "Layanan tidak boleh kosong"
            etLayanan.requestFocus()
            return
        }

        simpan(nama, alamat, telepon, layanan)
    }

    fun simpan(nama: String, alamat: String, telepon: String, layanan: String) {
        val cabangBaru = myRef.push()
        val cabangId = cabangBaru.key
        val data = ModelCabang(
            cabangId.toString(),
            nama,
            alamat,
            telepon,
            layanan
        )

        cabangBaru.setValue(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Data cabang berhasil disimpan", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal menyimpan data cabang", Toast.LENGTH_SHORT).show()
            }
    }
}
