package com.example.shifoxona

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AddPatientActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var doctorNameEditText: EditText
    private lateinit var addPatientButton: Button
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)

        nameEditText = findViewById(R.id.nameEditText)
        doctorNameEditText = findViewById(R.id.doctorNameEditText)
        addPatientButton = findViewById(R.id.addPatientButton)

        addPatientButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val doctorName = doctorNameEditText.text.toString()
            val arrivalTime = System.currentTimeMillis()

            if (name.isEmpty() || doctorName.isEmpty()) {
                Toast.makeText(this, "Iltimos, barcha maydonlarni to'ldiring", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val patient = hashMapOf(
                "name" to name,
                "doctorName" to doctorName,
                "arrivalTime" to arrivalTime
            )

            db.collection("Patients").add(patient)
                .addOnSuccessListener {
                    Toast.makeText(this, "Bemor muvaffaqiyatli qo'shildi", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Xatolik yuz berdi: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
