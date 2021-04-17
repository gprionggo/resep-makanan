package com.ub.gprionggo.resepmakanan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_makanan.view.*


class MakananAdapter(val context: Context, private val data: ArrayList<Makanan>) :
    RecyclerView.Adapter<MakananAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val callItem = layoutInflater.inflate(R.layout.item_makanan, parent, false)
        return ViewHolder(callItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = data[position]
        val image = holder.itemView.gambarMasakan
        Picasso.get().load(data.image).into(image)
        holder.itemView.namaMasakan.text = data.nama
        holder.getdata(data)
//        Toast.makeText(context, ""+data.judul, Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
        return data.size

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var makanan = Makanan()

        init {
            view.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("id", makanan.id_makanan)
                bundle.putString("nama", makanan.nama)
                bundle.putString("resep", makanan.resep_masakan)
                bundle.putString("image", makanan.image)

                val i = Intent(itemView.context, DetailMakanan::class.java)
                i.putExtras( bundle)
                itemView.context.startActivity(i)
            }
        }

        fun getdata(makanans: Makanan) {
            makanan = makanans
        }
    }
}