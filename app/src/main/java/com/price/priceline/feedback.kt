package com.price.priceline

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONObject
import java.util.*

class feedback : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        val back = findViewById<ImageButton>(R.id.idtoback)
        back.setOnClickListener {
            startActivity(Intent(applicationContext, profile::class.java))
        }

        val prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val email = prefs.getString("email", "")
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        //val myLdt = LocalDateTime.of(year, month, day, ... )
        val adate = day.toString()+"/"+month.toString()+"/"+year.toString()

        val send = findViewById<ImageButton>(R.id.sendfeednow)

        send.setOnClickListener {
        val feed = findViewById<EditText>(R.id.thefeedback)
        val client = AsyncHttpClient(true, 80, 443)
        //prepare what you will post
        val data = JSONObject()
        //get username from edit text
        data.put("email", email)
        data.put("feedback", feed.text.toString())
        data.put("date", adate)


        val condata = StringEntity(data.toString())

        //post it to api
        client.post(this, "http://coding.co.ke/jereson/pricefeedback",
            condata, "application/json",
            object: JsonHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    Toast.makeText(applicationContext, "thank you for your feedback", Toast.LENGTH_LONG).show()
                    feed.text.clear()
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseString: String?,
                    throwable: Throwable?
                ) {
                    Toast.makeText(applicationContext, "Something went wrong "+statusCode, Toast.LENGTH_LONG).show()


                }


            })//ends the client.post
        }//ends send on click

    }//ends oncreate
}//ends class