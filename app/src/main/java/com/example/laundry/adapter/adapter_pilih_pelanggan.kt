package com.example.laundry.transaksi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.laundry.R
import com.example.laundry.modeldata.ModelPelanggan

class adapter_pilih_pelanggan(
    private val pelangganList: List<ModelPelanggan>,
    private val onItemClick: (ModelPelanggan) -> Unit
) : RecyclerView.Adapter<adapter_pilih_pelanggan.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_pilih_pelanggan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nomor = position + 1
        val item = pelangganList[position]
        holder.TvCard_ID_PP.text = "[$nomor]"
        holder.TvCard_NamaP_Pelanggan.text = item.tvCARD_PELANGGAN_Nama ?: "-"
        holder.TvCard_AlamatP_Pelanggan.text = "Alamat : ${item.tvCARD_PELANGGAN_Almt ?: "-"}"
        holder.TvCard_NoHP_PP.text = "No HP : ${item.tvCARD_PELANGGAN_NoHP ?: "-"}"

        holder.Cv_PP.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = pelangganList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Cv_PP: CardView = itemView.findViewById(R.id.Cv_PP)
        val TvCard_ID_PP: TextView = itemView.findViewById(R.id.TvCard_ID_PP)
        val TvCard_NamaP_Pelanggan: TextView = itemView.findViewById(R.id.TvCard_NamaP_Pelanggan)
        val TvCard_AlamatP_Pelanggan: TextView = itemView.findViewById(R.id.TvCard_AlamatP_Pelanggan)
        val TvCard_NoHP_PP: TextView = itemView.findViewById(R.id.TvCard_NoHP_PP)
    }
}
