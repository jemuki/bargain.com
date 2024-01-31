package com.price.priceline

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import com.google.android.material.imageview.ShapeableImageView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.squareup.picasso.Picasso
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import kotlinx.android.synthetic.main.activity_singleservice.*
import kotlinx.android.synthetic.main.activity_singleservice.backpro
import kotlinx.android.synthetic.main.activity_singleservice.nextpro
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class singleservice : AppCompatActivity() {
    var pimg: ArrayList<String> = ArrayList<String>()
    //current position/index of selected images
    private var position = 0
    var counter = 0
//    lateinit var imageList: java.util.ArrayList<ImageModel>
//    lateinit var imageAdapter: ImageAdapter
//    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singleservice)
//        recyclerView = findViewById(R.id.ssrecycler)
//        imageList= java.util.ArrayList<ImageModel>()

        val back = findViewById<ImageButton>(R.id.sstoback)
        back.setOnClickListener {
            finish()
        }
        val prefs = getSharedPreferences("servicedb", Context.MODE_PRIVATE)
        val name = findViewById<TextView>(R.id.ssname)
        name.text =prefs.getString("name", "")
        val price = findViewById<TextView>(R.id.ssprice)
        price.text = prefs.getString("price", "")
        val location = findViewById<TextView>(R.id.ssloc)
        location.text = prefs.getString("location", "")
        val desc = findViewById<TextView>(R.id.ssdesc)
        desc.text = prefs.getString("description", "")

        if (prefs.getString("image1", "")!!.isEmpty()){
            recyimgser.visibility = View.GONE
            consx.visibility = View.GONE
        }else {
            val image1 = prefs.getString("image1", "")
            val image2 = prefs.getString("image2", "")
            val image3 = prefs.getString("image3", "")
            val image4 = prefs.getString("image4", "")
            val image5 = prefs.getString("image5", "")
            val image6 = prefs.getString("image6", "")
            val image7 = prefs.getString("image7", "")
            val image8 = prefs.getString("image8", "")
            val image9 = prefs.getString("image9", "")
            val image10 = prefs.getString("image10", "")


            pimg = ArrayList()
            if (image1 != "blank"   ) {
                pimg.add(image1!!)
            }
            if (image2 != "blank") {
                pimg.add(image2!!)
            }
            if (image3 != "blank") {
                pimg.add(image3!!)
            }
            if (image4 != "blank") {
                pimg.add(image4!!)
            }
            if (image5 != "blank") {
                pimg.add(image5!!)
            }
            if (image6 != "blank") {
                pimg.add(image6!!)
            }
            if (image7 != "blank") {
                pimg.add(image7!!)
            }
            if (image8 != "blank") {
                pimg.add(image8!!)
            }
            if (image9 != "blank") {
                pimg.add(image9!!)
            }
            if (image10 != "blank") {
                pimg.add(image10!!)
            }

            val img = findViewById<ShapeableImageView>(R.id.recyimgser)
            Picasso.with(applicationContext).load(pimg[position]).fit().into(img)
            nextpro.setOnClickListener {
                if (position < pimg!!.size-1){
                    position++
                    Picasso.with(applicationContext).load(pimg[position]).fit().into(img)
                }
                else{
                    //no more images
                    Toast.makeText(this, "No More images...", Toast.LENGTH_SHORT).show()
                }
            }
            //switch to previous image clicking this button
            backpro.setOnClickListener {
                if (position > 0){
                    position--
                    Picasso.with(applicationContext).load(pimg[position]).fit().into(img)
                }
                else{
                    //no more images
                    Toast.makeText(this, "No More images...", Toast.LENGTH_SHORT).show()
                }

            }

//            imageList.add(ImageModel("$image1"))
//            imageList.add(ImageModel("$image2"))
//            imageList.add(ImageModel("$image3"))
//            imageList.add(ImageModel("$image4"))
//            imageList.add(ImageModel("$image5"))
//            imageList.add(ImageModel("$image6"))
//            imageList.add(ImageModel("$image7"))
//            imageList.add(ImageModel("$image8"))
//            imageList.add(ImageModel("$image9"))
//            imageList.add(ImageModel("$image10"))
//            imageAdapter = ImageAdapter(applicationContext, imageList)
//            imageAdapter.setImageListItems(imageList)
//            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
//            recyclerView.setHasFixedSize(true)
//            recyclerView.adapter = imageAdapter

        }//ends else



        //text the uploader
        val btn = findViewById<Button>(R.id.ssstext)
        val phone = prefs.getString("contact", "")
        btn.setOnClickListener {
            val call = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            startActivity(call)
        }


    }//ends oncreate


    private  fun contacted2(){
        val prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val email = prefs.getString("email", "")
        val prefs2  =  getSharedPreferences("servicedb", Context.MODE_PRIVATE)
        val id = prefs2.getString("id", "")
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        //val myLdt = LocalDateTime.of(year, month, day, ... )
        val adate = day.toString()+"/"+month.toString()+"/"+year.toString()

        val client = AsyncHttpClient(true, 80, 443)
        //prepare what you will post
        val data = JSONObject()
        //get username from edit text
        data.put("email", email)
        data.put("id", id )
        data.put("date", adate)
        val condata = StringEntity(data.toString())

        //post it to api
        client.post(this, "http://coding.co.ke/jereson/contactedservice",
            condata, "application/json",
            object: JsonHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    Toast.makeText(applicationContext, "done", Toast.LENGTH_LONG).show()
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

    }//end function


}//ends class