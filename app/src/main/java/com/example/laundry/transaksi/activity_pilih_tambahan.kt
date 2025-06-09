package com.example.laundry.transaksi

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.laundry.modeldata.ModelTambah
import com.google.firebase.database.*
import com.example.laundry.R
import com.example.laundry.adapter.adapter_pilih_tambahan

class activity_pilih_tambahan : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvKosong: TextView
    private lateinit var dbRef: DatabaseReference
    private lateinit var tambahList: ArrayList<ModelTambah>
    private lateinit var adapterTambah: adapter_pilih_tambahan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_tambahan)

        // Inisialisasi view
        searchView = findViewById(R.id.search_viewPT)
        recyclerView = findViewById(R.id.rv_data_PT)
        tvKosong = findViewById(R.id.TvKosongPT)

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        // Setup Data
        tambahList = arrayListOf()
        adapterTambah = adapter_pilih_tambahan(tambahList, tvKosong)
        recyclerView.adapter = adapterTambah

        // Ambil data dari Firebase
        getDataTambah()

        // Listener untuk search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                filterData(newText ?: "")
                return true
            }
        })
    }

    private fun getDataTambah() {
        dbRef = FirebaseDatabase.getInstance().getReference("DataTambah")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tambahList.clear()
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val item = data.getValue(ModelTambah::class.java)
                        item?.let { tambahList.add(it) }
                    }
                    adapterTambah.updateList(tambahList)
                } else {
                    tvKosong.visibility = TextView.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Tangani error jika perlu
            }
        })
    }

    private fun filterData(query: String) {
        val filteredList = tambahList.filter {
            it.nama_tambahan?.contains(query, ignoreCase = true) == true ||
                    it.harga_tambahan?.contains(query, ignoreCase = true) == true ||
                    it.cabang_tambahan?.contains(query, ignoreCase = true) == true
        }
        adapterTambah.updateList(filteredList)
    }
}
