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
import com.example.laundry.modeldata.ModelPegawai
import com.example.laundry.pegawai.EditPegawai

class adapter_data_pegawai(
    private val context: Context,
    private val listPegawai: List<ModelPegawai>
) : RecyclerView.Adapter<adapter_data_pegawai.PegawaiViewHolder>() {

    inner class PegawaiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvID: TextView = itemView.findViewById(R.id.tvCARD_PEGAWAI_ID)
        val tvNama: TextView = itemView.findViewById(R.id.tvCARD_PEGAWAI_Nama)
        val tvAlamat: TextView = itemView.findViewById(R.id.tvCARD_PEGAWAI_Almt)
        val tvNoHP: TextView = itemView.findViewById(R.id.tvCARD_PEGAWAI_NoHP)
        val tvCabang: TextView = itemView.findViewById(R.id.tvCARD_PEGAWAI_cabang)
        val tvDaftar: TextView = itemView.findViewById(R.id.tvCARD_PEGAWAI_daftar)
        val btnHubungi: Button = itemView.findViewById(R.id.btn_HUBUNGI_pegawai)
        val btnLihat: Button = itemView.findViewById(R.id.btn_LIHAT_pegawai)
        val CardPegawai: CardView = itemView.findViewById(R.id.CardPegawai)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PegawaiViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_data_pegawai1, parent, false)
        return PegawaiViewHolder(view)
    }

    override fun onBindViewHolder(holder: PegawaiViewHolder, position: Int) {
        val pegawai = listPegawai[position]

        holder.tvID.text = "ID: ${pegawai.idPegawai ?: "-"}"
        holder.tvNama.text = "Nama: ${pegawai.namaPegawai ?: "-"}"
        holder.tvAlamat.text = "Alamat: ${pegawai.alamatPegawai ?: "-"}"
        holder.tvNoHP.text = "No HP: ${pegawai.noHpPegawai ?: "-"}"
        holder.tvCabang.text = "Cabang: ${pegawai.idCabangPegawai ?: "-"}"
        holder.tvDaftar.text = "Terdaftar: ${pegawai.terdaftar ?: "-"}"

        holder.CardPegawai.setOnClickListener {
            val intent = Intent(context, EditPegawai::class.java).apply {
                putExtra("idPegawai", pegawai.idPegawai)
                putExtra("namaPegawai", pegawai.namaPegawai)
                putExtra("alamatPegawai", pegawai.alamatPegawai)
                putExtra("noHpPegawai", pegawai.noHpPegawai)
                putExtra("idCabangPegawai", pegawai.idCabangPegawai)
                putExtra("terdaftar", pegawai.terdaftar)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // tambahan flag untuk context non-activity
            }
            context.startActivity(intent)
        }


        // Tombol Hubungi: buka dialer telepon
        holder.btnHubungi.setOnClickListener {
            val nomor = pegawai.noHpPegawai?.replace("+62", "0") ?: ""
            if (nomor.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$nomor")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }

        // Tombol Lihat: buka EditPegawai dengan kirim data lewat Intent
        holder.btnLihat.setOnClickListener {
            val intent = Intent(context, EditPegawai::class.java).apply {
                putExtra("idPegawai", pegawai.idPegawai)
                putExtra("namaPegawai", pegawai.namaPegawai)
                putExtra("alamatPegawai", pegawai.alamatPegawai)
                putExtra("noHpPegawai", pegawai.noHpPegawai)
                putExtra("idCabangPegawai", pegawai.idCabangPegawai)
                putExtra("terdaftar", pegawai.terdaftar)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // tambahan flag untuk context non-activity
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listPegawai.size
}
