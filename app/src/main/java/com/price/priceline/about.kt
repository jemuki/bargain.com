package com.price.priceline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageButton

class about : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val back = findViewById<ImageButton>(R.id.abouttback)
        back.setOnClickListener {
            startActivity(Intent(applicationContext, profile::class.java))
        }
        val web = findViewById<WebView>(R.id.webabout)
        web.loadUrl("file:///android_asset/ab.html")
    }//ends oncreate
}//ends class