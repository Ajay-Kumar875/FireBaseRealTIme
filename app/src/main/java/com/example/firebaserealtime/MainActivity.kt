package com.example.firebaserealtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var btnWrite: Button
    lateinit var btnRead:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnWrite=findViewById(R.id.btnWrite)
        btnRead=findViewById(R.id.btnRead)

        btnWrite.setOnClickListener {
            val intent= Intent(this,writeActivity::class.java)
            startActivity(intent)
        }

        btnRead.setOnClickListener {
            val intent=Intent(this,ReadActivity::class.java)
            startActivity(intent)
        }


    }
}