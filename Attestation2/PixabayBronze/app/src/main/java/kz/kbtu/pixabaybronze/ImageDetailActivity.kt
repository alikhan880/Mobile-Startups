package kz.kbtu.pixabaybronze

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image_detail.*

class ImageDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        val i = intent
        val tags = i.getStringExtra("tags")
        val author = i.getStringExtra("user")
        val imageLink = i.getStringExtra("webformatURL")
        val likes = i.getIntExtra("likes", 0)
        val favs = i.getIntExtra("favorites", 0)
        val comments = i.getIntExtra("comments", 0)

        tagsTextView.text = tags
        likeTextView.text = likes.toString()
        favTextView.text = favs.toString()
        commentsTextView.text = comments.toString()
        authorTextView.text = author

        Glide.with(this)
                .load(imageLink)
                .into(image)

    }
}
