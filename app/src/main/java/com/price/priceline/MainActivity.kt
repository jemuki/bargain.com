package com.price.priceline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val back  = findViewById<FloatingActionButton>(R.id.toprofile)
        back.setOnClickListener {
            startActivity(Intent(applicationContext, profile::class.java))
        }
        val pro = findViewById<CardView>(R.id.card1)
        pro.setOnClickListener {
            startActivity(Intent(applicationContext, products::class.java))
        }
        val ser = findViewById<CardView>(R.id.card2)
        ser.setOnClickListener {
            startActivity(Intent(applicationContext, services::class.java))
        }

    }//ends oncreate
}//ends class