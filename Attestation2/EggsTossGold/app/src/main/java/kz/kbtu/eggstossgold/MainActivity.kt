package kz.kbtu.eggstossgold

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var life = 4
    var leftPos = 0.0f
    var centerPos = 0.0f
    var rightPos = 0.0f
    var spawnPos = 0.0f
    var eggPos = 0
    var score = 0
    var basketPos = 1
    var timer: CountDownTimer? = null
    var speed = 15
    var isSuperEggOnScreen = false
    var level = 0
    lateinit var rl: RelativeLayout
    lateinit var egg: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rl = findViewById(R.id.parent)
        egg = ImageView(this)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val screenW = size.x.toFloat()
        val const = 50
        level = intent.getIntExtra("level", 0)

        placeBasket(level)


        leftPos = (screenW / 6) - const
        centerPos = (screenW / 6) * 3 - const
        rightPos = (screenW / 6) * 5 - const

        leftBrokenEgg.visibility = View.INVISIBLE
        centerBrokenEgg.visibility = View.INVISIBLE
        rightBrokenEgg.visibility = View.INVISIBLE



        leftButton.setOnClickListener {
            basket.x = leftPos - basket.width / 4
            basketPos = 0
        }
        centerButton.setOnClickListener {
            basket.x = centerPos - basket.width / 2
            basketPos = 1
        }
        rightButton.setOnClickListener {
            basket.x = rightPos - basket.width / 4 * 3
            basketPos = 2
        }

        createEgg()
        setLifeImage()

    }

    private fun placeBasket(level: Int) {
        when(level){
            2 -> {basket.y = basket.y - 150f}
            3 -> {basket.y = basket.y - 300f}
        }
        basket.invalidate()
    }

    private fun createEgg() {
        rl.removeView(egg)
        val random = Random()
        eggPos = random.nextInt(3)

        when (eggPos) {
            0 -> spawnPos = leftPos
            1 -> spawnPos = centerPos
            2 -> spawnPos = rightPos
        }

        val randomEgg = random.nextInt(1111)
        egg = ImageView(this)

        if(randomEgg % 11 == 0){
            egg.setImageResource(R.drawable.superegg)
            isSuperEggOnScreen = true
        }
        else{
            egg.setImageResource(R.drawable.egg)
        }
        rl.addView(egg)
        egg.x = spawnPos
        egg.y = -50.0f
        toss()
    }

    private fun toss() {
        timer = object : CountDownTimer(4500, 1) {

            override fun onTick(p0: Long) {
                egg.y = egg.y + speed
                if ((egg.y > basket.y) && (egg.y < basket.y + basket.height) && (basketPos == eggPos)) {
                    score++
                    if(isSuperEggOnScreen && life < 5){
                        life++
                        isSuperEggOnScreen = false
                        setLifeImage()
                    }
                    if(score % 5 == 0 && score > 0) speed++
                    scoreTextView.text = score.toString()
                    timer!!.cancel()
                    createEgg()
                } else if (egg.y >= bottomLL.y + 350) {
                    life--
                    timer!!.cancel()
                    setLifeImage()
                    when (eggPos) {
                        0 -> leftBrokenEgg.visibility = View.VISIBLE
                        1 -> centerBrokenEgg.visibility = View.VISIBLE
                        2 -> rightBrokenEgg.visibility = View.VISIBLE
                    }

                    if (life < 0) {
                        val intent = Intent(baseContext, FinishScreen::class.java)
                        intent.putExtra("score", score)
                        startActivity(intent)
                    } else {
                        createEgg()
                    }

                }
            }

            override fun onFinish() {
                rl.removeView(egg)
                createEgg()
            }


        }.start()
    }

    fun setLifeImage() {
        eggs.removeAllViews()
        for (i in 4 downTo 0) {
            if (life - i < 0) {
                createLifeImage(R.drawable.brokenegg)
            } else
                createLifeImage(R.drawable.defaultegg)
        }
    }

    private fun createLifeImage(drawable: Int) {
        val eggLife = ImageView(this)
        eggLife.setImageResource(drawable)
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.setMargins(2, 2, 2, 2)
        eggLife.layoutParams = lp
        eggs.addView(eggLife)
    }
}
