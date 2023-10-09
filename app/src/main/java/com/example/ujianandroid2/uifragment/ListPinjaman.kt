package com.example.ujianandroid2.uifragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ujianandroid2.R
import com.example.ujianandroid2.adapter.PinjamanAdapter
import com.example.ujianandroid2.apiservice.APIConfig
import com.example.ujianandroid2.apiservice.model.ResponseGetAllData
import com.example.ujianandroid2.apiservice.model.ResponseSuccess
import com.example.ujianandroid2.apiservice.model.TrxpinjamanItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

/**
 * A simple [Fragment] subclass.
 * Use the [ListPinjaman.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListPinjaman : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recyclerView: RecyclerView
    lateinit var pinjamanAdapter: PinjamanAdapter

    lateinit var fabAddData : FloatingActionButton

    lateinit var progressBar : ProgressBar

    lateinit var editTextSearch: EditText
    lateinit var buttonSearch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_pinjaman, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.lstTodo)
        progressBar = view.findViewById(R.id.progressBar)

        editTextSearch = view.findViewById(R.id.editTextSearch)
        buttonSearch = view.findViewById(R.id.buttonSearch)

        fabAddData = view.findViewById(R.id.floatingActionButton)

        fabAddData.setOnClickListener(View.OnClickListener {

            parentFragmentManager.beginTransaction()
                .addToBackStack("add form")
                .replace(R.id.frmFragmentRoot, AddPinjaman.newInstance("add", TrxpinjamanItem(), "Tambah Data"))
                .commit()

        })

        buttonSearch.setOnClickListener {
            searchingDataPinjaman()
        }

        getAllPinjamanlist()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListPinjaman.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListPinjaman().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun getAllPinjamanlist(){

        val client = APIConfig.getApiService().getAllData()


        showProgressBar(true)


        client.enqueue(object : Callback<ResponseGetAllData> {
            override fun onResponse(
                call: Call<ResponseGetAllData>,
                response: Response<ResponseGetAllData>
            ) {

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    showProgressBar(false)

                    pinjamanAdapter = PinjamanAdapter(responseBody.data?.trxpinjaman!!, {item ->

                        parentFragmentManager.beginTransaction()
                            .addToBackStack("add form")
                            .replace(R.id.frmFragmentRoot, AddPinjaman.newInstance("update",item, "Update Data"))
                            .commit()
                    } , { item ->
                        deleteDataPinjaman(item)

                    })

                    recyclerView.layoutManager = LinearLayoutManager(context)

                    recyclerView.adapter = pinjamanAdapter



                }
            }

            override fun onFailure(call: Call<ResponseGetAllData>, t: Throwable) {
                showProgressBar(false)
                Log.e("INFO", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun deleteDataPinjaman(data : TrxpinjamanItem){
        val client = APIConfig.getApiService()
            .deleteDataPinjaman(toRequestBody(data.id.toString()))

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
                    getAllPinjamanlist()
                }
            }

            override fun onFailure(call: Call<ResponseSuccess>, t: Throwable) {
                showProgressBar(false)
                Log.e("INFO", "onFailure: ${t.message.toString()}")
            }
        })


    }

    fun searchingDataPinjaman(){

        val filterField = "NamaLengkap"
        val filterOperator = "equal"
        val filterValue = editTextSearch.text.toString()
        val sortorder = "ASC"


        val client = APIConfig.getApiService()
            .getAllDataByFilter(filterField, filterOperator, filterValue, sortorder)

        showProgressBar(true)

        client.enqueue(object : Callback<ResponseGetAllData> {
            override fun onResponse(call: Call<ResponseGetAllData>, response: Response<ResponseGetAllData>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    showProgressBar(false)

                    pinjamanAdapter = PinjamanAdapter(responseBody.data?.trxpinjaman!!, {item ->

                        parentFragmentManager.beginTransaction()
                            .addToBackStack("add form")
                            .replace(R.id.frmFragmentRoot, AddPinjaman.newInstance("update",item, "Update Data"))
                            .commit()
                    } , { item ->
                        deleteDataPinjaman(item)

                    })

                    recyclerView.layoutManager = LinearLayoutManager(context)

                    recyclerView.adapter = pinjamanAdapter
                }
            }

            override fun onFailure(call: Call<ResponseGetAllData>, t: Throwable) {
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