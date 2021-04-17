package com.ub.gprionggo.resepmakanan

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_tambah_data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class TambahData : AppCompatActivity() {
    var id: Int = 0
    private val storageRef = Firebase.storage.reference
    private var curFile: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_data)

        ivImage.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE_IMAGE_PICK)
            }
        }

        btnTambah.setOnClickListener {
            uploadImageToStorage()
        }
    }

    private fun uploadImageToStorage() = CoroutineScope(Dispatchers.IO).launch {
        val fileName = "${UUID.randomUUID()}.jpg"
        val ref = storageRef.child("images/$fileName")
        val uploadTask = curFile?.let { ref.putFile(it) }

        val urlTask = uploadTask?.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result.toString()
                insertDatabase(downloadUri)
            } else {
                Toast.makeText(this@TambahData, "Not Success", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insertDatabase(downloadUri: String) {
        val nama = edtNama.text.toString()
        val resep = edtResep.text.toString()

        FirebaseDatabase.getInstance().reference.child("makananjatim").push()
            .setValue(Makanan(UUID.randomUUID().toString(), resep, downloadUri, nama))
        val i = Intent(this,HomeActivity::class.java)
        startActivity(i)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE_PICK) {
            data?.data?.let {
                curFile = it
                ivImage.setImageURI(it)
            }
        }
    }

    companion object {
        const val REQUEST_CODE_IMAGE_PICK = 0
        const val TAG = "ACTIVITY"
    }
}