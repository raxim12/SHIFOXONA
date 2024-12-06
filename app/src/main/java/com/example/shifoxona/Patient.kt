package com.example.shifoxona

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Patient(val id: String, val name: String, val doctorName: String, val arrivalTime: Long)

class PatientAdapter(private val patients: List<Patient>) :
    RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val doctorNameTextView: TextView = itemView.findViewById(R.id.doctorNameTextView)
        val arrivalTimeTextView: TextView = itemView.findViewById(R.id.arrivalTimeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_patient, parent, false)
        return PatientViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patients[position]
        holder.nameTextView.text = patient.name
        holder.doctorNameTextView.text = patient.doctorName
        holder.arrivalTimeTextView.text = "Kelgan vaqti: ${java.text.SimpleDateFormat("HH:mm dd/MM/yyyy").format(patient.arrivalTime)}"
    }

    override fun getItemCount() = patients.size
}
