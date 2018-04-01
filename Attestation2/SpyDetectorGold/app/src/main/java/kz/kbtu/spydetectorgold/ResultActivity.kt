package kz.kbtu.spydetectorgold

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    var cntCorrect : Int? = null
    var cntIncorrect : Int? = null
    var name : String? = null
    var list : ArrayList<Result>? = null
    var recyclerAdapter : RecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        ////
        list = UtilsSharedPrefs.getResults()
//        for(x in list!!){
//            Log.d("list", x.toString())
//        }
        ////

        list!!.sortedWith(compareBy<Result>{it.name}.thenBy{it.incorrect}.thenBy{it.correct})

        recyclerAdapter = RecyclerAdapter(list!!)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = recyclerAdapter


        val intent = getIntent()
        cntCorrect = intent.getIntExtra("correct",  -1)
        cntIncorrect = intent.getIntExtra("incorrect", -1)

        btn_save.setOnClickListener{
            if(et_name.text.isEmpty())
                Toast.makeText(this, "Имя не должно быть пустым", Toast.LENGTH_SHORT).show()
            else{
                name = et_name.text.toString()
                container_save.visibility = View.GONE
                addToList()
            }
        }


    }

    private fun addToList() {
        val result = Result(name!!, cntCorrect!!, cntIncorrect!!)
        list!!.add(result)
        list!!.sortedWith(compareBy<Result>{it.name}.thenBy{it.incorrect}.thenBy{it.correct})
        UtilsSharedPrefs.saveResult(list!!)
        recyclerAdapter!!.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
