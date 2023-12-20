package com.example.autetificacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autetificacion.databinding.ActivityMainBinding
import com.example.autetificacion.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth

class RegistroActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Nuevo usuario"
        binding.BRegistrase.setOnClickListener {
            //comprobar que nigun campo este vacio
            if (binding.Nombre.text.isNotEmpty() && binding.Apellidos.text.isNotEmpty()
                && binding.email.text.isNotEmpty() && binding.contrasena.text.isNotEmpty()
            ) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.text.toString(),
                    binding.contrasena.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                       val intent = Intent(this,InicioActivity::class.java).apply {
                           putExtra("Nombreusuario",binding.Nombre.text.toString())
                       }
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, "Error al registro del nuevo usuario", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            } else {
                Toast.makeText(this, "Algun campo esta vacio", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}