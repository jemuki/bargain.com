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

class edit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val back = findViewById<ImageButton>(R.id.editback)
        back.setOnClickListener {
            startActivity(Intent(applicationContext, profile::class.java))
        }
        val prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val email = prefs.getString("email", "")
        val username = prefs.getString("username", "")
        val contact = prefs.getString("contact", "")

        //send buttons
        val semail = findViewById<ImageButton>(R.id.sendeemail)
        val sname = findViewById<ImageButton>(R.id.sendename)
        val sphone = findViewById<ImageButton>(R.id.sendephone)
        val spass = findViewById<ImageButton>(R.id.sendepass)
        //edit texts
        val nemail  = findViewById<EditText>(R.id.eeemail)
        val nname = findViewById<EditText>(R.id.eename)
        val nphone = findViewById<EditText>(R.id.eephone)
        val p1 = findViewById<EditText>(R.id.epas1)
        val p2 = findViewById<EditText>(R.id.epas2)
        //post the new email

        semail.setOnClickListener {
            val client = AsyncHttpClient(true, 80, 443)
            //prepare what you will post
            val data = JSONObject()
            //get username from edit text
            data.put("email", email)
            data.put("nemail", nemail.text.toString())


            val condata = StringEntity(data.toString())

            //post it to api
            client.post(this, "http://coding.co.ke/jereson/editpriceemail",
                condata, "application/json",
                object: JsonHttpResponseHandler() {
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        response: JSONObject?
                    ) {
                        Toast.makeText(applicationContext, "email edited successfully", Toast.LENGTH_LONG).show()
                        val prefsuser = applicationContext.getSharedPreferences("db", Context.MODE_PRIVATE)
                        val editoruser = prefsuser.edit()
                        editoruser.putString("email", nemail.text.toString())
                        editoruser.apply()
                        startActivity(Intent(applicationContext, profile::class.java))

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

        }//ends for email


        //edit name
        sname.setOnClickListener {
            val client = AsyncHttpClient(true, 80, 443)
            //prepare what you will post
            val data = JSONObject()
            //get username from edit text
            data.put("email", email)
            data.put("nusername", nname.text.toString())


            val condata = StringEntity(data.toString())

            //post it to api
            client.post(this, "http://coding.co.ke/jereson/editpricename",
                condata, "application/json",
                object: JsonHttpResponseHandler() {
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        response: JSONObject?
                    ) {
                        Toast.makeText(applicationContext, "username edited successfully", Toast.LENGTH_LONG).show()
                        val prefsuser = applicationContext.getSharedPreferences("db", Context.MODE_PRIVATE)
                        val editoruser = prefsuser.edit()
                        editoruser.putString("username", nname.text.toString())
                        editoruser.apply()

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

        }//ends for email


        //edit contact
        sphone.setOnClickListener {
            val client = AsyncHttpClient(true, 80, 443)
            //prepare what you will post
            val data = JSONObject()
            //get username from edit text
            data.put("email", email)
            data.put("nphone", nphone.text.toString())


            val condata = StringEntity(data.toString())

            //post it to api
            client.post(this, "http://coding.co.ke/jereson/editpricephone",
                condata, "application/json",
                object: JsonHttpResponseHandler() {
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        response: JSONObject?
                    ) {
                        Toast.makeText(applicationContext, "contact edited successfully", Toast.LENGTH_LONG).show()
                        val prefsuser = applicationContext.getSharedPreferences("db", Context.MODE_PRIVATE)
                        val editoruser = prefsuser.edit()
                        editoruser.putString("contact", nphone.text.toString())
                        editoruser.apply()

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

        }//ends for email


        //edit your password
        spass.setOnClickListener {
            val client = AsyncHttpClient(true, 80, 443)
            //prepare what you will post
            val data = JSONObject()
            //get username from edit text
            data.put("email", email)
            data.put("oldpassword", p1.text.toString())
            data.put("newpassword", p2.text.toString())

            val condata = StringEntity(data.toString())

            //post it to api
            client.post(this, "http://coding.co.ke/jereson/editpricepassword",
                condata, "application/json",
                object: JsonHttpResponseHandler() {
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        response: JSONObject?
                    ) {
                        Toast.makeText(applicationContext, "$response", Toast.LENGTH_LONG).show()
                        val prefsuser = applicationContext.getSharedPreferences("db", Context.MODE_PRIVATE)
                        val editoruser = prefsuser.edit()
                        editoruser.putString("password", p2.text.toString())
                        editoruser.apply()

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

        }//ends for email


    }//ends oncreate
}//ends class