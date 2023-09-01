package com.example.firebaserealtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsUpdateActivity : AppCompatActivity() {

    private lateinit var tvEmpId: TextView
    private lateinit var etEmpName : EditText
    private lateinit var etEmpAge: EditText
    private lateinit var etEmpSalary: EditText
    private lateinit var btnUpdate : Button
    private lateinit var dbRef: DatabaseReference
    lateinit var name:String
    lateinit var age: String
    lateinit var salary:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details_update)

        tvEmpId = findViewById(R.id.tvEmpId)
        etEmpName =findViewById(R.id.tvEmpName)
        etEmpAge=findViewById(R.id.tvEmpAge)
        etEmpSalary =findViewById(R.id.tvEmpSalary)

        btnUpdate=findViewById(R.id.btnUpdate)

        setValuesToViews()

        btnUpdate.setOnClickListener {
            updateEmpData(tvEmpId.text.toString(),etEmpName.text.toString(),etEmpAge.text.toString(),etEmpSalary.text.toString())
            Toast.makeText(this,"Details Updated",Toast.LENGTH_LONG).show()
            val intent = Intent(this@EmployeeDetailsUpdateActivity,ReadActivity::class.java)
            startActivity(intent)
        }
    }
    private fun updateEmpData(tvEmpId:String,name:String,age:String,salary:String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(tvEmpId)
        var empInfo = Employee(tvEmpId,name,age,salary)
        dbRef.setValue(empInfo)
    }
    private fun setValuesToViews(){
        tvEmpId.text = intent.getStringExtra("empId")
        etEmpName.setText(intent.getStringExtra("empName"))
        etEmpAge.setText(intent.getStringExtra("empAge"))
        etEmpSalary.setText(intent.getStringExtra("empSalary"))

        name= etEmpName.text.toString()
        age = etEmpAge.text.toString()
        salary = etEmpSalary.text.toString()
    }
}