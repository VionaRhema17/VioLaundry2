package com.example.laundry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.laundry.R
import com.example.laundry.modeldata.ModelTambah
import java.text.NumberFormat
import java.util.Locale


class adapter_Konfirmasi_data(private val list: List<ModelTambah>) :
    RecyclerView.Adapter<adapter_Konfirmasi_data.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvId: TextView = view.findViewById(R.id.TvCard_ID_KF)
        val tvNama: TextView = view.findViewById(R.id.TvCard_Nama_KF)
        val tvHarga: TextView = view.findViewById(R.id.TvCard_Harga_KF)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_konfirmasi_tambahan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvId.text = "[${position + 1}]"
        holder.tvNama.text = item.nama_tambahan ?: "-"

        val harga = item.harga_tambahan
            ?.replace(".", "")
            ?.replace(",", "")
            ?.trim()
            ?.toIntOrNull() ?: 0

        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        holder.tvHarga.text = "${formatter.format(harga)}"
    }


    override fun getItemCount(): Int = list.size
}
