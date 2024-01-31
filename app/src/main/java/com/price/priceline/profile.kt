package com.price.priceline

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val back = findViewById<ImageButton>(R.id.protohome)
        back.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
        val editz = findViewById<FloatingActionButton>(R.id.toedit)
        editz.setOnClickListener {
            startActivity(Intent(applicationContext, edit::class.java))
        }

        val prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val name = findViewById<TextView>(R.id.pname)
        name.text = prefs.getString("username", "")
        val phone = findViewById<TextView>(R.id.pphone)
        phone.text = prefs.getString("contact", "")
        val email = findViewById<TextView>(R.id.pemail)
        email.text = prefs.getString("email", "")

        val mp = findViewById<CardView>(R.id.myproducts)
        mp.setOnClickListener {
            startActivity(Intent(applicationContext, myproducts::class.java))
        }
        val ms = findViewById<CardView>(R.id.myservices)
        ms.setOnClickListener {
            startActivity(Intent(applicationContext, myservices::class.java))
        }
        val tfeed = findViewById<CardView>(R.id.tofeedback)
        tfeed.setOnClickListener {
            startActivity(Intent(applicationContext, feedback::class.java))
        }
        val myfee = findViewById<CardView>(R.id.tomyfeed)
        myfee.setOnClickListener {
            startActivity(Intent(applicationContext, myfeed::class.java))
        }
        val ab = findViewById<CardView>(R.id.toabout)
        ab.setOnClickListener {
            startActivity(Intent(applicationContext, about::class.java))
        }
    }//ends oncreate
}//ends class