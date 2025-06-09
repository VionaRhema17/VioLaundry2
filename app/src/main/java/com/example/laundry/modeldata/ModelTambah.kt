package com.example.laundry.modeldata
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModelTambah(
    var id_tambahan: String? = "",
    var nama_tambahan: String? = "",
    var harga_tambahan: String? = "",
    var cabang_tambahan: String? = ""
) : Parcelable
