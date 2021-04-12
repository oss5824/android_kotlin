package com.example.aop_part2_chapter01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val heightEditText:EditText=findViewById(R.id.heightEditText)
        val weightEditText = findViewById<EditText>(R.id.weightEditText)

        val resultButton = findViewById<Button>(R.id.resultButton)

        resultButton.setOnClickListener{

            if(heightEditText.text.isEmpty()||weightEditText.text.isEmpty()){
                Toast.makeText(this,"빈 값이 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //아래로는 빈 값이 올 수 없음
            val height:Int = heightEditText.text.toString().toInt()
            val weight:Int = weightEditText.text.toString().toInt()

            //안드로이드 시스템에서 result Activity를 찾는다.
            val intent= Intent(this,ResultActivity::class.java)

            intent.putExtra("height",height)
            intent.putExtra("weight",weight)
            startActivity(intent)
        }
    }
}