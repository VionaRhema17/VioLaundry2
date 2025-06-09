package com.android.laundry

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.laundry.R
import com.example.laundry.cabang.DataCabang
import com.example.laundry.layanan.datalayanan
import com.example.laundry.pegawai.DataPegawai
import com.example.laundry.pelanggan.DataPelanggan
import com.example.laundry.tambah.DataTambah
import com.example.laundry.transaksi.Transaksi
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var pelanggan : ImageView
    lateinit var pegawai : CardView
    lateinit var layanan : CardView
    lateinit var cabang : CardView
    lateinit var transaksi : ImageView
    lateinit var tambahan : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Pastikan file XML sesuai

        pelanggan = findViewById(R.id.card_masuk_pelanggan)
        pegawai = findViewById(R.id.card_masuk_pegawai)
        layanan = findViewById(R.id.card_masuk_layanan)
        cabang = findViewById(R.id.card_masuk_cabang)
        transaksi = findViewById(R.id.card_masuk_transaksi)
        tambahan = findViewById(R.id.card_masuk_tambah)

        pelanggan.setOnClickListener {
            val intent = Intent(this@MainActivity, DataPelanggan:: class.java)
            startActivity(intent)
        }

        pegawai.setOnClickListener {
            val intent = Intent( this@MainActivity, DataPegawai:: class.java)
            startActivity(intent)
        }

        layanan.setOnClickListener {
            val intent = Intent( this@MainActivity, datalayanan::class.java)
            startActivity(intent)
        }

        cabang.setOnClickListener {
            val intent = Intent( this@MainActivity, DataCabang::class.java)
            startActivity(intent)
        }

        transaksi.setOnClickListener {
            val intent = Intent( this@MainActivity, Transaksi::class.java)
            startActivity(intent
            )
        }

        tambahan.setOnClickListener {
            val intent = Intent( this@MainActivity, DataTambah::class.java)
            startActivity(intent)
        }


        // Inisialisasi TextView
        val sapaTextView: TextView = findViewById(R.id.tvGreeting)
        val tanggalTextView: TextView = findViewById(R.id.tvDate)

        // Menampilkan pesan selamat berdasarkan waktu
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val greeting = when (hour) {
            in 5..10 -> "Selamat Pagi, Viona"
            in 11..14 -> "Selamat Siang, Viona"
            in 15..17 -> "Selamat Sore, Viona"
            else -> "Selamat Malam, Viona"
        }
        sapaTextView.text = greeting

        // Menampilkan tanggal saat ini
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        tanggalTextView.text = dateFormat.format(Date())
    }
}