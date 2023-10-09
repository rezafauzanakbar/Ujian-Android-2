package com.example.ujianandroid2

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.ujianandroid2.apiservice.APIConfig
import com.example.ujianandroid2.apiservice.model.ResponseSuccess
import com.example.ujianandroid2.apiservice.model.TrxpinjamanItem
import com.example.ujianandroid2.uifragment.ListPinjaman
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.frmFragmentRoot, ListPinjaman.newInstance("",""))
                .commit()
        }

        //  getAllTodolist()

        //  addDataTodoList(TodolistItem(" Kerja",null,"Berangkat Kerja","0"))
        //  updateDataTodoList(TodolistItem("Kerja Paksa"," 4","Romusha","1"))
        //   deleteDataTodoList(TodolistItem(null,"4",null,null))
    }

    fun deleteDataTodoList(data : TrxpinjamanItem){
        val client = APIConfig.getApiService()
            .deleteDataPinjaman(toRequestBody(data.id.toString()))

        client.enqueue(object : Callback<ResponseSuccess> {
            override fun onResponse(
                call: Call<ResponseSuccess>,
                response: Response<ResponseSuccess>
            ) {

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.e("INFO", "onSuccess: ${responseBody.message}")
                }
            }

            override fun onFailure(call: Call<ResponseSuccess>, t: Throwable) {

                Log.e("INFO", "onFailure: ${t.message.toString()}")
            }
        })


    }



    fun toRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}