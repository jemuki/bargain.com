package com.price.priceline

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import org.json.JSONArray

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val back = findViewById<ImageButton>(R.id.logtoback)
        back.setOnClickListener {
            startActivity(Intent(applicationContext, signup::class.java))
        }

        //do the login
        val now = findViewById<Button>(R.id.nowlogin)
        now.setOnClickListener {
            val con = findViewById<EditText>(R.id.logcontact).text
            if (con.length == 0){
                Toast.makeText(applicationContext, "please upload all the details", Toast.LENGTH_LONG).show()
            }//ends if
            else {
            val client = AsyncHttpClient(true, 80, 443)
            val logem = findViewById<EditText>(R.id.logemail)
            val logpa = findViewById<EditText>(R.id.logpassword)
            val logco = findViewById<EditText>(R.id.logcontact)
            val details = logem.text.toString()+"plus"+logpa.text.toString().replace(" ", "_")
            client.get(this, "http://coding.co.ke/jereson/pricelogin/$details"
                ,null, "application/json",
                object  : JsonHttpResponseHandler(){
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out cz.msebera.android.httpclient.Header>?,
                        response: JSONArray?
                    ) {
                        val gson = GsonBuilder().create()
                        val List = gson.fromJson(response.toString(),
                            Array<Any>::class.java).toList()
                        if (List.size == 1){
                            val prefs = applicationContext.getSharedPreferences("db", Context.MODE_PRIVATE)
                            val editor = prefs.edit()
                            editor.putString("email",logem.text.toString())
                            editor.putString("password", logpa.text.toString())
                            editor.putString("contact", logco.text.toString())
                            editor.apply()
                            startActivity(Intent(applicationContext, about::class.java))
                        }else {
                           Toast.makeText(applicationContext, "account not found", Toast.LENGTH_LONG).show()
                       }//ends else
                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Array<out cz.msebera.android.httpclient.Header>?,
                        responseString: String?,
                        throwable: Throwable?
                    ) {
                        Toast.makeText(applicationContext, "Try again "+statusCode, Toast.LENGTH_LONG).show()
                 }
                })//ends handler
            }//ends else
        }//ends now login


    }//ends oncreate
}//ends class