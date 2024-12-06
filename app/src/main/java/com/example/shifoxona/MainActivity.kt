package com.example.shifoxona

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class MainActivity : AppCompatActivity() {
    private lateinit var patientRecyclerView: RecyclerView
    private val db = FirebaseFirestore.getInstance()
    private lateinit var addPatient: AppCompatButton
    private var patientsListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        patientRecyclerView = findViewById(R.id.patientRecyclerView)
        patientRecyclerView.layoutManager = LinearLayoutManager(this)

        addPatient = findViewById(R.id.addBemor)
        addPatient.setOnClickListener {
            startActivity(Intent(this, AddPatientActivity::class.java))
        }

        // Ma'lumotlarni tartiblash (shifokor ism-familyasi bo'yicha)
        loadPatientsOrderedByDoctorName()
    }

    private fun loadPatientsOrderedByDoctorName() {
        // Firestore kolleksiyasini kuzatish uchun snapshot listener
        patientsListener = db.collection("Patients")
            .orderBy("doctorName") // Shifokor ism-familyasi bo'yicha tartiblash
            .addSnapshotListener { querySnapshot, e ->
                if (e != null) {
                    Toast.makeText(
                        this,
                        "Xatolik yuz berdi: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@addSnapshotListener
                }

                // Ma'lumotlarni yangilash
                val patients = querySnapshot?.documents?.map { document ->
                    Patient(
                        id = document.id,
                        name = document.getString("name") ?: "Noma'lum",
                        doctorName = document.getString("doctorName") ?: "Noma'lum",
                        arrivalTime = document.getLong("arrivalTime") ?: 0L
                    )
                } ?: emptyList()

                patientRecyclerView.adapter = PatientAdapter(patients)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Listenerni tozalash
        patientsListener?.remove()
    }
}
