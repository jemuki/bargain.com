package com.price.priceline

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso


class ImageAdapter(var context: Context, imageList: ArrayList<ImageModel>):
    RecyclerView.Adapter<ImageAdapter .ViewHolder>() {

    var imageList: List<ImageModel> = listOf() // empty product list


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    //Note below code returns above class and pass the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }


    //so far item view is same as single item
    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        val image = holder.itemView.findViewById(R.id.spimg) as ShapeableImageView

        val item = imageList[position]
        if (item.image == "blank") {
            image.visibility = View.GONE
        }//ends the if
        else {
            Picasso
                .with(context)
                .load(item.image)
                .fit()
                .into(image);

        }//ends the else
      }

    override fun getItemCount(): Int {
        return imageList.size
    }

    //we will call this function on Loopj response
    fun setImageListItems(imageList: List<ImageModel>) {
        this.imageList = imageList
        notifyDataSetChanged()
    }
}
