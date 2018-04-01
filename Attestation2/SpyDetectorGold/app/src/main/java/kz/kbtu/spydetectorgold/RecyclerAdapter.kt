package kz.kbtu.spydetectorgold

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_list.view.*

/**
 * Created by abakh on 15-Mar-18.
 */
class RecyclerAdapter(private val list : ArrayList<Result>) : RecyclerView.Adapter<RecyclerAdapter.ResultHolder>(){
    override fun onBindViewHolder(holder: RecyclerAdapter.ResultHolder, position: Int) {
        val item = list.get(position)
        holder.bindItem(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerAdapter.ResultHolder{
        val rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_list, parent, false)
        return ResultHolder(rootView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ResultHolder(var v : View) : RecyclerView.ViewHolder(v){


        fun bindItem(item: Result) {
            v.item_name.text = "Name: ${item.name}"
            v.item_correct.text = "Right: ${item.correct}"
            v.item_incorrect.text = "Wrong: ${item.incorrect}"
        }

    }

}