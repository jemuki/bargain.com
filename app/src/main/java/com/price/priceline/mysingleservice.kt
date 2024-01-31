package com.price.priceline

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_mysingleservice.*

class mysingleservice : AppCompatActivity() {
//    lateinit var imageList: java.util.ArrayList<ImageModel>
//    lateinit var imageAdapter: ImageAdapter
//    lateinit var recyclerView: RecyclerView
    var pimg: ArrayList<String> = ArrayList<String>()
    private var position = 0
    var counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mysingleservice)

//        recyclerView = findViewById(R.id.myssrecycler)
//        imageList= java.util.ArrayList<ImageModel>()

        val back = findViewById<ImageButton>(R.id.myssertoback)
        back.setOnClickListener {
            finish()
        }

        val prefs = getSharedPreferences("servicedb", Context.MODE_PRIVATE)
        val name = findViewById<TextView>(R.id.myssname)
        name.text =prefs.getString("name", "")
        val price = findViewById<TextView>(R.id.myssprice)
        price.text = prefs.getString("price", "")
        val location = findViewById<TextView>(R.id.myssloc)
        location.text = prefs.getString("location", "")
        val desc = findViewById<TextView>(R.id.myssdesc)
        desc.text = prefs.getString("description", "")
        if (prefs.getString("image1", "")!!.isEmpty()){
            myrecyimgser.visibility = View.GONE
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

            val img = findViewById<ShapeableImageView>(R.id.myrecyimgser)
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




//        imageList.add(ImageModel("$image1"))
//        imageList.add(ImageModel("$image2"))
//        imageList.add(ImageModel("$image3"))
//        imageList.add(ImageModel("$image4"))
//        imageList.add(ImageModel("$image5"))
//        imageList.add(ImageModel("$image6"))
//        imageList.add(ImageModel("$image7"))
//        imageList.add(ImageModel("$image8"))
//        imageList.add(ImageModel("$image9"))
//        imageList.add(ImageModel("$image10"))
//        imageAdapter = ImageAdapter(applicationContext, imageList)
//        imageAdapter.setImageListItems(imageList)
//        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
//        recyclerView.setHasFixedSize(true)
//        recyclerView.adapter = imageAdapter



    }//ends oncreate
}//ends class