package kz.kbtu.kitappgold

import android.widget.AbsListView

/**
 * Created by abakh on 26-Mar-18.
 */
abstract class EndlessScrollListener : AbsListView.OnScrollListener {
    var visibleThreshold = 5
    var currentPage = 0
    var previousTotalItemCount = 0
    var loading = true
    var startingPageIndex = 0


    constructor(){

    }

    constructor(threshold : Int){
        this.visibleThreshold = threshold
    }

    constructor(threshold: Int, startPage : Int){
        this.visibleThreshold = threshold
        this.startingPageIndex = startPage
        this.currentPage = startPage
    }
    override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        if(totalItemCount < previousTotalItemCount){
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if(totalItemCount == 0) this.loading = true
        }

        if(loading && totalItemCount > previousTotalItemCount){
            loading = false
            previousTotalItemCount = totalItemCount
            currentPage++
        }

        if(!loading && (firstVisibleItem + visibleItemCount + visibleThreshold) >= totalItemCount){
            loading = onLoadMore(currentPage + 1, totalItemCount)
        }
    }

    abstract fun onLoadMore(page: Int, totalItemCount: Int): Boolean

    override fun onScrollStateChanged(p0: AbsListView?, p1: Int) {

    }
}