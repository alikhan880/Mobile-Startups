package kz.kbtu.kitappgold

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_result.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ResultActivity : AppCompatActivity() {
    val TAG = "Main"
    val orderTypes = arrayOf("relevance", "newest")
    val printTypes = arrayOf("all", "books", "magazines")
    var adapterOrder: ArrayAdapter<String>? = null
    var adapterTypes: ArrayAdapter<String>? = null
    var bookName: String? = null
    var bookList: ArrayList<Book>? = null
    var adapter: BooksAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        bookName = intent.extras.get("bookName")?.toString()
        bookNameEditText.setText(bookName)
        bookList = ArrayList()

        searchButton.setOnClickListener {
            val bookName = bookNameEditText.text.toString()
            if (bookName == "") {
                Toast.makeText(this, "Введите название книги", Toast.LENGTH_SHORT).show()
            } else {
                onSearchClick(bookName)
            }
        }

        adapter = BooksAdapter(this, bookList)
        resultListView.adapter = adapter
        resultListView.setOnScrollListener(object : EndlessScrollListener(){
            override fun onLoadMore(page: Int, totalItemCount: Int): Boolean {
                makeRequest(page)
                return true
            }

        })

        resultListView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, Info :: class.java)
            intent.putExtra("title", bookList!![i].title)
            intent.putExtra("authors", bookList!![i].author)
            intent.putExtra("description", bookList!![i].description)
            intent.putExtra("pageCount", bookList!![i].pagesCount)
            intent.putExtra("image", bookList!![i].imageLink)


            startActivity(intent)
        }

        adapterOrder = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, orderTypes)
        adapterTypes = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, printTypes)

        spinner_order.adapter = adapterOrder
        spinner_type.adapter = adapterTypes

        spinner_type.setSelection(0)
        spinner_order.setSelection(0)

        onSearchClick(bookName!!)
    }

    private fun onSearchClick(bookName: String) {
        val order = spinner_order.selectedItem
        Log.d(TAG, order.toString())
        val printType = spinner_type.selectedItem
        Log.d(TAG, printType.toString())
//        val url = "https://www.googleapis.com/books/v1/volumes?q=${bookName.replace(" ", "%20")}"
        bookList!!.clear()
        makeRequest(1)
    }

    fun makeRequest(page : Int){
        val url = "https://www.googleapis.com/books/v1/volumes?q=${bookName!!.replace(" ", "%20")}&orderBy=${spinner_order.selectedItem}" +
                "&printType=${spinner_type.selectedItem}&startIndex=${page * 10}&maxResults=10"
        val queue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET, url, null, object : Response.Listener<JSONObject> {
            override fun onResponse(response: JSONObject?) {
                Log.d(TAG, response.toString())
                try {
                    val books = response!!.getJSONArray("items")
                    Log.d(TAG, books.length().toString())
                    val temp = parse(books)
                    bookList!!.addAll(temp)
                    adapter!!.notifyDataSetChanged()
                    noResult.visibility = View.INVISIBLE
                    resultListView.visibility = View.VISIBLE
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.d(TAG, "onResponse:catch")
                    noResult.visibility = View.VISIBLE
                    resultListView.visibility = View.INVISIBLE
                }
            }
        }, Response.ErrorListener { error ->
            Log.e(TAG, error.message)
        })
        queue.add(request)
    }


    private fun parse(books: JSONArray?) : ArrayList<Book>{
        val tempArray = ArrayList<Book>()
        for (i in 0 until books!!.length()) {
            val item = books.getJSONObject(i)
            val volumeInfo = item.getJSONObject("volumeInfo")
            val bookName = volumeInfo.getString("title")
            var subtitle: String? = null
            if (volumeInfo.has("subtitle")) subtitle = volumeInfo.getString("subtitle")
            var author: String? = null
            if (volumeInfo.has("author")) author = volumeInfo.getJSONArray("authors").get(0).toString()
            var description: String? = null
            if (volumeInfo.has("description")) description = volumeInfo.getString("description")
            var pagesCount: String? = null
            if (volumeInfo.has("pageCount")) pagesCount = volumeInfo.getString("pageCount")
            var imageLink: String? = null
            if (volumeInfo.has("imageLinks")) imageLink = volumeInfo.getJSONObject("imageLinks").getString("thumbnail")
            tempArray.add(Book(bookName, subtitle, author, description, pagesCount, imageLink))
        }
        return tempArray
    }
}
