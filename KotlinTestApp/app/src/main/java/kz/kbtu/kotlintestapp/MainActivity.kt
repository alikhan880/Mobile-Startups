package kz.kbtu.kotlintestapp

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {



    lateinit var btnClick : Button
    lateinit var rootView : LinearLayout
    var random = Random()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
    }

    private fun bindViews(){
        btnClick = findViewById(R.id.button)
        rootView = findViewById(R.id.layout)
        btnClick.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.button -> buttonClicked()
        }
    }

    private fun buttonClicked() {
        rootView.setBackgroundColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
        onClick(null)
    }

    private fun isPrime(a : Int) : Boolean{
        return a % 2 != 0
    }


}
