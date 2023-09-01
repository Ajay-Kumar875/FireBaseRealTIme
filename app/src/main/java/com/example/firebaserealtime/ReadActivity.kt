package com.example.firebaserealtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ReadActivity : AppCompatActivity() {

    private lateinit var empRecyclerView: RecyclerView
    private lateinit var tvLoadingData:TextView
    private lateinit var empList:ArrayList<Employee>
    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        empRecyclerView =findViewById(R.id.rvEmp)
        empRecyclerView.layoutManager=LinearLayoutManager(this)
        empRecyclerView.setHasFixedSize(true)
        tvLoadingData=findViewById(R.id.tvLoadingData)

        empList = arrayListOf<Employee>()

        getEmployeeData()
    }
    private fun getEmployeeData(){
        empRecyclerView.visibility=View.GONE
        tvLoadingData.visibility=View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if (snapshot.exists()){
                    for (empSnap:DataSnapshot in snapshot.children){
                        val empData:Employee? = empSnap.getValue(Employee::class.java)
                        empList.add(empData!!)
                    }
                    val mAdapter=EmpAdapter(empList)
                    empRecyclerView.adapter=mAdapter

                    mAdapter.setOnItemClickListener(object :EmpAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@ReadActivity,EmployeeDetailsActivity::class.java)

                            intent.putExtra("empId",empList[position].empId)
                            intent.putExtra("empName",empList[position].empName)
                            intent.putExtra("empAge",empList[position].empAge)
                            intent.putExtra("empSalary",empList[position].empSalary)
                            startActivity(intent)
                        }
                    })

                    empRecyclerView.visibility=View.VISIBLE
                    tvLoadingData.visibility=View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}