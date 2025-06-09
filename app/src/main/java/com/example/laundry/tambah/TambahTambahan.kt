package com.example.laundry.tambah

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.laundry.modeldata.ModelTambah
import com.example.laundry.R

class TambahTambahan : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etHarga: EditText
    private lateinit var etCabang: EditText
    private lateinit var btnSimpan: Button
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_tambahan)

        etNama = findViewById(R.id.ET1_NamaTambahan)
        etHarga = findViewById(R.id.ET2_HargaTambahan)
        etCabang = findViewById(R.id.ET3_CabangTambahan)
        btnSimpan = findViewById(R.id.btn_Simpan)

        dbRef = FirebaseDatabase.getInstance().getReference("DataTambah")

        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val harga = etHarga.text.toString().trim()
            val cabang = etCabang.text.toString().trim()

            if (nama.isEmpty() || harga.isEmpty() || cabang.isEmpty()) {
                Toast.makeText(this, "Semua data harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = dbRef.push().key ?: return@setOnClickListener
            val data = ModelTambah(id, nama, harga, cabang)

            dbRef.child(id).setValue(data).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Gagal menambahkan data", Toast.LENGTH_SHORT).show()
                }
            }
        }

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}






















