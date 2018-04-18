package kz.kbtu.pixabaybronze

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PixabayImagesAdapter.PixabayImagesListener{
    override fun onItemClick(i: Int) {
        val intent = Intent(this, ImageDetailActivity :: class.java)
        intent.putExtra("tags", imagesList[i].tags)
        intent.putExtra("webformatURL", imagesList[i].webFormatURL)
        intent.putExtra("favorites", imagesList[i].favorites)
        intent.putExtra("likes", imagesList[i].likes)
        intent.putExtra("comments", imagesList[i].comments)
        intent.putExtra("user", imagesList[i].user)
        startActivity(intent)
    }

    lateinit var requestQueue : RequestQueue
    lateinit var gridLayoutManager : GridLayoutManager
    val TAG = "MainActivityLogs"
    var searchFor = "car"
    var page = 1
    var imagesList = arrayListOf<PixabayImages>()
    lateinit var adapter : PixabayImagesAdapter
    var pastLoaded = 0
    var visibleLoaded = 0
    var countLoaded = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue = Volley.newRequestQueue(this)
        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager
        adapter = PixabayImagesAdapter(this, imagesList, this)
        recyclerView.adapter = adapter
        loadImages(searchFor, page)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if(dy > 0){
                    pastLoaded = gridLayoutManager.childCount
                    visibleLoaded = gridLayoutManager.itemCount
                    countLoaded = gridLayoutManager.findFirstVisibleItemPosition()

                    if(visibleLoaded + pastLoaded >= countLoaded){
                        loadImages(searchFor, ++page)
                    }
                }
            }
        })
    }

    private fun loadImages(searchFor: String, page: Int) {
        val url = "https://pixabay.com/api/?key=8585575-4820004a023cebe04decbec39&q=${Uri.encode(searchFor)}&image_type=photo&page=$page"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject?) {
                        try{
                            val jsonArray = response!!.getJSONArray("hits")
                            for(i in 0 until jsonArray.length()){
                                val eachObject = jsonArray.getJSONObject(i)
                                imagesList.add(PixabayImages(eachObject.getString("tags"), eachObject.getString("webformatURL"),
                                        eachObject.getInt("favorites"), eachObject.getInt("likes"), eachObject.getInt("comments"),
                                        eachObject.getString("user")))

                            }
                        }catch (e : JSONException){
                            e.printStackTrace()
                        }
                        val position = imagesList.size
                        adapter.notifyItemRangeInserted(position, position + 20)
                        Log.d(TAG, "onReponse : ${response.toString()}")
                    }
                }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError?) {
                Log.d(TAG, "onError : ${error.toString()}")
            }

        })

        requestQueue.add(request)
    }
}
