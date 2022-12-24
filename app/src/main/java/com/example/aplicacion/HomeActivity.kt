package com.example.aplicacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC
}

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Setup
        val bundle : Bundle?=intent.extras
        val email : String? =bundle?.getString("email")
        val provider : String? =bundle?.getString("provider")

        setup(email?:"",provider?:"")
    }
    private fun setup(email: String, provider: String){
        val emailtext = findViewById<TextView>(R.id.emailtextView)
        val providertext = findViewById<TextView>(R.id.providertextView)
        val logOut = findViewById<Button>(R.id.logOut)

        title = "Inicio"

        emailtext.text = email
        providertext.text =provider

        logOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}