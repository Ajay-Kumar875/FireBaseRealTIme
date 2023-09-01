package com.example.firebaserealtime

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class writeActivity : AppCompatActivity() {

    lateinit var empName: EditText
    lateinit var empAge: EditText
    lateinit var empSalary: EditText
    lateinit var btnWriteData: Button

    lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        empName = findViewById(R.id.empName)
        empAge = findViewById(R.id.empAge)
        empSalary = findViewById(R.id.empSalary)
        btnWriteData = findViewById(R.id.btnWriteData)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        btnWriteData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        val name = empName.text.toString()
        val age = empAge.text.toString()
        val salary = empSalary.text.toString()

        if (name.isEmpty()) empName.error = "Please enter name"
        if (age.isEmpty()) empName.error = "Please enter age"
        if (salary.isEmpty()) empName.error = "Please enter salary"

        val empId = dbRef.push().key!!

        val employee = Employee(empId, name, age, salary)
        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(
                    this@writeActivity,
                    "Data inserted to firestore successfully...",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { err ->
                Toast.makeText(this, "Error : ${err.message}", Toast.LENGTH_SHORT).show()
            }

    }
}