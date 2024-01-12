package com.example.autetificacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autetificacion.databinding.ActivityMainBinding
import com.example.autetificacion.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db= FirebaseFirestore.getInstance()

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
                        db.collection("usuarios").document(binding.email.text.toString()).set(
                            mapOf(
                                "nombre"  to  binding.Nombre.text.toString(),
                                "apellidos" to binding.Apellidos.text.toString())

                        )
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