package com.price.priceline

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso


class ServiceAdapter(var context: Context, serviceList: ArrayList<ServiceModel>):
    RecyclerView.Adapter<ServiceAdapter .ViewHolder>() {

    var serviceList: List<ServiceModel> = listOf() // empty product list


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    //Note below code returns above class and pass the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return ViewHolder(view)
    }


    //so far item view is same as single item
    override fun onBindViewHolder(holder: ServiceAdapter.ViewHolder, position: Int) {
        val name = holder.itemView.findViewById(R.id.sname) as TextView
        val price = holder.itemView.findViewById<TextView>(R.id.sprice)
        val loc = holder.itemView.findViewById<TextView>(R.id.sloc)
        val image = holder.itemView.findViewById(R.id.simage) as ShapeableImageView

        val item = serviceList[position]
        if (item.image1.isEmpty()) {
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
            val x = Intent(context, singleservice::class.java)
            x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(x)

        }//ends holder.item view
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    //we will call this function on Loopj response
    fun setServiceListItems(serviceList: List<ServiceModel>) {
        this.serviceList = serviceList
        notifyDataSetChanged()
    }
}
