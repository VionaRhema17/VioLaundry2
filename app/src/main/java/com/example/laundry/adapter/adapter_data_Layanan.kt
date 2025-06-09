package com.example.laundry.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.laundry.R
import com.example.laundry.layanan.EditLayanan
import com.example.laundry.modeldata.ModelLayanan

class adapter_data_Layanan(
    private val listlayanan: ArrayList<ModelLayanan>
) : RecyclerView.Adapter<adapter_data_Layanan.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_data_layanan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listlayanan[position]
        holder.tvID.text = "[${position + 1}]"
        holder.tvNama.text = item.tvCard_NamaLayanan
        holder.tvHarga.text = item.tvCard_Harga
        holder.tvcabang.text = item.tvCard_NamaCabang_Layanan

        holder.cardlayanan.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, EditLayanan::class.java)

            // Gunakan key yang konsisten
            intent.putExtra("id", item.tvCard_ID_Layanan)
            intent.putExtra("nama", item.tvCard_NamaLayanan)
            intent.putExtra("harga", item.tvCard_Harga)
            intent.putExtra("cabang", item.tvCard_NamaCabang_Layanan)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listlayanan.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardlayanan: CardView = itemView.findViewById(R.id.Cv_Layanan)
        val tvID: TextView = itemView.findViewById(R.id.TvCard_ID_Layanan)
        val tvNama: TextView = itemView.findViewById(R.id.TvCard_NamaLayanan)
        val tvHarga: TextView = itemView.findViewById(R.id.TvCard_Harga)
        val tvcabang: TextView = itemView.findViewById(R.id.TvCard_NamaCabang_Layanan)
    }
}
