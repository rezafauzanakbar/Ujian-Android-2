package com.example.ujianandroid2.apiservice.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseGetAllData(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class TrxpinjamanItem(

	@field:SerializedName("Alamat")
	val alamat: String? = null,

	@field:SerializedName("NamaLengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("JumlahOutstanding")
	val jumlahOutstanding: String? = null
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("trxpinjaman")
	val trxpinjaman: List<TrxpinjamanItem?>? = null
) : Parcelable
