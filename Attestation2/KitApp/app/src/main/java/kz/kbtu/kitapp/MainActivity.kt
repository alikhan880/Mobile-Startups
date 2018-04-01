package kz.kbtu.kitapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchButton.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("bookName", bookNameEditText.text.toString())
            startActivity(intent)
        }
    }

}
