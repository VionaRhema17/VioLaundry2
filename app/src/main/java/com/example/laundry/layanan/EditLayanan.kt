package com.example.laundry.layanan

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.example.laundry.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class EditLayanan : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etHarga: EditText
    private lateinit var etCabang: EditText
    private lateinit var btnEditSimpan: Button

    private var isEditMode = false
    private var idLayanan: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_tambah_layanan)

        etNama = findViewById(R.id.ET1_NamaLayanan)
        etHarga = findViewById(R.id.ET2_Harga)
        etCabang = findViewById(R.id.ET4_NamaCabang_Layanan)
        btnEditSimpan = findViewById(R.id.btn_Edit_Layanan)

        // Ambil data dari Intent
        idLayanan = intent.getStringExtra("id")
        val nama = intent.getStringExtra("nama")
        val harga = intent.getStringExtra("harga")
        val cabang = intent.getStringExtra("cabang")

        etNama.setText(nama)
        etHarga.setText(formatHargaUntukTampilan(harga ?: ""))
        etCabang.setText(cabang)

        setEditTextsEnabled(false)

        btnEditSimpan.setOnClickListener {
            if (!isEditMode) {
                setEditTextsEnabled(true)
                btnEditSimpan.text = "Simpan"
                isEditMode = true
            } else {
                val namaInput = etNama.text.toString().trim()
                val hargaInput = etHarga.text.toString().trim()
                val cabangInput = etCabang.text.toString().trim()

                if (namaInput.isEmpty()) etNama.error = "Harus diisi"
                if (hargaInput.isEmpty()) etHarga.error = "Harus diisi"
                if (cabangInput.isEmpty()) etCabang.error = "Harus diisi"
                if (namaInput.isEmpty() || hargaInput.isEmpty() || cabangInput.isEmpty()) return@setOnClickListener

                val hargaBersih = hargaInput
                    .replace("Rp", "")
                    .replace(".", "")
                    .replace(",", "")
                    .trim()

                val id = idLayanan ?: return@setOnClickListener
                val updateRef = FirebaseDatabase.getInstance().getReference("layanan").child(id)

                val updatedData = mapOf(
                    "tvCard_ID_Layanan" to id,
                    "tvCard_NamaLayanan" to namaInput,
                    "tvCard_Harga" to hargaBersih,
                    "tvCard_NamaCabang_Layanan" to cabangInput
                )

                updateRef.updateChildren(updatedData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Data layanan berhasil diperbarui", Toast.LENGTH_SHORT).show()
                        setEditTextsEnabled(false)
                        btnEditSimpan.text = "Edit"
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
        etNama.isEnabled = enabled
        etHarga.isEnabled = enabled
        etCabang.isEnabled = enabled
    }

    private fun formatHargaUntukTampilan(harga: String): String {
        return try {
            val parsed = harga.replace("[^\\d]".toRegex(), "").toDouble()
            NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(parsed)
        } catch (e: Exception) {
            harga
        }
    }
}
