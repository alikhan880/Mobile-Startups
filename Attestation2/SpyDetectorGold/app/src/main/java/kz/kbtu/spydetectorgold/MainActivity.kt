package kz.kbtu.spydetectorgold

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val color = arrayOf(Color.RED, Color.GRAY, Color.BLUE, Color.BLACK, Color.GREEN)
    val colorName = arrayOf("Красный", "Серый", "Синий", "Черный", "Зеленый")
    var leftColorIndex = 0
    var rightColorIndex = 0
    var leftTextIndex = 0
    var rightTextIndex = 0
    val random = Random()
    var cntCorrect = 0
    var cntIncorrect = 0
    lateinit var timer : Timer
    var timerCount = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnYes.setOnClickListener {
            if (leftTextIndex == rightColorIndex) {
                correct()
            } else {
                incorrect()
            }
            updateView()
        }

        btnNo.setOnClickListener {
            if (leftTextIndex != rightColorIndex) {
                correct()
            } else {
                incorrect()
            }

            updateView()
        }

        updateView()
    }

    override fun onResume() {
        super.onResume()
        startTimer()
    }

    private fun startTimer(){
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread(object : Runnable{
                    override fun run() {
                        if(timerCount <= 0){
                            Log.d("timer", timerCount.toString())
                            stop()
                            reset()
                            return
                        }
                        tv_timer.text = timerCount.toString()
                        timerCount--
                    }

                })
            }

        }, 0, 1000)
    }

    private fun stop(){
        timer.cancel()
        val intent = Intent(this, ResultActivity :: class.java)
        intent.putExtra("correct", cntCorrect)
        intent.putExtra("incorrect", cntIncorrect)
        startActivity(intent)
    }

    private fun reset() {
        timerCount = 20
        cntCorrect = 0
        cntIncorrect = 0
        updateView()
    }

    private fun incorrect() {
        cntIncorrect++
    }

    private fun correct() {
        cntCorrect++
    }

    fun updateView() {
        leftTextIndex = random.nextInt(colorName.size)
        leftTextView.text = colorName[leftTextIndex]

        leftColorIndex = random.nextInt(color.size)
        leftTextView.setTextColor(color[leftColorIndex])

        rightTextIndex = random.nextInt(colorName.size)
        rightTextView.text = colorName[rightTextIndex]

        rightColorIndex = random.nextInt(color.size)
        rightTextView.setTextColor(color[rightColorIndex])


    }
}
