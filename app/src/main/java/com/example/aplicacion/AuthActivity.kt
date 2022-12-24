package com.example.aplicacion

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FilterQueryProvider
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth


class AuthActivity : AppCompatActivity() {
    @SuppressLint("InvalidAnalyticsName")
    override fun onCreate(savedInstanceState: Bundle?) {
        //Splash
        Thread.sleep(2000)
        setTheme(R.style.Theme_Aplicacion)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

    //Analytics event
        val analytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("Message","Integracion de fire base completa")
        analytics.logEvent("Init Screen", bundle)

    //setup
        setup()
        }
    private fun setup(){
        title = "Autenticación"

        val logIn = findViewById<Button>(R.id.LogIn)
        val signUp = findViewById<Button>(R.id.signUp)
        //val signUp=findViewById(R.id.signUp) as Button
        val email = findViewById<EditText>(R.id.EmailEditText)
        val password = findViewById<EditText>(R.id.PasswordEditTex)

        signUp.setOnClickListener{
            if(email.text.isNotEmpty() && password.text.isNotEmpty()){
                FirebaseAuth.getInstance().
                signInWithEmailAndPassword(email.text.toString(),
                    password.text.toString()).addOnCompleteListener(){
                        if(it.isSuccessful){
                            showHome(it.result?.user?.email ?:"", ProviderType.BASIC)
                        }else{
                            showAlert()
                    }
                }
            }
        }
        logIn.setOnClickListener {
            if (email.text.isNotEmpty() && password.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    email.text.toString(),
                    password.text.toString()
                ).addOnCompleteListener() {
                    if (it.isSuccessful) {
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        showAlert()
                    }
                }
            }
        }
    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autentificando el ususario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog= builder.create()
        dialog.show()
    }
    private fun showHome(email: String, provider: ProviderType){
        val homeIntent = Intent( this,HomeActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(homeIntent)
    }
}
