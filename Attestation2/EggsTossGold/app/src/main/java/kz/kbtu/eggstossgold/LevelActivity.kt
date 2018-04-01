package kz.kbtu.eggstossgold

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_level.*

class LevelActivity : AppCompatActivity(), View.OnClickListener {
    var chosen = 0
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.levelEasy -> chosen = 1
            R.id.levelAmateur -> chosen = 2
            R.id.levelHard -> chosen = 3
        }
        val intent = Intent(this, MainActivity :: class.java)
        intent.putExtra("level", chosen)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
        levelEasy.setOnClickListener(this)
        levelHard.setOnClickListener(this)
        levelAmateur.setOnClickListener(this)
    }
}
