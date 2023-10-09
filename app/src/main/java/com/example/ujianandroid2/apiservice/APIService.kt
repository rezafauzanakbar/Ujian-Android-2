package com.example.ujianandroid2.apiservice

import com.example.ujianandroid2.apiservice.model.ResponseGetAllData
import com.example.ujianandroid2.apiservice.model.ResponseSuccess
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface APIService {

    @GET("trxpinjaman/all")
    fun getAllData() : Call<ResponseGetAllData>

    @Multipart
    @POST("trxpinjaman/add")
    fun addDataPinjaman(@Part("NamaLengkap") NamaLengkap: RequestBody, @Part("Alamat")
    Alamat: RequestBody, @Part("JumlahOutstanding") JumlahOutstanding: RequestBody) : Call<ResponseSuccess>

    @Multipart
    @POST("trxpinjaman/update")
    fun updateDataPinjaman(@Part("Id") id: RequestBody, @Part("NamaLengkap") NamaLengkap: RequestBody, @Part("Alamat")
    Alamat: RequestBody, @Part("JumlahOutstanding") JumlahOutstanding: RequestBody) : Call<ResponseSuccess>

    @Multipart
    @POST("trxpinjaman/delete")
    fun deleteDataPinjaman(@Part("Id") id: RequestBody) : Call<ResponseSuccess>


    @GET("trxpinjaman/all")
    fun getAllDataByFilter(@Query("filters[0][co][2][fl]") filterField : String,
                           @Query("filters[0][co][2][op]") filterOperator : String,
                           @Query("filters[0][co][2][vl]") filterValue : String,
                           @Query("sort_order") sortorder : String

    ) : Call<ResponseGetAllData>
}