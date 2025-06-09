package com.example.laundry.pelanggan

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase
import com.example.laundry.R
import com.example.laundry.modeldata.ModelPelanggan
import java.text.SimpleDateFormat
import java.util.*

class TambahPelanggan : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("pelanggan")

    private lateinit var tv1_TambahPelanggan: TextView
    private lateinit var ET1_NamaLengkap: EditText
    private lateinit var ET2_Alamat: EditText
    private lateinit var ET3_NoHP: EditText
    private lateinit var ET4_NamaCabang: EditText
    private lateinit var btn_Simpan: Button

    private var idPelanggan: String = ""
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_pelanggan)

        initViews()
        getData()

        btn_Simpan.setOnClickListener {
            cekValidasi()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        tv1_TambahPelanggan = findViewById(R.id.tv1_TambahPelanggan)
        ET1_NamaLengkap = findViewById(R.id.ET1_NamaLengkap)
        ET2_Alamat = findViewById(R.id.ET2_Alamat)
        ET3_NoHP = findViewById(R.id.ET3_NoHP)
        ET4_NamaCabang = findViewById(R.id.ET4_NamaCabang)
        btn_Simpan = findViewById(R.id.btn_Simpan)
    }

    private fun getData() {
        idPelanggan = intent.getStringExtra("idPelanggan") ?: ""
        val nama = intent.getStringExtra("namaPelanggan")
        val alamat = intent.getStringExtra("alamatPelanggan")
        val hp = intent.getStringExtra("noHpPelanggan")
        val cabang = intent.getStringExtra("cabangPelanggan")

        if (idPelanggan.isNotEmpty()) {
            isEditMode = true
            tv1_TambahPelanggan.text = "Edit Pelanggan"
            ET1_NamaLengkap.setText(nama)
            ET2_Alamat.setText(alamat)
            ET3_NoHP.setText(hp)
            ET4_NamaCabang.setText(cabang)
            btn_Simpan.text = "Sunting"
        } else {
            isEditMode = false
            tv1_TambahPelanggan.text = getString(R.string.tv1_TambahPelanggan)
            btn_Simpan.text = "Simpan"
        }
    }

    private fun cekValidasi() {
        val nama = ET1_NamaLengkap.text.toString()
        val alamat = ET2_Alamat.text.toString()
        val noHP = ET3_NoHP.text.toString()
        val cabang = ET4_NamaCabang.text.toString()

        if (nama.isEmpty()) {
            ET1_NamaLengkap.error = getString(R.string.validasi_nama_pelanggan)
            ET1_NamaLengkap.requestFocus()
            return
        }
        if (alamat.isEmpty()) {
            ET2_Alamat.error = getString(R.string.validasi_alamat_pelanggan)
            ET2_Alamat.requestFocus()
            return
        }
        if (noHP.isEmpty()) {
            ET3_NoHP.error = getString(R.string.validasi_noHP_pelanggan)
            ET3_NoHP.requestFocus()
            return
        }
        if (cabang.isEmpty()) {
            ET4_NamaCabang.error = getString(R.string.validasi_Cabang_pelanggan)
            ET4_NamaCabang.requestFocus()
            return
        }

        if (isEditMode) {
            update()
        } else {
            simpan()
        }
    }

    private fun simpan() {
        myRef.get().addOnSuccessListener { snapshot ->
            val count = snapshot.childrenCount.toInt() + 1
            val pelangganId = "PLG" + String.format("%06d", count)
            val waktuDaftar = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

            val data = ModelPelanggan(
                tvCARD_PELANGGAN_ID = pelangganId,
                tvCARD_PELANGGAN_Nama = ET1_NamaLengkap.text.toString(),
                tvCARD_PELANGGAN_Almt = ET2_Alamat.text.toString(),
                tvCARD_PELANGGAN_NoHP = ET3_NoHP.text.toString(),
                tvCARD_PELANGGAN_Cabang = ET4_NamaCabang.text.toString(),
                tvCARD_PELANGGAN_daftar = waktuDaftar
            )

            myRef.child(pelangganId).setValue(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data pelanggan berhasil disimpan", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                }

        }.addOnFailureListener {
            Toast.makeText(this, "Gagal mengambil jumlah pelanggan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun update() {
        val pelangganRef = database.getReference("pelanggan").child(idPelanggan)
        val data = mapOf(
            "tvCARD_PELANGGAN_Nama" to ET1_NamaLengkap.text.toString(),
            "tvCARD_PELANGGAN_Almt" to ET2_Alamat.text.toString(),
            "tvCARD_PELANGGAN_NoHP" to ET3_NoHP.text.toString(),
            "tvCARD_PELANGGAN_Cabang" to ET4_NamaCabang.text.toString()
        )

        pelangganRef.updateChildren(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Data pelanggan berhasil diperbarui", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show()
            }
    }
}