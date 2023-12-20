package com.example.autetificacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autetificacion.databinding.ActivityInicioBinding
import com.example.autetificacion.databinding.ActivityMainBinding
import com.example.autetificacion.databinding.ActivityMainBinding.*
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.InicioSesion.setOnClickListener {
            login()
        }
        binding.Registarse.setOnClickListener{
            registro()
        }
    }

    private fun registro() {
        startActivity(Intent(this,RegistroActivity::class.java))
    }

    private fun login() {
        //Comprobar que los campo de correo no esten vacios

        if (binding.Correo.text.isNotEmpty() && binding.pwd.text.isNotEmpty()) {
            //Iniciamossecion con el metodo
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.Correo.text.toString(),
                binding.pwd.text.toString()
            )
                .addOnCompleteListener {
                    //Si la autentificacion tuvo exito
                    if (it.isSuccessful) {
                        val intent = Intent(this, InicioActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Correo o contraseña incorrecta", Toast.LENGTH_LONG)
                            .show()
                    }
                }
        }
        else{
            Toast.makeText(this, "Algun campo esta vacio", Toast.LENGTH_LONG)
                .show()
        }
    }
}