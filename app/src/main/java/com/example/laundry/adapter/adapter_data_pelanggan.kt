package com.example.laundry.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.laundry.R
import com.example.laundry.modeldata.ModelPelanggan
import com.example.laundry.pelanggan.EditPelanggan

class adapter_data_pelanggan(
    private val context: Context,
    private val listPelanggan: List<ModelPelanggan>
) : RecyclerView.Adapter<adapter_data_pelanggan.PelangganViewHolder>() {

    inner class PelangganViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvID: TextView = itemView.findViewById(R.id.tvCARD_PELANGGAN_ID)
        val tvNama: TextView = itemView.findViewById(R.id.tvCARD_PELANGGAN_Nama)
        val tvAlamat: TextView = itemView.findViewById(R.id.tvCARD_PELANGGAN_Almt)
        val tvNoHP: TextView = itemView.findViewById(R.id.tvCARD_PELANGGAN_NoHP)
        val tvCabang: TextView = itemView.findViewById(R.id.tvCARD_PELANGGAN_cabang)
        val tvDaftar: TextView = itemView.findViewById(R.id.tvCARD_PELANGGAN_daftar)
        val btnHubungi: Button = itemView.findViewById(R.id.btn_HUBUNGI)
        val btnLihat: Button = itemView.findViewById(R.id.btn_LIHAT)
        val cvCARD_PELANGGAN : CardView = itemView.findViewById(R.id.cvCARD_PELANGGAN)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PelangganViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_pelanggan1, parent, false)
        return PelangganViewHolder(view)
    }

    override fun onBindViewHolder(holder: PelangganViewHolder, position: Int) {
        val pelanggan = listPelanggan[position]

        holder.tvID.text = "ID: ${pelanggan.tvCARD_PELANGGAN_ID ?: "-"}"
        holder.tvNama.text = "Nama: ${pelanggan.tvCARD_PELANGGAN_Nama ?: "-"}"
        holder.tvAlamat.text = "Alamat: ${pelanggan.tvCARD_PELANGGAN_Almt ?: "-"}"
        holder.tvNoHP.text = "No HP: ${pelanggan.tvCARD_PELANGGAN_NoHP ?: "-"}"
        holder.tvCabang.text = "Cabang: ${pelanggan.tvCARD_PELANGGAN_Cabang ?: "-"}"
        holder.tvDaftar.text = "Terdaftar: ${pelanggan.tvCARD_PELANGGAN_daftar ?: "-"}"
        holder.cvCARD_PELANGGAN.setOnClickListener {
            val intent = Intent(context, EditPelanggan::class.java).apply {
                putExtra("tvCARD_PELANGGAN_ID", pelanggan.tvCARD_PELANGGAN_ID)
                putExtra("tvCARD_PELANGGAN_Nama", pelanggan.tvCARD_PELANGGAN_Nama)
                putExtra("tvCARD_PELANGGAN_Almt", pelanggan.tvCARD_PELANGGAN_Almt)
                putExtra("tvCARD_PELANGGAN_NoHP", pelanggan.tvCARD_PELANGGAN_NoHP)
                putExtra("tvCARD_PELANGGAN_Cabang", pelanggan.tvCARD_PELANGGAN_Cabang)
                putExtra("tvCARD_PELANGGAN_daftar", pelanggan.tvCARD_PELANGGAN_daftar)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }


        // Tombol Hubungi
        holder.btnHubungi.setOnClickListener {
            val nomor = pelanggan.tvCARD_PELANGGAN_NoHP?.replace("+62", "0") ?: ""
            if (nomor.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$nomor")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
            }
        }

        // Tombol Lihat
        holder.btnLihat.setOnClickListener {
            val intent = Intent(context, EditPelanggan::class.java).apply {
                putExtra("tvCARD_PELANGGAN_ID", pelanggan.tvCARD_PELANGGAN_ID)
                putExtra("tvCARD_PELANGGAN_Nama", pelanggan.tvCARD_PELANGGAN_Nama)
                putExtra("tvCARD_PELANGGAN_Almt", pelanggan.tvCARD_PELANGGAN_Almt)
                putExtra("tvCARD_PELANGGAN_NoHP", pelanggan.tvCARD_PELANGGAN_NoHP)
                putExtra("tvCARD_PELANGGAN_Cabang", pelanggan.tvCARD_PELANGGAN_Cabang)
                putExtra("tvCARD_PELANGGAN_daftar", pelanggan.tvCARD_PELANGGAN_daftar)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listPelanggan.size
}