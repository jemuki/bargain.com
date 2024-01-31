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

class products : AppCompatActivity() {
    lateinit var productList: ArrayList<ProductModel>
    lateinit var productAdapter: ProductAdapter
    lateinit var recyclerView: RecyclerView
    var counter = 0

    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)


        val back = findViewById<ImageButton>(R.id.ducttoback)
        back.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }

        val pro = findViewById<ProgressBar>(R.id.progpro)

        //go to add products
        val toadd = findViewById<FloatingActionButton>(R.id.addpro)
        toadd.setOnClickListener {
            startActivity(Intent(applicationContext, addproduct::class.java))
        }

        val s1 = findViewById<ImageButton>(R.id.searchproicon)
        val cardx = findViewById<CardView>(R.id.sprobox)
        cardx.visibility = View.GONE
        swipeRefreshLayout = findViewById(R.id.refreshpro)
        //get the products from the api
        val prefs = getSharedPreferences("myfeed", Context.MODE_PRIVATE)
        if (prefs.getString("feed", null) != null){
            personalpro(prefs.getString("feed", "")!!)
        }//ends if
        else {
            recyclerView = findViewById(R.id.prorecycler)
            productList=ArrayList<ProductModel>()

            val client = AsyncHttpClient(true, 80, 443)
            productAdapter = ProductAdapter(applicationContext, productList)
            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
            recyclerView.setHasFixedSize(true)

            client.get(this, "http://coding.co.ke/jereson/priceproducts"
                ,null, "application/json",
                object  : JsonHttpResponseHandler(){
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out cz.msebera.android.httpclient.Header>?,
                        response: JSONArray?
                    ) {
                        val gson = GsonBuilder().create()
                        val List = gson.fromJson(response.toString(),
                            Array<ProductModel>::class.java).toList()

                        if (List.size == 0){
                            Toast.makeText(applicationContext, "No products yet", Toast.LENGTH_LONG).show()
                            pro.visibility = View.GONE
                        }else {
                            productAdapter.setProductListItems(List)
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

            recyclerView.adapter = productAdapter

        }//ends else


        //refresh the products
        swipeRefreshLayout.setOnRefreshListener {
            if (prefs.getString("feed", null) != null){
                swipeRefreshLayout.isRefreshing = false
                personalpro(prefs.getString("feed", "")!!)
            }//ends if not null
            else {
                swipeRefreshLayout.isRefreshing = false
                refresh()
            }//ends else
        }//ends refresh listener

        //refresh 2
        s1.setOnClickListener {
          if (counter == 0){
              cardx.visibility = View.VISIBLE
              s1.setImageResource(R.drawable.ic_baseline_close_24)
              counter ++
          }else {
              cardx.visibility = View.GONE
              s1.setImageResource(R.drawable.ic_baseline_search_24)
              counter --
          }
        }//ends search on click
        val s2 = findViewById<ImageButton>(R.id.searnowpro)
            s2.setOnClickListener {
                //get the json stuff
                val client = AsyncHttpClient(true, 80, 443)
                productAdapter = ProductAdapter(applicationContext, productList)
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                recyclerView.setHasFixedSize(true)

                val sea = findViewById<EditText>(R.id.etspro)
                val search =  sea.text.toString().replace(" ", "_")
                println("mysearch"+search)
                pro.visibility = View.VISIBLE
                client.get(this, "http://coding.co.ke/jereson/searchpriceproducts/$search"
                    ,null, "application/json",
                    object  : JsonHttpResponseHandler(){
                        override fun onSuccess(
                            statusCode: Int,
                            headers: Array<out cz.msebera.android.httpclient.Header>?,
                            response: JSONArray?
                        ) {
                            val gson = GsonBuilder().create()
                            val List = gson.fromJson(response.toString(),
                                Array<ProductModel>::class.java).toList()
                            if (List.size == 0){
                                Toast.makeText(applicationContext, "No matching results", Toast.LENGTH_LONG).show()
                                pro.visibility = View.GONE
                            }else {

                                productAdapter.setProductListItems(List)
                                cardx.visibility = View.GONE
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

                recyclerView.adapter = productAdapter

            }//ends s2 inclick



    }//ends oncreate

    private fun refresh(){
        //get the products from the api
        recyclerView = findViewById(R.id.prorecycler)
        productList=ArrayList<ProductModel>()

        val client = AsyncHttpClient(true, 80, 443)
        productAdapter = ProductAdapter(applicationContext, productList)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)
        val proz = findViewById<ProgressBar>(R.id.progpro)

        client.get(this, "http://coding.co.ke/jereson/priceproducts"
            ,null, "application/json",
            object  : JsonHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out cz.msebera.android.httpclient.Header>?,
                    response: JSONArray?
                ) {
                    val gson = GsonBuilder().create()
                    val List = gson.fromJson(response.toString(),
                        Array<ProductModel>::class.java).toList()

                    if (List.size == 0){
                        Toast.makeText(applicationContext, "No products yet", Toast.LENGTH_LONG).show()
                        proz.visibility = View.GONE
                    }else {
                        productAdapter.setProductListItems(List)
                        proz.visibility = View.GONE
                    }
                }


                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out cz.msebera.android.httpclient.Header>?,
                    responseString: String?,
                    throwable: Throwable?
                ) {
                    Toast.makeText(applicationContext, "Try again "+statusCode, Toast.LENGTH_LONG).show()
                    proz.visibility = View.GONE
                }
            })//ends handler

        recyclerView.adapter = productAdapter

    }//ends refresh function

    private  fun personalpro(string: String) {
        println("my string" + string)
        //get the products from the api
        recyclerView = findViewById(R.id.prorecycler)
        productList=ArrayList<ProductModel>()

        val client = AsyncHttpClient(true, 80, 443)
        productAdapter = ProductAdapter(applicationContext, productList)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)
        val proz = findViewById<ProgressBar>(R.id.progpro)

        client.get(this, "http://coding.co.ke/jereson/personalpriceproducts/$string"
            ,null, "application/json",
            object  : JsonHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out cz.msebera.android.httpclient.Header>?,
                    response: JSONArray?
                ) {
                    val gson = GsonBuilder().create()
                    val List = gson.fromJson(response.toString(),
                        Array<ProductModel>::class.java).toList()

                    if (List.size == 0){
                        Toast.makeText(applicationContext, "No products yet", Toast.LENGTH_LONG).show()
                        proz.visibility = View.GONE
                    }else {
                        productAdapter.setProductListItems(List)
                        proz.visibility = View.GONE
                    }
                }


                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out cz.msebera.android.httpclient.Header>?,
                    responseString: String?,
                    throwable: Throwable?
                ) {
                    Toast.makeText(applicationContext, "Try again "+statusCode, Toast.LENGTH_LONG).show()
                    proz.visibility = View.GONE
                }
            })//ends handler

        recyclerView.adapter = productAdapter

    }//ends personal products personalization
}//ends class