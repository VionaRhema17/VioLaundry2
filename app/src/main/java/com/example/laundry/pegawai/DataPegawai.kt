package com.example.laundry.pegawai

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.laundry.modeldata.ModelPegawai
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.laundry.R
import com.example.laundry.adapter.adapter_data_pegawai

class DataPegawai : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("pegawai")
    lateinit var rvData_Pegawai: RecyclerView
    lateinit var listPegawai: ArrayList<ModelPegawai>
    lateinit var fabData_Pegawai_Tambah: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_data_pegawai)
        init()

        val layoutManager = LinearLayoutManager(this)
            layoutManager.reverseLayout = true
            layoutManager.stackFromEnd = true
        rvData_Pegawai.layoutManager=layoutManager
        rvData_Pegawai.setHasFixedSize(true)
        listPegawai = arrayListOf<ModelPegawai>()
        getData()
        val fabData_Pegawai_Tambah : FloatingActionButton = findViewById(R.id.fabData_Pegawai_Tambah)
        fabData_Pegawai_Tambah.setOnClickListener{
            val intent = Intent (this, TambahPegawai::class.java)
            intent.putExtra("judul", this.getString(R.string.tv1_TambahPegawai))
            intent.putExtra("namaPegawai","" )
            intent.putExtra("alamatPegawai","" )
            intent.putExtra("idPegawai","")
            intent.putExtra("noHpPegawai","" )
            intent.putExtra("idCabangPegawai","" )
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun init() {
        rvData_Pegawai = findViewById(R.id.rvData_Pegawai)
        fabData_Pegawai_Tambah = findViewById(R.id.fabData_Pegawai_Tambah)
    }

    fun getData(){
        val query = myRef.orderByChild("idPegawai").limitToLast(100)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    listPegawai.clear()
                    for (dataSnapshot in snapshot.children) {
                        val pegawai = dataSnapshot.getValue(ModelPegawai::class.java)
                        if (pegawai != null) {
                            listPegawai.add(pegawai)
                        }
                    }

                    val adapter = adapter_data_pegawai(this@DataPegawai, listPegawai)
                    rvData_Pegawai.adapter = adapter
                    adapter.notifyDataSetChanged()
                }

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DataPegawai,error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}