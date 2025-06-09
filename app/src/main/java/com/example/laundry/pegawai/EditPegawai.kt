package com.example.laundry.pegawai

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.example.laundry.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditPegawai : AppCompatActivity() {

    private lateinit var namaPegawai: EditText
    private lateinit var alamatPegawai: EditText
    private lateinit var noHpPegawai: EditText
    private lateinit var idCabangPegawai: EditText
    private lateinit var btnSunting: Button

    private var isEditMode = false
    private var idPegawai: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pegawai)

        namaPegawai = findViewById(R.id.etEditNama)
        alamatPegawai = findViewById(R.id.etEditAlamat)
        noHpPegawai = findViewById(R.id.etEditNoHP)
        idCabangPegawai = findViewById(R.id.etEditCabang)
        btnSunting = findViewById(R.id.btnSunting)

        // Ambil data dari intent
        idPegawai = intent.getStringExtra("idPegawai")
        val nama = intent.getStringExtra("namaPegawai")
        val alamat = intent.getStringExtra("alamatPegawai")
        val noHP = intent.getStringExtra("noHpPegawai")
        val cabang = intent.getStringExtra("idCabangPegawai")

        // Set data ke EditText
        namaPegawai.setText(nama)
        alamatPegawai.setText(alamat)
        noHpPegawai.setText(noHP)
        idCabangPegawai.setText(cabang)

        // Nonaktifkan input
        setEditTextsEnabled(false)

        btnSunting.setOnClickListener {
            if (!isEditMode) {
                setEditTextsEnabled(true)
                btnSunting.text = "Simpan"
                isEditMode = true
            } else {
                val namaInput = namaPegawai.text.toString().trim()
                val alamatInput = alamatPegawai.text.toString().trim()
                val noHPInput = noHpPegawai.text.toString().trim()
                val cabangInput = idCabangPegawai.text.toString().trim()

                // Validasi input
                if (namaInput.isEmpty() || alamatInput.isEmpty() || noHPInput.isEmpty() || cabangInput.isEmpty()) {
                    if (namaInput.isEmpty()) namaPegawai.error = "Harus diisi"
                    if (alamatInput.isEmpty()) alamatPegawai.error = "Harus diisi"
                    if (noHPInput.isEmpty()) noHpPegawai.error = "Harus diisi"
                    if (cabangInput.isEmpty()) idCabangPegawai.error = "Harus diisi"
                    return@setOnClickListener
                }

                if (!noHPInput.all { it.isDigit() }) {
                    noHpPegawai.error = "Hanya boleh angka"
                    return@setOnClickListener
                }

                val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                val pegawaiId = idPegawai ?: return@setOnClickListener

                val database = FirebaseDatabase.getInstance().getReference("pegawai")

                // ✅ Perbaikan utama: tambahkan "idPegawai"
                val pegawaiData = mapOf(
                    "idPegawai" to pegawaiId,
                    "namaPegawai" to namaInput,
                    "alamatPegawai" to alamatInput,
                    "noHpPegawai" to noHPInput,
                    "idCabangPegawai" to cabangInput,
                    "terdaftar" to currentTime
                )

                database.child(pegawaiId).updateChildren(pegawaiData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Data pegawai berhasil diperbarui", Toast.LENGTH_SHORT).show()
                        setEditTextsEnabled(false)
                        btnSunting.text = "Edit"
                        isEditMode = false
                        finish() // ✅ agar langsung kembali ke daftar dan refresh otomatis
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun setEditTextsEnabled(enabled: Boolean) {
        namaPegawai.isEnabled = enabled
        alamatPegawai.isEnabled = enabled
        noHpPegawai.isEnabled = enabled
        idCabangPegawai.isEnabled = enabled
    }
}
