package com.ub.gprionggo.resepmakanan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val data = ArrayList<Makanan>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        logOut()
        addData()
        getData()
    }

    fun addData() {
        floatingactionbutton.setOnClickListener {
            val i = Intent(this, TambahData::class.java)
            startActivity(i)
        }
    }

    fun getData() {
        FirebaseDatabase.getInstance().reference.child("makananjatim")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    data.clear()
                    snapshot.children.forEach {
                        val datas = it.getValue(Makanan::class.java) as Makanan
                        data.add(datas)
                        recyclerViewMakanan.layoutManager = LinearLayoutManager(this@HomeActivity)
                        recyclerViewMakanan.adapter = MakananAdapter(applicationContext, data)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun logOut() {

        auth = FirebaseAuth.getInstance()
        btnLogout.setOnClickListener {
            auth.signOut()
            Intent(this@HomeActivity, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }
}