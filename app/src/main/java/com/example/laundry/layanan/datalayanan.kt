package com.example.laundry.layanan

import android.content.Intent
import android.widget.Toast
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.example.laundry.R
import com.example.laundry.adapter.adapter_data_Layanan
import com.example.laundry.modeldata.ModelLayanan

class datalayanan : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("layanan")

    private lateinit var rv_data_layanan: RecyclerView
    private lateinit var fabData_layanan_tambah: FloatingActionButton
    private lateinit var layananList: ArrayList<ModelLayanan>
    private lateinit var adapter: adapter_data_Layanan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_datalayanan)

        init()
        setupRecyclerView()
        tekan()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        getdata() // refresh saat kembali ke halaman ini
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true

        rv_data_layanan.layoutManager = layoutManager
        rv_data_layanan.setHasFixedSize(true)

        layananList = arrayListOf()
        adapter = adapter_data_Layanan(layananList)
        rv_data_layanan.adapter = adapter
    }

    private fun getdata() {
        val query = myRef.orderByChild("idLayanan").limitToLast(100)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                layananList.clear()
                for (dataSnapshot in snapshot.children) {
                    val layanan = dataSnapshot.getValue(ModelLayanan::class.java)
                    if (layanan != null) {
                        layananList.add(layanan)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@datalayanan, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun init() {
        rv_data_layanan = findViewById(R.id.rv_data_layanan)
        fabData_layanan_tambah = findViewById(R.id.fabData_layanan_tambah)
    }

    private fun tekan() {
        fabData_layanan_tambah.setOnClickListener {
            val intent = Intent(this, TambahLayanan::class.java)
            startActivity(intent)
        }
    }
}
