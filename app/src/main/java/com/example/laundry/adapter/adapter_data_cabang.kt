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
import com.example.laundry.cabang.TambahCabang
import com.example.laundry.modeldata.ModelCabang

class adapter_data_cabang(
    private val appContext: Context,
    private val listCabang: ArrayList<ModelCabang>
) : RecyclerView.Adapter<adapter_data_cabang.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_data_cabang, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listCabang[position]
        holder.tvID.text = item.id
        holder.tvNama.text = item.namaCabang
        holder.tvAlamat.text = item.alamat
        holder.tvNoHP.text = item.noHP
        holder.tvLayanan.text = item.layanan

        holder.btnHubungi.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${item.noHP}"))
            appContext.startActivity(intent)
        }

        holder.btnLihat.setOnClickListener {
            // TODO: Buat dan arahkan ke activity DetailCabang jika dibutuhkan
        }

        holder.cvCard.setOnClickListener {
            val intent = Intent(appContext, TambahCabang::class.java)
            intent.putExtra("judul", "Edit Cabang")
            intent.putExtra("id", item.id)
            intent.putExtra("namaCabang", item.namaCabang)
            intent.putExtra("alamat", item.alamat)
            intent.putExtra("noHP", item.noHP)
            intent.putExtra("layanan", item.layanan)
            appContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listCabang.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvCard: CardView = itemView.findViewById(R.id.CvCard_Cabang)
        val tvID: TextView = itemView.findViewById(R.id.TvCard_ID_Cabang)
        val tvNama: TextView = itemView.findViewById(R.id.TvCard_Nama_Cabang)
        val tvAlamat: TextView = itemView.findViewById(R.id.TvCard_Alamat_Cabang)
        val tvNoHP: TextView = itemView.findViewById(R.id.TvCard_NoHP_Cabang)
        val tvLayanan: TextView = itemView.findViewById(R.id.TvCard_Layanan_Cabang)
        val btnHubungi: Button = itemView.findViewById(R.id.btn_HUBUNGI)
        val btnLihat: Button = itemView.findViewById(R.id.btn_LIHAT)
    }
}
