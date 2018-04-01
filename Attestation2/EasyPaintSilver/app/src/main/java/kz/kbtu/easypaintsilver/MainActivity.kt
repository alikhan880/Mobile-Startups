package kz.kbtu.easypaintsilver

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var paintView : PaintView
    private lateinit var clearAll : Button
    private lateinit var buttonPlus : Button
    private lateinit var buttonMinus : Button
    private lateinit var paintSizeView : TextView
    private lateinit var radioGroup : RadioGroup
    private var paintSize = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView.init(metrics)
    }

    private fun bindViews() {
        paintView = findViewById(R.id.paintView)
        clearAll = findViewById(R.id.clearAll)
        buttonPlus = findViewById(R.id.buttonPlus)
        buttonMinus = findViewById(R.id.buttonMinus)
        paintSizeView = findViewById(R.id.paintSizeView)
        radioGroup = findViewById(R.id.radio_group)
        clearAll.setOnClickListener(this)
        buttonPlus.setOnClickListener(this)
        buttonMinus.setOnClickListener(this)
        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                val button : RadioButton? = p0!!.findViewById(p1)
                if(button!!.isChecked){
                    when(p1){
                        R.id.radio_black ->{
                            paintView.color = Color.BLACK
                        }
                        R.id.radio_red -> {
                            paintView.color = Color.RED
                        }
                        R.id.radio_blue -> {
                            paintView.color = Color.BLUE
                        }
                        R.id.radio_eraser -> {
                            paintView.color = PaintView.BG_COLOR
                        }
                    }
                }

            }
        })
        radioGroup.check(R.id.radio_black)

    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.buttonPlus -> onClickPlus()
            R.id.buttonMinus -> onClickMinus()
            R.id.clearAll -> onClickClear()
        }
    }

    private fun onClickClear() {
        paintView.clear()
    }

    private fun onClickMinus() {
        paintSize -= 4
        if(paintSize <= 0) paintSize = 1
        paintSizeView.text = paintSize.toString()
        setPaintSize()
    }

    private fun onClickPlus() {
        paintSize += 4
        if(paintSize > 60) paintSize = 60
        paintSizeView.text = paintSize.toString()
        setPaintSize()
    }

    private fun setPaintSize(){
        paintView.setBrushSize(paintSize)
    }
}
