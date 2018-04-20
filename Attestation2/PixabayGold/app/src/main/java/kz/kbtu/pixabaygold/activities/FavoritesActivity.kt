package kz.kbtu.pixabaygold.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_favorites.*
import kz.kbtu.pixabaygold.R
import kz.kbtu.pixabaygold.adapters.FavoritesAdapter
import kz.kbtu.pixabaygold.helpers.Parent
import kz.kbtu.pixabaygold.helpers.Utils

class FavoritesActivity : AppCompatActivity() {

    var list = ArrayList<Parent>()
    lateinit var adapter : FavoritesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        recycler_favs.layoutManager = GridLayoutManager(this, 2)
        adapter = FavoritesAdapter(this, list)
        recycler_favs.adapter = adapter
        val imageList = Utils.getFavsImages()
        val videosList = Utils.getFavsVideos()

        list.addAll(imageList)
        list.addAll(videosList)

        adapter.notifyDataSetChanged()

    }
}
