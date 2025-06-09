package com.example.laundry.pegawai

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
import com.example.laundry.modeldata.ModelPegawai
import java.text.SimpleDateFormat
import java.util.*

class TambahPegawai : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("pegawai")

    private lateinit var tv1_TambahPegawai: TextView
    private lateinit var ET1_NamaLengkap_Pegawai: EditText
    private lateinit var ET2_Alamat: EditText
    private lateinit var ET3_NoHP: EditText
    private lateinit var ET4_NamaCabang: EditText
    private lateinit var btn_Simpan: Button

    private var idPegawai: String = ""
    private var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_pegawai)

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
        tv1_TambahPegawai = findViewById(R.id.tv1_TambahPegawai)
        ET1_NamaLengkap_Pegawai = findViewById(R.id.ET1_NamaLengkap_Pegawai)
        ET2_Alamat = findViewById(R.id.ET2_Alamat)
        ET3_NoHP = findViewById(R.id.ET3_NoHP)
        ET4_NamaCabang = findViewById(R.id.ET4_NamaCabang)
        btn_Simpan = findViewById(R.id.btn_Simpan)
    }

    private fun getData() {
        idPegawai = intent.getStringExtra("idPegawai") ?: ""
        val nama = intent.getStringExtra("namaPegawai")
        val alamat = intent.getStringExtra("alamatPegawai")
        val hp = intent.getStringExtra("noHpPegawai")
        val cabang = intent.getStringExtra("idCabangPegawai")

        if (idPegawai.isNotEmpty()) {
            isEditMode = true
            tv1_TambahPegawai.text = "Edit Pegawai"
            ET1_NamaLengkap_Pegawai.setText(nama)
            ET2_Alamat.setText(alamat)
            ET3_NoHP.setText(hp)
            ET4_NamaCabang.setText(cabang)
            btn_Simpan.text = "Sunting"
        } else {
            isEditMode = false
            tv1_TambahPegawai.text = getString(R.string.tv1_TambahPegawai)
            btn_Simpan.text = "Simpan"
        }
    }

    private fun cekValidasi() {
        val nama = ET1_NamaLengkap_Pegawai.text.toString()
        val alamat = ET2_Alamat.text.toString()
        val noHP = ET3_NoHP.text.toString()
        val cabang = ET4_NamaCabang.text.toString()

        if (nama.isEmpty()) {
            ET1_NamaLengkap_Pegawai.error = getString(R.string.validasi_nama_pegawai)
            ET1_NamaLengkap_Pegawai.requestFocus()
            return
        }
        if (alamat.isEmpty()) {
            ET2_Alamat.error = getString(R.string.validasi_alamat_pegawai)
            ET2_Alamat.requestFocus()
            return
        }
        if (noHP.isEmpty()) {
            ET3_NoHP.error = getString(R.string.validasi_noHP_pegawai)
            ET3_NoHP.requestFocus()
            return
        }
        if (cabang.isEmpty()) {
            ET4_NamaCabang.error = getString(R.string.validasi_Cabang_pegawai)
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
            val pegawaiId = "PGW" + String.format("%03d", count)
            val waktuDaftar = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

            val data = ModelPegawai(
                idPegawai = pegawaiId,
                namaPegawai = ET1_NamaLengkap_Pegawai.text.toString(),
                terdaftar = waktuDaftar,
                alamatPegawai = ET2_Alamat.text.toString(),
                noHpPegawai = ET3_NoHP.text.toString(),
                idCabangPegawai = ET4_NamaCabang.text.toString()
            )

            myRef.child(pegawaiId).setValue(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "sukses_simpan_pegawai", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "gagal_simpan_pegawai", Toast.LENGTH_SHORT).show()
                }
        }.addOnFailureListener {
            Toast.makeText(this, "Gagal mengambil jumlah pegawai", Toast.LENGTH_SHORT).show()
        }
    }


    private fun update() {
        val pegawaiRef = database.getReference("pegawai").child(idPegawai)
        val data = mapOf(
            "namaPegawai" to ET1_NamaLengkap_Pegawai.text.toString(),
            "alamatPegawai" to ET2_Alamat.text.toString(),
            "noHpPegawai" to ET3_NoHP.text.toString(),
            "idCabangPegawai" to ET4_NamaCabang.text.toString()
        )

        pegawaiRef.updateChildren(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Data Pegawai Berhasil Diperbarui", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show()
            }
    }
}
