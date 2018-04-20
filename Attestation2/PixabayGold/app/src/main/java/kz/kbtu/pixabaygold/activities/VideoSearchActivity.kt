package kz.kbtu.pixabaygold.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_video_search.*
import kz.kbtu.pixabaygold.R
import kz.kbtu.pixabaygold.helpers.Utils
import kz.kbtu.pixabaysilver.adapters.PixabayVideosAdapter
import kz.kbtu.pixabaysilver.helpers.PixabayVideos
import org.json.JSONException
import org.json.JSONObject

class VideoSearchActivity : AppCompatActivity(), PixabayVideosAdapter.PixabayVideosListener, MenuItem.OnMenuItemClickListener {
    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        when(p0!!.itemId){
            R.id.favs ->{
                startActivity(Intent(this, FavoritesActivity :: class.java))
            }
        }
        return true
    }

    override fun onFavsClick(pos: Int) {
        val list = Utils.getFavsVideos()
        list.add(videosList[pos])
        Utils.saveFavsVideos(list)
        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(i: Int) {
        val intent = Intent(this, VideoDetailActivity :: class.java)
        intent.putExtra("tags", videosList[i].tags)
        intent.putExtra("favorites", videosList[i].favorites)
        intent.putExtra("videoUrl", videosList[i].videoUrl)
        intent.putExtra("views", videosList[i].views)
        intent.putExtra("likes", videosList[i].likes)
        intent.putExtra("user", videosList[i].user)
        startActivity(intent)
    }

    lateinit var requestQueue: RequestQueue
    lateinit var gridLayoutManager: GridLayoutManager
    val TAG = "MainActivityLogs"
    var searchFor = "car"
    var page = 1
    var videosList = arrayListOf<PixabayVideos>()
    lateinit var adapter: PixabayVideosAdapter
    var pastLoaded = 0
    var visibleLoaded = 0
    var countLoaded = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_search)
        requestQueue = Volley.newRequestQueue(this)
        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerViewVideos.layoutManager = gridLayoutManager
        adapter = PixabayVideosAdapter(this, videosList, this)
        recyclerViewVideos.adapter = adapter
        loadVideos(searchFor, page)

        recyclerViewVideos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0) {
                    pastLoaded = gridLayoutManager.childCount
                    visibleLoaded = gridLayoutManager.itemCount
                    countLoaded = gridLayoutManager.findFirstVisibleItemPosition()

                    if (visibleLoaded + pastLoaded >= countLoaded) {
                        loadVideos(searchFor, ++page)
                    }
                }
            }
        })

        imagesVideoActivity.setOnClickListener {
            startActivity(Intent(this, MainActivity :: class.java))
            finish()
        }


    }


    fun loadVideos(searchFor: String, i: Int) {
        val url = "https://pixabay.com/api/videos/?key=8585575-4820004a023cebe04decbec39&q=${Uri.encode(searchFor)}&page=$page"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject?) {
                        try{
                            val jsonArray = response!!.getJSONArray("hits")
                            for(x in 0 until jsonArray.length()){
                                val jsonObject = jsonArray.getJSONObject(x)
                                videosList.add(PixabayVideos(jsonObject.getString("tags"),
                                        jsonObject.getString("picture_id"), jsonObject.getJSONObject("videos")
                                        .getJSONObject("tiny").getString("url"), jsonObject.getInt("views"),
                                        jsonObject.getInt("favorites"), jsonObject.getInt("likes"), jsonObject.getString("user")))

                            }
                        }catch (e : JSONException){
                            e.printStackTrace()
                        }
                        val pos = videosList.size
                        adapter.notifyItemRangeInserted(pos, pos + 20)

                    }
                }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError?) {
                Log.d(TAG, "onError : ${error.toString()}")
            }

        })

        requestQueue.add(request)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        val item = menu!!.findItem(R.id.search)
        val favsItem = menu.findItem(R.id.favs)
        item.isVisible = false
        favsItem.setOnMenuItemClickListener(this)

        return true
    }
}
