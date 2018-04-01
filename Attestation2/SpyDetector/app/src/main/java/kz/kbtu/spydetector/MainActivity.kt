package kz.kbtu.spydetector

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
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

    private fun incorrect() {
        Toast.makeText(this, "Неправильно", Toast.LENGTH_SHORT).show()
    }

    private fun correct() {
        Toast.makeText(this, "Правильно", Toast.LENGTH_SHORT).show()
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

        tv_correct.text = cntCorrect.toString()
        tv_incorrect.text = cntIncorrect.toString()

    }
}
