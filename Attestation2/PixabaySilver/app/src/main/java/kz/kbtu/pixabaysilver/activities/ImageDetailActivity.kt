package kz.kbtu.pixabaysilver.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image_detail.*
import kz.kbtu.pixabaysilver.R

class ImageDetailActivity : AppCompatActivity() {
    var imageLink : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        val i = intent
        val tags = i.getStringExtra("tags")
        val author = i.getStringExtra("user")
        imageLink = i.getStringExtra("webformatURL")
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)

        val menuItem = menu!!.findItem(R.id.share)

        menuItem.setOnMenuItemClickListener {
            share()
        }
        return true
    }

    private fun share(): Boolean {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_SUBJECT, "Shared image : ${Uri.parse(imageLink)}")
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "Share url of this image"))
        return true
    }
}
