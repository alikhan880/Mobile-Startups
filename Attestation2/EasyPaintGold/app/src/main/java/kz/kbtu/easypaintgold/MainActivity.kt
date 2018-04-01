package kz.kbtu.easypaintgold

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var paintView : PaintView
    private lateinit var clearAll : Button
    private lateinit var buttonPlus : Button
    private lateinit var buttonMinus : Button
    private lateinit var buttonShare : Button
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
        buttonShare = findViewById(R.id.button_share)
        buttonShare.setOnClickListener(this)
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
            R.id.button_share -> shareClicked()
        }
    }

    private fun shareClicked() {
        val bitmap = getBitmapFromView(paintView)
        try {
            val file = File(this.externalCacheDir, "temp.jpeg")
            val os = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
            file.setReadable(true, false)
            val intent = Intent(android.content.Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
            intent.type = "image/jpeg"
            startActivity(Intent.createChooser(intent, "Share image via"))

        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun getBitmapFromView(v : View) : Bitmap{
        val ans = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(ans)
        val drawable = v.background
        if(drawable != null){
            drawable.draw(canvas)
        }
        else{
            canvas.drawColor(Color.WHITE)
        }
        v.draw(canvas)
        return ans
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
