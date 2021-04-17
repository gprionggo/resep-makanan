package com.ub.gprionggo.resepmakanan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            val email = etEmail1.text.toString().trim();
            val password = etPassword1.text.toString().trim()

            if (email.isEmpty()){
                etEmail1.error = "email harus diisi"
                etEmail1.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etEmail1.error = "Email Tidak Valid"
                etEmail1.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()||password.length < 6){
                etPassword1.error = "Password Harus Lebih dari 6 Karakter"
                etPassword1.requestFocus()
                return@setOnClickListener
            }

            registerUser(email,password)
        }

        btnMasuk.setOnClickListener {
            Intent(this@RegisterActivity,LoginActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
            if(it.isSuccessful){
                Intent(this@RegisterActivity,HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            } else{
                Toast.makeText(this,it.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            Intent(this@RegisterActivity,HomeActivity::class.java).also {intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}