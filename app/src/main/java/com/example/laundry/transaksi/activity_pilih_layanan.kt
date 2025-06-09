package com.example.laundry.transaksi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.example.laundry.R
import com.example.laundry.adapter.adapter_pilih_layanan
import com.example.laundry.modeldata.ModelLayanan

class activity_pilih_layanan : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var layananList: ArrayList<ModelLayanan>
    private lateinit var adapter: adapter_pilih_layanan
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_layanan)

        recyclerView = findViewById(R.id.rv_data_PL)
        searchView = findViewById(R.id.search_viewPL)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        layananList = arrayListOf()

        adapter = adapter_pilih_layanan(layananList) { layanan ->
            val intent = Intent()
            intent.putExtra("NAMA_LAYANAN", layanan.tvCard_NamaLayanan)
            intent.putExtra("HARGA", layanan.tvCard_Harga)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        recyclerView.adapter = adapter

        database = FirebaseDatabase.getInstance().getReference("layanan")
        getLayananData()

        setupSearch()
    }

    private fun getLayananData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                layananList.clear()
                for (layananSnapshot in snapshot.children) {
                    val layanan = layananSnapshot.getValue(ModelLayanan::class.java)
                    layanan?.let { layananList.add(it) }
                }
                adapter.updateList(layananList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@activity_pilih_layanan, "Gagal ambil data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = layananList.filter {
                    it.tvCard_NamaLayanan?.contains(newText.orEmpty(), ignoreCase = true) == true
                }
                adapter.updateList(filteredList)
                return true
            }
        })
    }
}