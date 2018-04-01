package kz.kbtu.kitappsilver

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

/**
 * Created by abakh on 18-Mar-18.
 */
class BooksAdapter(val context: Context?, val books: JSONArray?) : BaseAdapter() {

    private var inflater: LayoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val imageLoader: ImageLoader?
    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)

    init {
        imageLoader = ImageLoader(requestQueue, object : ImageLoader.ImageCache {
            val cache = LruCache<String, Bitmap>(20)

            override fun getBitmap(url: String?): Bitmap? {
                if (url == null) return null
                else return cache.get(url)
            }

            override fun putBitmap(url: String?, bitmap: Bitmap?) {
                if (url != null) cache.put(url, bitmap)
            }

        })

    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (p1 == null) {
            view = inflater.inflate(R.layout.row_item, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder

        } else {
            view = p1
            viewHolder = view.tag as ViewHolder
        }
        try {
            val book = books!!.getJSONObject(p0)
            val volumeInfo = book.getJSONObject("volumeInfo")


            var title: String? = null
            var subTitle: String? = null

            if (volumeInfo.has("title")) title = volumeInfo.getString("title")
            if (volumeInfo.has("subtitle")) subTitle =  volumeInfo.getString("subtitle")
            viewHolder.titleTextView.text = title
            viewHolder.subtitleTextView.text = subTitle

            val imageLinks = volumeInfo.getJSONObject("imageLinks")
            val thumbnailLink = imageLinks.getString("thumbnail")
            val pages = volumeInfo.getString("pageCount")
            viewHolder.pagesCount.text = "$pages страниц"
            (viewHolder.coverImageView as NetworkImageView).setImageUrl(thumbnailLink, imageLoader)

        } catch (e: JSONException) {
            e.printStackTrace()
        }


        return view as View

    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return books!!.length()
    }
}

class ViewHolder(row: View?) {
    var titleTextView: TextView = row!!.findViewById(R.id.title)
    var subtitleTextView: TextView = row!!.findViewById(R.id.subTitle)
    var coverImageView: ImageView = row!!.findViewById(R.id.coverImageView)
    var pagesCount: TextView = row!!.findViewById(R.id.pages)

}
