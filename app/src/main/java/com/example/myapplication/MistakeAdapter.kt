package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import kotlinx.android.synthetic.main.item_mistake.view.*

class MistakeAdapter(
    private val mistakes: MutableList<Mistake>
) : RecyclerView.Adapter<MistakeAdapter.MistakeViewHolder>() {
    class MistakeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MistakeViewHolder {
        return MistakeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_mistake,
                parent,
                false
            )
        )
    }

    fun addMistake(mistake: Mistake){
        mistakes.add(mistake)
        notifyItemInserted(mistakes.size -1)
    }




    override fun onBindViewHolder(holder: MistakeViewHolder, position: Int) {
        val curmistake = mistakes[position]
        holder.itemView.apply {
//            R.id.tvmistakeTitle.text = curmistake.title
//            R.id.cbDone.isChecked = curmistake.isChecked
            val mistakeTitleText = findViewById<TextView>(R.id.tvMistakeTitle)
            mistakeTitleText.text = curmistake.title

        }
    }

    override fun getItemCount(): Int {
        return mistakes.size
    }
}