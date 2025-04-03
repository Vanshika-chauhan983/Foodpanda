package com.vanshika.foodpanda.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vanshika.foodpanda.DataClasses.FaqData
import com.vanshika.foodpanda.R

class FaqAdapter(val context: Context, private val FaqList:List<FaqData>) : RecyclerView.Adapter<FaqAdapter.ViewHolder>() {
    class ViewHolder(itemview:View) : RecyclerView.ViewHolder(itemview){
        val questiontv:TextView=itemview.findViewById(R.id.question_text)
        val answertv:TextView=itemview.findViewById(R.id.answer_text)
        val constraintLayout:ConstraintLayout=itemview.findViewById(R.id.constraint_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.faq_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return FaqList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = FaqList[position]
        holder.questiontv.text=currentItem.question
        holder.answertv.text= currentItem.answer.toString()

        var isExpandable:Boolean=currentItem.isExpanded
        holder.answertv.visibility=if (isExpandable) View.VISIBLE else View.GONE

        holder.constraintLayout.setOnClickListener{
            currentItem.isExpanded=!currentItem.isExpanded
            notifyItemChanged(position)
        }
    }
}