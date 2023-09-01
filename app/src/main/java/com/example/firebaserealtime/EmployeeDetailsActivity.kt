package com.example.firebaserealtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpName :TextView
    private lateinit var tvEmpAge: TextView
    private lateinit var tvEmpSalary: TextView
    private lateinit var btnUpdate :Button
    private lateinit var btnDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        tvEmpId = findViewById(R.id.tvEmpId)
        tvEmpName = findViewById(R.id.tvEmpName)
        tvEmpAge=findViewById(R.id.tvEmpAge)
        tvEmpSalary=findViewById(R.id.tvEmpSalary)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete=findViewById(R.id.btnDelete)

        setValuesToViews()

        btnDelete.setOnClickListener {
            deleteRecord(intent.getStringExtra("empId").toString())
        }
        btnUpdate.setOnClickListener {
            val intent = Intent(this@EmployeeDetailsActivity,EmployeeDetailsUpdateActivity::class.java)

            intent.putExtra("empId",tvEmpId.text.toString())
            intent.putExtra("empName",tvEmpName.text.toString())
            intent.putExtra("empAge",tvEmpAge.text.toString())
            intent.putExtra("empSalary",tvEmpSalary.text.toString())
            startActivity(intent)
        }
    }
    private fun setValuesToViews(){
        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpName.text =intent.getStringExtra("empName")
        tvEmpAge.text=intent.getStringExtra("empAge")
        tvEmpSalary.text=intent.getStringExtra("empSalary")
    }
    private fun deleteRecord(id:String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Employee data deleted",Toast.LENGTH_LONG).show()

            val intent = Intent(this,ReadActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {error->
            Toast.makeText(this,"Deleting Error ${error.message}",Toast.LENGTH_LONG).show()
        }
    }
}