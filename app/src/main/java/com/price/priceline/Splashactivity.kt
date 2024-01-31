package com.price.priceline

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Splashactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashactivity)



        //we delay layout for x seconds
        Handler().postDelayed(  Runnable {          //runnable makes the odes inside runnable
            val myPrefs = getSharedPreferences("db", Context.MODE_PRIVATE)
            val email = myPrefs.getString("email", null)
            val password = myPrefs.getString("password", null)
            if (email != null && password != null )
            {
                startActivity(Intent(applicationContext, MainActivity::class.java))

            }else
            {
                startActivity(Intent(applicationContext, signup::class.java))
            }
            finish()    //finish this activity completely,,,you can't go back
        }, 4000)




    }//ends oncreate
}//ends class