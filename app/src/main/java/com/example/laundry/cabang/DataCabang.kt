package com.example.laundry.cabang

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.example.laundry.R
import com.example.laundry.adapter.adapter_data_cabang
import com.example.laundry.modeldata.ModelCabang

class DataCabang : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Cabang")
    lateinit var rv_data_cabang: RecyclerView
    lateinit var fabData_cabang_tambah: FloatingActionButton
    lateinit var cabangList: ArrayList<ModelCabang>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_data_cabang)
        init()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        rv_data_cabang.layoutManager = layoutManager
        rv_data_cabang.setHasFixedSize(true)

        cabangList = arrayListOf<ModelCabang>()
        tekan()
        getdata()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun init() {
        rv_data_cabang = findViewById(R.id.rv_data_cabang)
        fabData_cabang_tambah = findViewById(R.id.fabData_cabang_tambah)
    }

    fun tekan() {
        fabData_cabang_tambah.setOnClickListener {
            val intent = Intent(this, TambahCabang::class.java)
            startActivity(intent)
        }
    }

    fun getdata() {
        val query = myRef.orderByChild("idCabang").limitToLast(100)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    cabangList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val cabang = dataSnapshot.getValue(ModelCabang::class.java)
                        if (cabang != null) {
                            cabangList.add(cabang)
                        }
                    }
                    val adapter = adapter_data_cabang(this@DataCabang, cabangList)
                    rv_data_cabang.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DataCabang, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
