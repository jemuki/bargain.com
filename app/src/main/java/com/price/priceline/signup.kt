package com.price.priceline

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONObject


class signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val log = findViewById<Button>(R.id.tologin)
        log.setOnClickListener {
            startActivity(Intent(applicationContext, login::class.java))
        }
        val username = findViewById<EditText>(R.id.username)
        val email = findViewById<EditText>(R.id.email)
        val password1 = findViewById<EditText>(R.id.password1)
        val password2 = findViewById<EditText>(R.id.password2)
        val phone = findViewById<EditText>(R.id.zxphone)

        val newacc = findViewById<Button>(R.id.createaccount)
        newacc.setOnClickListener {
            val passwordx1 = findViewById<EditText>(R.id.password1)
            val passwordx2 = findViewById<EditText>(R.id.password2)


            if (passwordx1.text.toString() != passwordx2.text.toString()) {
                Toast.makeText(applicationContext, "Your password didn't match", Toast.LENGTH_LONG).show()
                }

            else{
                if (phone.text.length == 0){
                    Toast.makeText(applicationContext, "please fill in all the details", Toast.LENGTH_LONG).show()
                }//ends if phone
                else {
                    if (email.text.length == 0){
                        Toast.makeText(applicationContext, "please upload all the details", Toast.LENGTH_LONG).show()
                    }//ends if
                    else {
                        val client = AsyncHttpClient(true, 80, 443)
                        //prepare what you will post
                        val data = JSONObject()
                        //get data from edit text
                        data.put("username", username.text.toString())
                        data.put("email", email.text.toString())
                        data.put("password1", password1.text.toString())
                        data.put("school", phone.text.toString())



                            //convert data into a format that our api is using
                        val condata = StringEntity(data.toString())

                        //post it to api
                        client.post(this, "http://coding.co.ke/jereson/pricesignup",
                            condata, "application/json",
                            object: JsonHttpResponseHandler() {
                                override fun onSuccess(
                                    statusCode: Int,
                                    headers: Array<out Header>?,
                                    response: JSONObject?
                                ) {
                                    // super.onSuccess(statusCode, headers, response)
                                    //save to preferences
                                    val prefs = applicationContext.getSharedPreferences("db", Context.MODE_PRIVATE)
                                    val editor = prefs.edit()
                                    editor.putString("username",username.text.toString())
                                    editor.putString("email", email.text.toString())
                                    editor.putString("password", password1.text.toString())
                                    editor.putString("contact", phone.text.toString())

                                    editor.apply()
                                    println("my response"+response)


                                    Toast.makeText(applicationContext, "account created successfully, welcome to bargain.com", Toast.LENGTH_LONG).show()
                                    username.text.clear()
                                    email.text.clear()
                                    password1.text.clear()
                                    password2.text.clear()

                                    startActivity(Intent(applicationContext, about::class.java))
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
                    }//ends email else
                }//ends phone else
            }//ends else password
        }//ends on click listener
    }//ends on create
}//ends class