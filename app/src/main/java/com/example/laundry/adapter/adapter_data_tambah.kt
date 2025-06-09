package com.example.laundry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.laundry.modeldata.ModelTambah
    import com.example.laundry.R

class adapter_data_tambah(private val list: List<ModelTambah>) :
    RecyclerView.Adapter<adapter_data_tambah.TambahViewHolder>() {

    inner class TambahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.TvCard_Nama_Tambahan)
        val tvHarga: TextView = itemView.findViewById(R.id.TvCard_Harga_Tambahan)
        val tvCabang: TextView = itemView.findViewById(R.id.TvCard_cabang_Tambahan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TambahViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_data_tambah, parent, false)
        return TambahViewHolder(view)
    }

    override fun onBindViewHolder(holder: TambahViewHolder, position: Int) {
        val data = list[position]
        holder.tvNama.text = data.nama_tambahan
        holder.tvHarga.text = "Rp ${data.harga_tambahan}"
        holder.tvCabang.text = data.cabang_tambahan
    }

    override fun getItemCount(): Int = list.size
}
