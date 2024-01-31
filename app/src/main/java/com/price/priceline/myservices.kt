package com.price.priceline

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import org.json.JSONArray
import java.util.ArrayList

class myservices : AppCompatActivity() {
    lateinit var serviceList: ArrayList<ServiceModel>
    lateinit var myserviceAdapter: MyserviceAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myservices)
        val back = findViewById<ImageButton>(R.id.mstoback)
        back.setOnClickListener {
            startActivity(Intent(applicationContext, profile::class.java))
        }

        val label = findViewById<ProgressBar>(R.id.myserprogrrerss)

        recyclerView = findViewById(R.id.myserrecycler)
        serviceList=ArrayList<ServiceModel>()

        val client = AsyncHttpClient(true, 80, 443)
        myserviceAdapter = MyserviceAdapter(applicationContext, serviceList)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)
        val prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val email = prefs.getString("email", "")

        client.get(this, "http://coding.co.ke/jereson/mypriceservices/$email"
            ,null, "application/json",
            object  : JsonHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out cz.msebera.android.httpclient.Header>?,
                    response: JSONArray?
                ) {
                    val gson = GsonBuilder().create()
                    val List = gson.fromJson(response.toString(),
                        Array<ServiceModel>::class.java).toList()
                    if (List.size == 0){
                        Toast.makeText(applicationContext, "No services yet", Toast.LENGTH_LONG).show()
                        label.visibility = View.GONE
                    }else {
                        myserviceAdapter.setServiceListItems(List)
                        label.visibility = View.GONE
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out cz.msebera.android.httpclient.Header>?,
                    responseString: String?,
                    throwable: Throwable?
                ) {
                    Toast.makeText(applicationContext, "Try again "+statusCode, Toast.LENGTH_LONG).show()
                    label.visibility = View.GONE
                }
            })//ends handler

        recyclerView.adapter = myserviceAdapter


    }//ends oncreate
}//ends class