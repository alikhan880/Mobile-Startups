package kz.kbtu.pixabaysilver.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_video_detail.*
import kz.kbtu.pixabaysilver.R

class VideoDetailActivity : AppCompatActivity() {
    private var videoUrl : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)

        val tags = intent.getStringExtra("tags")
        videoUrl = intent.getStringExtra("videoUrl")
        val author = intent.getStringExtra("user")
        val likes = intent.getIntExtra("likes", 0)
        val favs = intent.getIntExtra("favorites", 0)
        val views = intent.getIntExtra("views", 0)

        tagsTextView.text = tags
        authorTextView.text = author
        likeTextView.text = likes.toString()
        favTextView.text = favs.toString()
        viewsTextView.text = views.toString()
        videoView.setVideoURI(Uri.parse(videoUrl))

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()
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
        intent.putExtra(Intent.EXTRA_SUBJECT, "Shared video: ${Uri.parse(videoUrl)}")
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "Share url of this video"))
        return true
    }
}
