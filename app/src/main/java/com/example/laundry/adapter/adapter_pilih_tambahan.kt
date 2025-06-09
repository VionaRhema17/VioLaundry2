package com.example.laundry.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.laundry.R
import com.example.laundry.modeldata.ModelTambah
import java.text.NumberFormat
import java.util.*

class adapter_pilih_tambahan(
    private val tambahList: ArrayList<ModelTambah>,
    private val tvKosong: TextView
) : RecyclerView.Adapter<adapter_pilih_tambahan.ViewHolder>(), Filterable {

    private var filteredList: ArrayList<ModelTambah> = ArrayList(tambahList)
    private lateinit var context: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvCard: CardView = itemView.findViewById(R.id.CvCard_Tambahan)
        val tvID: TextView = itemView.findViewById(R.id.TvCard_ID_Tambahan)
        val tvNama: TextView = itemView.findViewById(R.id.TvCard_Nama_Tambahan)
        val tvHarga: TextView = itemView.findViewById(R.id.TvCard_Harga_Tambahan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.card_pilih_tambahan1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredList[position]
        val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))
        val hargaNumber = item.harga_tambahan
            ?.replace(".", "")
            ?.replace(",", "")
            ?.trim()
            ?.toIntOrNull() ?: 0
        val hargaFormatted = "Rp ${formatter.format(hargaNumber)}"

        holder.tvID.text = "[${position + 1}]"
        holder.tvNama.text = item.nama_tambahan ?: "-"
        holder.tvHarga.text = hargaFormatted

        holder.cvCard.setOnClickListener {
            val intent = Intent().apply {
                putExtra("idTambahan", item.id_tambahan)
                putExtra("nama", item.nama_tambahan)
                putExtra("harga", item.harga_tambahan)
            }

            if (context is Activity) {
                (context as Activity).setResult(Activity.RESULT_OK, intent)
                (context as Activity).finish()
            }
        }
    }


    override fun getItemCount(): Int = filteredList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val keyword = constraint?.toString()?.lowercase()?.trim() ?: ""
                val resultList = if (keyword.isEmpty()) {
                    tambahList
                } else {
                    tambahList.filter {
                        it.nama_tambahan?.lowercase()?.contains(keyword) == true
                    }
                }

                return FilterResults().apply {
                    values = ArrayList(resultList)
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as? ArrayList<ModelTambah> ?: arrayListOf()
                notifyDataSetChanged()

                tvKosong.visibility = if (filteredList.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    // ✅ Tambahkan fungsi updateList di bagian bawah adapter
    fun updateList(newList: List<ModelTambah>) {
        filteredList.clear()
        filteredList.addAll(newList)
        notifyDataSetChanged()

        tvKosong.visibility = if (filteredList.isEmpty()) View.VISIBLE else View.GONE
    }
}
