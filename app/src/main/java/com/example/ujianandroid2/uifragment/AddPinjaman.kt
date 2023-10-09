package com.example.ujianandroid2.uifragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ujianandroid2.R
import com.example.ujianandroid2.apiservice.APIConfig
import com.example.ujianandroid2.apiservice.model.ResponseSuccess
import com.example.ujianandroid2.apiservice.model.TrxpinjamanItem
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

/**
 * A simple [Fragment] subclass.
 * Use the [AddPinjaman.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPinjaman : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: TrxpinjamanItem? = null
    private var param3: String? = null

    lateinit var txtNama : EditText
    lateinit var txtAlamat : EditText
    lateinit var txtOutstanding : EditText
    lateinit var txtTitle : TextView
    lateinit var btnSend : Button

    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getParcelable(ARG_PARAM2,TrxpinjamanItem::class.java)
            param3 = it.getString(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_pinjaman, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtNama = view.findViewById(R.id.editNama)
        txtAlamat = view.findViewById(R.id.editAlamat)
        txtOutstanding = view.findViewById(R.id.editOutstanding)
        btnSend = view.findViewById(R.id.btnSend)
        txtTitle = view.findViewById(R.id.txtTitle)

        txtTitle.text = param3

        progressBar = view.findViewById(R.id.progressBar2)

        if(param1 == "add"){

            btnSend.setOnClickListener(View.OnClickListener {
                addDataPinjaman(TrxpinjamanItem(txtAlamat.text.toString(),
                    txtNama.text.toString(),
                    null,
                    txtOutstanding.text.toString()))
            })

        }else{
            txtAlamat.setText(param2?.alamat)
            txtNama.setText( param2?.namaLengkap)
            txtOutstanding.setText(param2?.jumlahOutstanding)

            btnSend.setOnClickListener {

                updateDataPinjaman(TrxpinjamanItem(txtAlamat.text.toString(),txtNama.text.toString(),param2?.id,txtOutstanding.text.toString()))
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddPinjaman.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: TrxpinjamanItem, param3: String) =
            AddPinjaman().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putParcelable(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                }
            }
    }

    fun addDataPinjaman(data : TrxpinjamanItem){
        val client = APIConfig.getApiService()
            .addDataPinjaman(toRequestBody(data.namaLengkap.toString()),
                toRequestBody(data.alamat.toString()),
                toRequestBody(data.jumlahOutstanding.toString())

            )

        showProgressBar(true)
        client.enqueue(object : Callback<ResponseSuccess> {
            override fun onResponse(
                call: Call<ResponseSuccess>,
                response: Response<ResponseSuccess>
            ) {

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.e("INFO", "onSuccess: ${responseBody.message}")
                    showProgressBar(false)
                    parentFragmentManager.popBackStackImmediate()
                }
            }

            override fun onFailure(call: Call<ResponseSuccess>, t: Throwable) {
                showProgressBar(false)
                Log.e("INFO", "onFailure: ${t.message.toString()}")
            }
        })


    }

    fun updateDataPinjaman(data : TrxpinjamanItem){
        val client = APIConfig.getApiService()
            .updateDataPinjaman(toRequestBody(data.id.toString()),toRequestBody(data.namaLengkap.toString()),
                toRequestBody(data.alamat.toString()),toRequestBody(data.jumlahOutstanding.toString()))


        showProgressBar(true)
        client.enqueue(object : Callback<ResponseSuccess> {
            override fun onResponse(
                call: Call<ResponseSuccess>,
                response: Response<ResponseSuccess>
            ) {

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.e("INFO", "onSuccess: ${responseBody.message}")
                    showProgressBar(false)
                    parentFragmentManager.popBackStackImmediate()
                }
            }

            override fun onFailure(call: Call<ResponseSuccess>, t: Throwable) {
                showProgressBar(false)
                Log.e("INFO", "onFailure: ${t.message.toString()}")
            }
        })


    }

    fun toRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    fun showProgressBar(flag:Boolean){
        if (flag){
            progressBar.visibility = View.VISIBLE
            progressBar.animate()
        }else{
            progressBar.visibility = View.GONE

        }
    }
}