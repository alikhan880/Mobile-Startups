package kz.kbtu.eggstossgold

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        startBtn.setOnClickListener {
            val intent = Intent(this, LevelActivity :: class.java)
            startActivity(intent)
            finish()
        }
    }
}
