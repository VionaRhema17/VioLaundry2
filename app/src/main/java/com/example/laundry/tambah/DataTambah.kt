package com.example.laundry.tambah

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.example.laundry.modeldata.ModelTambah
import com.example.laundry.R
import com.example.laundry.adapter.adapter_data_tambah

class DataTambah : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tambahList: ArrayList<ModelTambah>
    private lateinit var dbRef: DatabaseReference
    private lateinit var adapter: adapter_data_tambah
    private lateinit var fabTambah: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_tambah)

        recyclerView = findViewById(R.id.rv_data_Tambahan)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        tambahList = ArrayList()
        adapter = adapter_data_tambah(tambahList)
        recyclerView.adapter = adapter

        fabTambah = findViewById(R.id.fabData_Tambahan_tambah)
        fabTambah.setOnClickListener {
            startActivity(Intent(this, TambahTambahan::class.java))
        }

        dbRef = FirebaseDatabase.getInstance().getReference("DataTambah")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tambahList.clear()
                if (snapshot.exists()) {
                    for (dataSnap in snapshot.children) {
                        val data = dataSnap.getValue(ModelTambah::class.java)
                        data?.let { tambahList.add(it) }
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Log error jika perlu
            }
        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
