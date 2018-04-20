package kz.kbtu.pixabaysilver.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kz.kbtu.pixabaysilver.R
import kz.kbtu.pixabaysilver.helpers.PixabayImages

class PixabayImagesAdapter(val context: Context, val imageList: ArrayList<PixabayImages>, val listener : PixabayImagesListener) :
        RecyclerView.Adapter<PixabayImagesAdapter.PixabayViewHolder>() {



    interface PixabayImagesListener{
        fun onItemClick(position : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PixabayViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_pixabay_images, parent, false)
        return PixabayViewHolder(v)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: PixabayViewHolder, position: Int) {
        Glide.with(context)
                .load(imageList[position].webFormatURL)
                .into(holder.imageView)

        holder.authorTextView.text = imageList[position].user
        holder.tagsTextView.text = imageList[position].tags;


    }

    public fun clear(){
        val size = imageList.size
        imageList.clear()
        notifyItemRangeRemoved(0, size)
    }

    inner class PixabayViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        override fun onClick(p0: View?) {
            when(p0!!.id){
                R.id.eachItem -> listener.onItemClick(adapterPosition)
            }
        }

        var imageView : ImageView = view.findViewById(R.id.imageView)
        var tagsTextView : TextView = view.findViewById(R.id.tagsTextView)
        var authorTextView : TextView = view.findViewById(R.id.authorTextView)
        var eachItem : CardView = view.findViewById(R.id.eachItem)

        init {

            eachItem.setOnClickListener(this)
        }
    }



}
