package com.price.priceline

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.squareup.picasso.Picasso
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONObject


class MyserviceAdapter(var context: Context, serviceList: ArrayList<ServiceModel>):
    RecyclerView.Adapter<MyserviceAdapter .ViewHolder>(){

    var serviceList : List<ServiceModel> = listOf() // empty product list


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    //Note below code returns above class and pass the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyserviceAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_myproduct, parent, false)
        return ViewHolder(view)
    }


    //so far item view is same as single item
    override fun onBindViewHolder(holder: MyserviceAdapter.ViewHolder, position: Int) {
        val name = holder.itemView.findViewById(R.id.mypname) as TextView
        val price = holder.itemView.findViewById<TextView>(R.id.mypprice)
        val loc = holder.itemView.findViewById<TextView>(R.id.myploc)
        val del = holder.itemView.findViewById<ImageButton>(R.id.delmypro)
        val image = holder.itemView.findViewById(R.id.mypimage) as ShapeableImageView

        val item = serviceList[position]
        if (item.image1.isEmpty()){
            name.text = item.name
            price.text = item.price
            loc.text = item.location
            image.visibility = View.GONE
        }//ends the if
       else {
            name.text = item.name
            price.text = item.price
            loc.text = item.location
            Picasso
                .with(context)
                .load(item.image1)
                .fit()
                .into(image);

        }//ends the else




        del.setOnClickListener {
            delser(item.id)
        }

        holder.itemView.setOnClickListener {
            //save the clicked product on shared preference
            val prefs = context.getSharedPreferences("servicedb", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("id", item.id)
            editor.putString("username", item.username)
            editor.putString("name", item.name)
            editor.putString("location", item.location)
            editor.putString("price", item.price)
            editor.putString("description", item.description)
            editor.putString("contact", item.contact)
            editor.putString("image1", item.image1)
            editor.putString("image2", item.image2)
            editor.putString("image3", item.image3)
            editor.putString("image4", item.image4)
            editor.putString("image5", item.image5)
            editor.putString("image6", item.image6)
            editor.putString("image7", item.image7)
            editor.putString("image8", item.image8)
            editor.putString("image9", item.image9)
            editor.putString("image10", item.image10)

            editor.apply()

            //intent
            val x = Intent(context, mysingleservice::class.java)
            x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(x)

        }//ends holder.item view
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    //we will call this function on Loopj response
    fun setServiceListItems(serviceList: List<ServiceModel>){
        this.serviceList = serviceList
        notifyDataSetChanged()
    }

    private fun delser(id: String) {
        val client = AsyncHttpClient(true, 80, 443)
        //prepare what you will post
        val data = JSONObject()
        //get username from edit text
        data.put("id", id)
        val condata = StringEntity(data.toString())

        //post it to api
            client.post(context, "http://coding.co.ke/jereson/delpriceservice",
            condata, "application/json",
            object: JsonHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    Toast.makeText(context, "service deleted", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseString: String?,
                    throwable: Throwable?
                ) {
                    Toast.makeText(context, "Something went wrong "+statusCode, Toast.LENGTH_LONG).show()
                }
            })//ends the client.post
    }//ends function
}//end class
