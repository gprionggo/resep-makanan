package com.ub.gprionggo.resepmakanan

import com.google.firebase.database.PropertyName

data class Makanan (
    var id_makanan:String = "0",
    var resep_masakan: String = "",
    var image:String = "",
    var nama: String = ""
)