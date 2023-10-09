package com.example.ujianandroid2.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ujianandroid2.R
import com.example.ujianandroid2.apiservice.model.TrxpinjamanItem

class PinjamanAdapter (var data : List<TrxpinjamanItem?> , private val clickListener: (TrxpinjamanItem) -> Unit, private val onlongclick : (TrxpinjamanItem) ->Unit ) : RecyclerView.Adapter<PinjamanAdapter.ViewHolder>() {

    // lateinit var data : List<TodolistItem?>
    fun setPinjaman(pinjaman: List<TrxpinjamanItem?>?){
        if (pinjaman != null) {
            data = pinjaman
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listpinjaman, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtNama.text = data.get(position)?.namaLengkap
        holder.txtAlamat.text = data.get(position)?.alamat
        holder.txtOutstanding.text = data.get(position)?.jumlahOutstanding

        holder.itemView.setOnClickListener {
            clickListener(data.get(position)!!)
        }

        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                val alertDialog = AlertDialog.Builder(holder.itemView.context)
                    .setTitle("Hapus Data!!")
                    .setMessage("Apakah kamu ingin menghapus data ?")
                    .setPositiveButton("Delete"){dialog, which ->

                        onlongclick(data.get(position)!!)
                    }
                    .setNegativeButton("Cancel",null)
                    .create()
                alertDialog.show()


                return true
            }

        })



    }

    override fun getItemCount():Int = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val txtNama = itemView.findViewById<TextView>(R.id.txtNama)
        val txtAlamat = itemView.findViewById<TextView>(R.id.txtAlamat)
        val txtOutstanding = itemView.findViewById<TextView>(R.id.txtOutstanding)






    }
}