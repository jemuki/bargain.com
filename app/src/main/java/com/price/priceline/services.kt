package com.price.priceline

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.GsonBuilder
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import org.json.JSONArray
import java.util.ArrayList


class services : AppCompatActivity() {
    lateinit var serviceList: ArrayList<ServiceModel>
    lateinit var serviceAdapter: ServiceAdapter
    lateinit var recyclerView: RecyclerView
    var counter = 0
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)

        val back  = findViewById<ImageButton>(R.id.sertoback)
        back.setOnClickListener {
            finish()
        }
        val add = findViewById<FloatingActionButton>(R.id.addser)
        add.setOnClickListener {
            startActivity(Intent(applicationContext, addservice::class.java))
        }

        val pro = findViewById<ProgressBar>(R.id.progser)
        swipeRefreshLayout = findViewById(R.id.refreshser)
        recyclerView = findViewById(R.id.serrecycler)
        serviceList=ArrayList<ServiceModel>()
        val prefs = getSharedPreferences("myfeed2", Context.MODE_PRIVATE)
        if (prefs.getString("feed", null) != null){
            personalservices(prefs.getString("feed", "")!!)
        }//ends if
        else {
            refreshservices()
        }//end else

        swipeRefreshLayout.setOnRefreshListener {
            if (prefs.getString("feed", null) != null) {
                swipeRefreshLayout.isRefreshing = false
                personalservices(prefs.getString("feed", "")!!)
            }//ends if
            else{
                swipeRefreshLayout.isRefreshing = false
                refreshservices()
            }//ends else
            }//ends on refresh
        val cardz = findViewById<CardView>(R.id.sserbox)
        cardz.visibility = View.GONE
        val s1 = findViewById<ImageButton>(R.id.searchsericon)
        s1.setOnClickListener {
            if (counter == 0){
                cardz.visibility = View.VISIBLE
                s1.setImageResource(R.drawable.ic_baseline_close_24)
                counter ++
            }else {
                cardz.visibility = View.GONE
                s1.setImageResource(R.drawable.ic_baseline_search_24)
                counter --
            }
           }

        val s2 = findViewById<ImageButton>(R.id.searnowser)
        s2.setOnClickListener {
            val client = AsyncHttpClient(true, 80, 443)
            serviceAdapter = ServiceAdapter(applicationContext, serviceList)
            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
            recyclerView.setHasFixedSize(true)
            val et = findViewById<EditText>(R.id.etsser)
            val search = et.text.toString().replace(" ", "_")
            pro.visibility = View.VISIBLE
            client.get(this, "http://coding.co.ke/jereson/searchpriceservices/$search"
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
                            Toast.makeText(applicationContext, "No matching results", Toast.LENGTH_LONG).show()
                            pro.visibility = View.GONE
                        }else {
                            serviceAdapter.setServiceListItems(List)
                            cardz.visibility = View.GONE
                            pro.visibility = View.GONE
                            s1.setImageResource(R.drawable.ic_baseline_search_24)
                        }
                    }


                    override fun onFailure(
                        statusCode: Int,
                        headers: Array<out cz.msebera.android.httpclient.Header>?,
                        responseString: String?,
                        throwable: Throwable?
                    ) {
                        Toast.makeText(applicationContext, "Try again "+statusCode, Toast.LENGTH_LONG).show()
                        pro.visibility = View.GONE
                    }
                })//ends handler

            recyclerView.adapter = serviceAdapter
        }//ends s2

    }//ends oncreate
    private fun refreshservices(){
        recyclerView = findViewById(R.id.serrecycler)
        serviceList=ArrayList<ServiceModel>()
        val prox = findViewById<ProgressBar>(R.id.progser)
        prox.visibility = View.VISIBLE
        val client = AsyncHttpClient(true, 80, 443)
        serviceAdapter = ServiceAdapter(applicationContext, serviceList)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)

        client.get(this, "http://coding.co.ke/jereson/priceservices"
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
                        prox.visibility = View.GONE
                    }else {
                        serviceAdapter.setServiceListItems(List)
                        prox.visibility = View.GONE
                    }
                }
                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out cz.msebera.android.httpclient.Header>?,
                    responseString: String?,
                    throwable: Throwable?
                ) {
                    Toast.makeText(applicationContext, "Try again "+statusCode, Toast.LENGTH_LONG).show()
                    prox.visibility = View.GONE
                }
            })//ends handler

        recyclerView.adapter = serviceAdapter

    }//ends function


    private fun personalservices(string: String) {
        recyclerView = findViewById(R.id.serrecycler)
        serviceList=ArrayList<ServiceModel>()
        val pro = findViewById<ProgressBar>(R.id.progser)
        val client = AsyncHttpClient(true, 80, 443)
        serviceAdapter = ServiceAdapter(applicationContext, serviceList)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)

        client.get(this, "http://coding.co.ke/jereson/personalpriceservices/$string"
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
                        pro.visibility = View.GONE
                    }else {
                        serviceAdapter.setServiceListItems(List)
                        pro.visibility = View.GONE
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out cz.msebera.android.httpclient.Header>?,
                    responseString: String?,
                    throwable: Throwable?
                ) {
                    Toast.makeText(applicationContext, "Try again "+statusCode, Toast.LENGTH_LONG).show()
                    pro.visibility = View.GONE
                }
            })//ends handler

        recyclerView.adapter = serviceAdapter

    }//ends personal services


}//ends class