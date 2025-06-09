package com.example.laundry.pelanggan

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.example.laundry.R
import java.text.SimpleDateFormat
import java.util.*

class EditPelanggan : AppCompatActivity() {

    private lateinit var namaPelanggan: EditText
    private lateinit var alamatPelanggan: EditText
    private lateinit var noHpPelanggan: EditText
    private lateinit var cabangPelanggan: EditText
    private lateinit var btnSunting: Button

    private var isEditMode = false
    private var idPelanggan: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pelanggan)

        namaPelanggan = findViewById(R.id.etEditNama)
        alamatPelanggan = findViewById(R.id.etEditAlamat)
        noHpPelanggan = findViewById(R.id.etEditNoHP)
        cabangPelanggan = findViewById(R.id.etEditCabang)
        btnSunting = findViewById(R.id.btnSunting)

        // Ambil data dari intent
        idPelanggan = intent.getStringExtra("tvCARD_PELANGGAN_ID")
        val nama = intent.getStringExtra("tvCARD_PELANGGAN_Nama")
        val alamat = intent.getStringExtra("tvCARD_PELANGGAN_Almt")
        val noHP = intent.getStringExtra("tvCARD_PELANGGAN_NoHP")
        val cabang = intent.getStringExtra("tvCARD_PELANGGAN_Cabang")

        namaPelanggan.setText(nama)
        alamatPelanggan.setText(alamat)
        noHpPelanggan.setText(noHP)
        cabangPelanggan.setText(cabang)

        setEditTextsEnabled(false)

        btnSunting.setOnClickListener {
            if (!isEditMode) {
                // Ubah ke mode edit
                setEditTextsEnabled(true)
                btnSunting.text = "Simpan"
                isEditMode = true
            } else {
                // Ambil data input
                val namaInput = namaPelanggan.text.toString().trim()
                val alamatInput = alamatPelanggan.text.toString().trim()
                val noHPInput = noHpPelanggan.text.toString().trim()
                val cabangInput = cabangPelanggan.text.toString().trim()

                if (namaInput.isEmpty()) namaPelanggan.error = "Harus diisi"
                if (alamatInput.isEmpty()) alamatPelanggan.error = "Harus diisi"
                if (noHPInput.isEmpty()) noHpPelanggan.error = "Harus diisi"
                if (cabangInput.isEmpty()) cabangPelanggan.error = "Harus diisi"
                if (namaInput.isEmpty() || alamatInput.isEmpty() || noHPInput.isEmpty() || cabangInput.isEmpty()) return@setOnClickListener

                if (!noHPInput.all { it.isDigit() }) {
                    noHpPelanggan.error = "Hanya boleh angka"
                    return@setOnClickListener
                }

                val id = idPelanggan ?: return@setOnClickListener
                val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

                val pelangganRef = FirebaseDatabase.getInstance().getReference("pelanggan").child(id)

                val updatedData = mapOf(
                    "tvCARD_PELANGGAN_ID" to id,
                    "tvCARD_PELANGGAN_Nama" to namaInput,
                    "tvCARD_PELANGGAN_Almt" to alamatInput,
                    "tvCARD_PELANGGAN_NoHP" to noHPInput,
                    "tvCARD_PELANGGAN_Cabang" to cabangInput,
                    "tvCARD_PELANGGAN_daftar" to currentTime
                )

                pelangganRef.updateChildren(updatedData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Data pelanggan berhasil diperbarui", Toast.LENGTH_SHORT).show()
                        setEditTextsEnabled(false)
                        btnSunting.text = "Edit"
                        isEditMode = false
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun setEditTextsEnabled(enabled: Boolean) {
        namaPelanggan.isEnabled = enabled
        alamatPelanggan.isEnabled = enabled
        noHpPelanggan.isEnabled = enabled
        cabangPelanggan.isEnabled = enabled
    }
}