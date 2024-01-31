package com.price.priceline

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import kotlinx.android.synthetic.main.activity_addservice.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class addservice : AppCompatActivity() {
    //store uris of picked images
    private var images: ArrayList<Uri?>? = null
    var pimg: ArrayList<String> = ArrayList<String>()
    //current position/index of selected images
    private var position = 0
    private lateinit var send: FloatingActionButton

    //request code to pick image(s)
    private val PICK_IMAGES_CODE = 0

    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var filePath: Uri? = null
    private var numimgs: Int = 0

    var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addservice)

        val back = findViewById<ImageButton>(R.id.asptoback)
        back.setOnClickListener {
            finish()
        }

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        val send = findViewById<FloatingActionButton>(R.id.sendsernow)
        send.setOnClickListener {
            if (images!!.size >= 1 ){
                for (imageUri in images!!){
                    if (imageUri != null) {
                        uploadImage(imageUri)
                    }else {
                        uploadsome()
                    }
                }//ends for
            }//ends if size
            else {
                uploadsome()
            }//ends else for if
        }//ends send on click


        //init list
        images = ArrayList()
        pimg = ArrayList()
        //setup image switcher
        asimageSwitcher.setFactory { ImageView(applicationContext) }


        //pick images clicking this button
        pickImagesBtn.setOnClickListener {
            pickImagesIntent()
        }

        //switch to next image clicking this button
        nextBtn.setOnClickListener {
            if (position < images!!.size-1){
                position++
                asimageSwitcher.setImageURI(images!![position])
            }
            else{
                //no more images
                Toast.makeText(this, "No More images...", Toast.LENGTH_SHORT).show()
            }
        }

        //switch to previous image clicking this button
        previousBtn.setOnClickListener {
            if (position > 0){
                position--
                asimageSwitcher.setImageURI(images!![position])
            }
            else{
                //no more images
                Toast.makeText(this, "No More images...", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun pickImagesIntent(){
        val  intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), PICK_IMAGES_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGES_CODE){

            if (resultCode == Activity.RESULT_OK){

                if (data!!.clipData != null){
                    //picked multiple images
                    //get number of picked images
                    val count = data.clipData!!.itemCount
                    for (i in 0 until count){
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        //add image to list
                        images!!.add(imageUri)
                    }//ends for
                    numimgs = count
                    //set first image from list to image switcher
                    asimageSwitcher.setImageURI(images!![0])
                    position = 0;
                }
                else{
                    //picked single image
                    val imageUri = data.data
                    //set image to image switcher
                    asimageSwitcher.setImageURI(imageUri)
                    position = 0;

                }//

            }

        }


         }//ends oncreate


    private fun uploadImage(imageUri: Uri) {
        if(imageUri != null){
            println("uri2"+imageUri)
            Toast.makeText(applicationContext, "uploading...", Toast.LENGTH_LONG).show()
            val ref = storageReference?.child("price_services/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(imageUri!!)
                ?.addOnSuccessListener(
                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            val imageUrl = it.toString()
                            println("myurl is:"+imageUrl)
                            pimg.add(imageUrl)
                            var psize = pimg.size
                            var zip = numimgs
                            when {
                                pimg.size == numimgs ->{
                                    uploadall(pimg)
                                }
                            }//ends when

                            Toast.makeText(applicationContext, "uploading...", Toast.LENGTH_LONG).show()
                        }
                    })
                ?.addOnFailureListener(OnFailureListener { e ->
                    Toast.makeText(applicationContext, "please try again", Toast.LENGTH_LONG).show()
                })


        }else{
            uploadsome()
     }
    }//ends upload image



    private fun uploadall(pimg: ArrayList<String>) {
        val prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val email = prefs.getString("email", "")
        val username = prefs.getString("username", "")
        val contact = prefs.getString("contact", "")
        val name = findViewById<EditText>(R.id.asname)
        val price = findViewById<EditText>(R.id.asprice)
        val location = findViewById<EditText>(R.id.asloc)
        val desc = findViewById<EditText>(R.id.asdesc)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        //val myLdt = LocalDateTime.of(year, month, day, ... )
        val adate = day.toString()+"/"+month.toString()+"/"+year.toString()
        radioGroup = findViewById(R.id.radioGroupser)
        val intSelectButton: Int = radioGroup!!.checkedRadioButtonId
        radioButton = findViewById(intSelectButton)
        val cx = radioButton.text
        println("my category"+cx)

        val client = AsyncHttpClient(true, 80, 443)
        //prepare what you will post
        val data = JSONObject()
        //get username from edit text
        data.put("email", email)
        data.put("username", username)
        data.put("name", name.text.toString())
        data.put("price", price.text.toString())
        data.put("description", desc.text.toString())
        data.put("location", location.text.toString())
        data.put("contact", contact)
        data.put("category", cx)
        if (pimg.size >= 1){
            data.put("image1", this.pimg.get(0))
        }else {
            data.put("image1", "blank")
        }

        if (pimg.size >= 2){
            data.put("image2", this.pimg.get(1))
        }else {
            data.put("image2", "blank")
        }

        if (pimg.size >= 3){
            data.put("image3", this.pimg.get(2))
        }else {
            data.put("image3", "blank")
        }

        if (pimg.size >= 4){
            data.put("image4", this.pimg.get(3))
        }else {
            data.put("image4", "blank")
        }

        if (pimg.size >= 5){
            data.put("image5", this.pimg.get(4))
        }else {
            data.put("image5", "blank")
        }

        if (pimg.size >= 6){
            data.put("image6", this.pimg.get(5))
        }else {
            data.put("image6", "blank")
        }

        if (pimg.size >= 7){
            data.put("image7", this.pimg.get(6))
        }else {
            data.put("image7", "blank")
        }

        if (pimg.size >= 8){
            data.put("image8", this.pimg.get(7))
        }else {
            data.put("image8", "blank")
        }

        if (pimg.size >= 9){
            data.put("image9", this.pimg.get(8))
        }else {
            data.put("image9", "blank")
        }

        if (pimg.size >= 10){
            data.put("image10", this.pimg.get(9))
        }else {
            data.put("image10", "blank")
        }

        data.put("date", adate)

        val condata = StringEntity(data.toString())

        //post it to api
        client.post(this, "http://coding.co.ke/jereson/addpriceservice",
            condata, "application/json",
            object: JsonHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    Toast.makeText(applicationContext, "service added successfully", Toast.LENGTH_LONG).show()
                    name.text.clear()
                    price.text.clear()
                    location.text.clear()
                    desc.text.clear()
                    // imageSwitcher.clearFocus()
                    startActivity(Intent(applicationContext, services::class.java))
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

    }//ends upload all function

    private fun uploadsome(){
        val prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val email = prefs.getString("email", "")
        val username = prefs.getString("username", "")
        val contact = prefs.getString("contact", "")
        val name = findViewById<EditText>(R.id.asname)
        val price = findViewById<EditText>(R.id.asprice)
        val location = findViewById<EditText>(R.id.asloc)
        val desc = findViewById<EditText>(R.id.asdesc)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        //val myLdt = LocalDateTime.of(year, month, day, ... )
        val adate = day.toString()+"/"+month.toString()+"/"+year.toString()
        radioGroup = findViewById(R.id.radioGroupser)
        val intSelectButton: Int = radioGroup!!.checkedRadioButtonId
        radioButton = findViewById(intSelectButton)
        val cx = radioButton.text
        println("my category"+cx)

        val client = AsyncHttpClient(true, 80, 443)
        //prepare what you will post
        val data = JSONObject()
        //get username from edit text
        data.put("email", email)
        data.put("username", username)
        data.put("name", name.text.toString())
        data.put("price", price.text.toString())
        data.put("description", desc.text.toString())
        data.put("location", location.text.toString())
        data.put("contact", contact)
        data.put("date", adate)
        data.put("category", cx)
        val condata = StringEntity(data.toString())

        //post it to api
        client.post(this, "http://coding.co.ke/jereson/addpriceservicesome",
            condata, "application/json",
            object: JsonHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    Toast.makeText(applicationContext, "service added successfully", Toast.LENGTH_LONG).show()
                    name.text.clear()
                    price.text.clear()
                    location.text.clear()
                    desc.text.clear()
                    // imageSwitcher.clearFocus()
                    startActivity(Intent(applicationContext, services::class.java))
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

    }//ends fun addall

}//ends class