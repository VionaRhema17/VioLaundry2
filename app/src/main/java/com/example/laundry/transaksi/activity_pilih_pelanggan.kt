package com.example.laundry.transaksi

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.example.laundry.modeldata.ModelPelanggan
import com.example.laundry.R

class activity_pilih_pelanggan : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("pelanggan")
    private lateinit var rv_data_PP: RecyclerView
    private lateinit var pelangganList: ArrayList<ModelPelanggan>
    private lateinit var tvKosong: TextView
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var sharedPref: SharedPreferences
    private lateinit var tvCARD_PELANGGAN_Cabang: String
    private lateinit var fullList: ArrayList<ModelPelanggan>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pilih_pelanggan)

        sharedPref = getSharedPreferences("user_data", MODE_PRIVATE)
        tvCARD_PELANGGAN_Cabang = sharedPref.getString("tvCARD_PELANGGAN_Cabang", "") ?: ""
        Log.d("DEBUG_CABANG", "Cabang dari SharedPreferences: $tvCARD_PELANGGAN_Cabang")

        init()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        rv_data_PP.layoutManager = layoutManager
        rv_data_PP.setHasFixedSize(true)

        pelangganList = arrayListOf()
        fullList = arrayListOf()

        val adapter = adapter_pilih_pelanggan(pelangganList) { selectedPelanggan ->
            Log.d("SELECTED_PELANGGAN", "Nama: ${selectedPelanggan.tvCARD_PELANGGAN_Nama}, No HP: ${selectedPelanggan.tvCARD_PELANGGAN_NoHP}")
            val intent = Intent().apply {
                putExtra("NAMA", selectedPelanggan.tvCARD_PELANGGAN_Nama ?: "")
                putExtra("NOHP", selectedPelanggan.tvCARD_PELANGGAN_NoHP ?: "")
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        rv_data_PP.adapter = adapter

        setupSearch(adapter)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getData(adapter)
    }

    private fun init() {
        rv_data_PP = findViewById(R.id.rv_data_PP)
        tvKosong = findViewById(R.id.TvKosongPP)
        searchView = findViewById(R.id.search_viewPP)
        tvKosong.visibility = View.GONE
    }

    private fun getData(adapter: adapter_pilih_pelanggan) {
        val query = if (tvCARD_PELANGGAN_Cabang.isNotEmpty()) {
            myRef.orderByChild("tvCARD_PELANGGAN_Cabang")
                .equalTo(tvCARD_PELANGGAN_Cabang)
                .limitToLast(100)
        } else {
            myRef.limitToLast(100)
        }

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pelangganList.clear()
                fullList.clear()
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val pelanggan = dataSnapshot.getValue(ModelPelanggan::class.java)
                        Log.d("FIREBASE_DATA", "Item: ${dataSnapshot.value}")
                        if (pelanggan != null) {
                            pelangganList.add(pelanggan)
                            fullList.add(pelanggan)
                        }
                    }
                    adapter.notifyDataSetChanged()
                    tvKosong.visibility = if (pelangganList.isEmpty()) View.VISIBLE else View.GONE
                } else {
                    tvKosong.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@activity_pilih_pelanggan,
                    "Gagal mengambil data: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setupSearch(adapter: adapter_pilih_pelanggan) {
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText, adapter)
                return true
            }
        })
    }

    private fun filterList(query: String?, adapter: adapter_pilih_pelanggan) {
        val filteredList = ArrayList<ModelPelanggan>()
        if (query.isNullOrEmpty()) {
            filteredList.addAll(fullList)
        } else {
            val search = query.lowercase()
            for (item in fullList) {
                if (
                    item.tvCARD_PELANGGAN_Nama?.lowercase()?.contains(search) == true ||
                    item.tvCARD_PELANGGAN_NoHP?.lowercase()?.contains(search) == true
                ) {
                    filteredList.add(item)
                }
            }
        }
        pelangganList.clear()
        pelangganList.addAll(filteredList)
        adapter.notifyDataSetChanged()
        tvKosong.visibility = if (pelangganList.isEmpty()) View.VISIBLE else View.GONE
    }
}
