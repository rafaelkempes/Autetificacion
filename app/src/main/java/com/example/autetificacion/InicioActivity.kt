package com.example.autetificacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autetificacion.databinding.ActivityInicioBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db= FirebaseFirestore.getInstance()

    //Cerrar seccion
        binding.BCerrar.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,MainActivity::class.java))
        }
        //Guardar coche
        binding.BGuardarCoche.setOnClickListener{
            if(binding.EMarca.text.toString().isNotEmpty() && binding.EModelo.text.toString().isNotEmpty()
                && binding.EMatricula.text.toString().isNotEmpty() && binding.EColor.text.toString().isNotEmpty())
            {
                db.collection("coches").document(binding.EMatricula.text.toString()).set(mapOf(
                    "color"  to  binding.EColor.text.toString(),
                    "marca" to binding.EMarca.text.toString(),
                    "modelo" to binding.EModelo.text.toString()
                ))
            }
            else{
                Toast.makeText(this,"Algun campo esta vacio",Toast.LENGTH_LONG).show()
            }

        }
        //Editar
        binding.BEditar.setOnClickListener{
            db.collection("coches").whereEqualTo("matricula",binding.EMatricula.text.toString())
                .get().addOnSuccessListener {
                    it.forEach{
                        binding.EColor.setText(it.get("color")as String?)
                        binding.EMarca.setText(it.get("marca")as String?)
                        binding.EModelo.setText(it.get("color")as String?)
                    }
                }

        }

        //Eliminar coche

        binding.BEliminar.setOnClickListener{
            db.collection("coches").document(binding.EMatricula.text.toString()).delete()
        }
    }
}