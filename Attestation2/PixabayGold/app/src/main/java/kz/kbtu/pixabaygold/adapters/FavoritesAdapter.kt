package kz.kbtu.pixabaygold.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kz.kbtu.pixabaygold.R
import kz.kbtu.pixabaygold.helpers.Parent
import kz.kbtu.pixabaysilver.helpers.PixabayImages
import kz.kbtu.pixabaysilver.helpers.PixabayVideos

public class FavoritesAdapter(var context : Context, var list : ArrayList<Parent>) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = list[position]
        if(p is PixabayImages){
            Glide.with(context)
                    .load(p.webFormatURL)
                    .into(holder.imageView)
            holder.authorTextView.text = p.user
            holder.tagsTextView.text = p.tags;
        }

        else if(p is PixabayVideos){
            Glide.with(context)
                    .load("https://i.vimeocdn.com/video/${p.pictureId}_295x166.jpg")
                    .into(holder.imageView)

            holder.authorTextView.text = p.user
            holder.tagsTextView.text = p.tags;
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_pixabay_images, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){

        var imageView : ImageView = view.findViewById(R.id.imageView)
        var tagsTextView : TextView = view.findViewById(R.id.tagsTextView)
        var authorTextView : TextView = view.findViewById(R.id.authorTextView)
        var eachItem : CardView = view.findViewById(R.id.eachItem)
        var btn : Button = view.findViewById(R.id.btn_favs)
        init {
            btn.visibility = View.GONE
        }
    }
}