package kz.kbtu.kitappbronze

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val bookName = intent.extras.get("bookName")?.toString()
        bookNameEditText.setText(bookName)

        searchButton.setOnClickListener {
            val bookName = bookNameEditText.text.toString()
            if(bookName == ""){
                Toast.makeText(this, "Введите название книги", Toast.LENGTH_SHORT).show()
            }
            else{
                onSearchClick(bookName)
            }
        }
        onSearchClick(bookName!!)
    }

    private fun onSearchClick(bookName: String) {
        val url = "https://www.googleapis.com/books/v1/volumes?q=${bookName.replace(" ", "%20")}"
        val queue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET, url, null , object : Response.Listener<JSONObject>{
            override fun onResponse(response: JSONObject?) {
                Log.d(TAG, response.toString())
                try{
                    val books = response!!.getJSONArray("items")
                    Log.d(TAG, books.length().toString())
                    display(books)
                    noResult.visibility = View.INVISIBLE
                    resultListView.visibility = View.VISIBLE
                }
                catch (e : JSONException){
                    e.printStackTrace()
                    Log.d(TAG, "onResponse:catch")
                    noResult.visibility = View.VISIBLE
                    resultListView.visibility = View.INVISIBLE
                }
            }
        }, Response.ErrorListener {
            error ->  Log.e(TAG, error.message)
        })
        queue.add(request)
    }

    private fun display(books: JSONArray?) {
        val adapter = BooksAdapter(this, books)
        resultListView.adapter = adapter
        val intent = Intent(this, Info :: class.java)
        resultListView.setOnItemClickListener { adapterView, view, i, l ->
            try {
                val book = books!!.getJSONObject(i)
                val volumeInfo = book.getJSONObject("volumeInfo")


                val title = volumeInfo.getString("title")
                intent.putExtra("title", title)
                val author = volumeInfo.getString("authors")
                intent.putExtra("authors", author)
                val desc = volumeInfo.getString("description")
                intent.putExtra("description", desc)

                val pagesCount = volumeInfo.getString("pageCount")
                intent.putExtra("pageCount", pagesCount)
//                Log.d(TAG, "Pages: " + pagesCount.toString())

                val imageLinks = volumeInfo.getJSONObject("imageLinks")
                val thumbnailLink = imageLinks.getString("thumbnail")
                intent.putExtra("image", thumbnailLink)
            }
            catch (e : JSONException){
                e.printStackTrace()
            }
            startActivity(intent)
        }
    }
}
