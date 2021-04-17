package com.ub.gprionggo.resepmakanan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_makanan.*

class DetailMakanan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_makanan)
        val bundle = intent.extras
        Picasso.get().load(bundle?.getString("image")).into(detailImg)
        detailNama.text = bundle?.getString("nama")
        detailResep.text = bundle?.getString("resep")
    }
}