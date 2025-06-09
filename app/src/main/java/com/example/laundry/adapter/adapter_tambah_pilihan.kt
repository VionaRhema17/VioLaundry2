package com.example.laundry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.laundry.R
import com.example.laundry.modeldata.ModelTambah
import java.text.NumberFormat
import java.util.*

class adapter_tambah_pilihan(
    private val list: MutableList<ModelTambah>,
    private val onItemHapus: (ModelTambah) -> Unit
) : RecyclerView.Adapter<adapter_tambah_pilihan.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvId: TextView = itemView.findViewById(R.id.TvCard_ID_Tambahan)
        val tvNama: TextView = itemView.findViewById(R.id.TvCard_Nama_Tambahan)
        val tvHarga: TextView = itemView.findViewById(R.id.TvCard_Harga_Tambahan)
        val btnHapus: ImageView = itemView.findViewById(R.id.btnHapusTambahan)
        val cardView: CardView = itemView.findViewById(R.id.CvCard_Tambahan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_pilih_tambahan, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.tvId.text = "[${position + 1}]"
        holder.tvNama.text = item.nama_tambahan ?: "-"
        val harga = item.harga_tambahan?.toIntOrNull() ?: 0
        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        holder.tvHarga.text = "Harga: ${formatter.format(harga)}"

        holder.btnHapus.setOnClickListener {
            val removedItem = list[position]
            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, list.size)
            onItemHapus(removedItem)
        }
    }

    fun addItem(item: ModelTambah) {
        list.add(item)
        notifyItemInserted(list.size - 1)
    }
}
