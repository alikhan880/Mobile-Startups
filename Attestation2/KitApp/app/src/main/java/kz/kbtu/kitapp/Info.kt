package kz.kbtu.kitapp

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.LruCache
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_info.*

class Info : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        titleInfo.text = intent.getStringExtra("title")
        author.text = intent.getStringExtra("authors")
        fullInfo.text = intent.getStringExtra("description")
        pages.text = intent.getStringExtra("pageCount") + " страниц"
        val img = intent.getStringExtra("image")

        val requestQueue = Volley.newRequestQueue(this)
        val imageLoader = ImageLoader(requestQueue, object : ImageLoader.ImageCache{
            val cache = LruCache<String, Bitmap>(20)
            override fun getBitmap(url: String?): Bitmap? {
                return if(url != null) cache.get(url)
                else null
            }

            override fun putBitmap(url: String?, bitmap: Bitmap?) {
                if(url != null) cache.put(url, bitmap)
            }

        })
        (coverImageView as NetworkImageView).setImageUrl(img, imageLoader)
    }
}
