package com.example.laundry.layanan

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.laundry.R
import com.google.firebase.database.FirebaseDatabase
import com.example.laundry.modeldata.ModelLayanan

class TambahLayanan : AppCompatActivity() {
    val  database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("layanan")
    lateinit var tvTitle: TextView
    lateinit var etgaris1: EditText
    lateinit var etgaris2: EditText
    lateinit var etgaris3: EditText
    lateinit var btsimpan: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_layanan)
        init()
        btsimpan.setOnClickListener {
            cekValidasi()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun  init(){
        tvTitle = findViewById(R.id.tv1_TambahLayanan)
        etgaris1 = findViewById(R.id.ET1_NamaLayanan)
        etgaris2 = findViewById(R.id.ET2_Harga)
        etgaris3 = findViewById(R.id.ET4_NamaCabang_Layanan)
        btsimpan = findViewById(R.id.btn_Simpan_Layanan)
    }
    fun  cekValidasi() {
        val nama = etgaris1.text.toString()
        val harga = etgaris2.text.toString()
        val namacabang = etgaris3.text.toString()

        //validasi data
        if (nama.isEmpty()) {
            etgaris1.error = "Nama Tidak Boleh Kosong"
            Toast.makeText(
                this,
                "Nama Tidak Boleh Kosong",
                Toast.LENGTH_SHORT
            ).show()
            etgaris1.requestFocus()
            return
        }
        if (harga.isEmpty()) {
            etgaris2.error = "Harga Tidak Boleh Kosong"
            Toast.makeText(
                this,
                "Harga Tidak Boleh Kosong",
                Toast.LENGTH_SHORT
            ).show()
            etgaris2.requestFocus()
            return
        }
        if (namacabang.isEmpty()) {
            etgaris3.error = "Cabang Tidak Boleh Kosong"
            Toast.makeText(
                this,
                "Cabang Tidak Boleh Kosong",
                Toast.LENGTH_SHORT
            ).show()
            etgaris3.requestFocus()
            return
        }
        simpan()
    }

    fun simpan(){
        val  layananBaru = myRef.push()
        val layananId = layananBaru.key
        val data = ModelLayanan(
            layananId.toString(),
            etgaris1.text.toString(),
            etgaris2.text.toString(),
            etgaris3.text.toString(),

            )
        layananBaru.setValue(data)
            .addOnSuccessListener {
                Toast.makeText(this,"sukses_simpan_layanan", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this,"gagal_simpan_layanan", Toast.LENGTH_SHORT).show()
            }
    }
}