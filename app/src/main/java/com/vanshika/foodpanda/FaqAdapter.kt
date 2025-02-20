package com.vanshika.foodpanda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FaqAdapter(val context: Context, private val FaqList:List<FaqData>) : RecyclerView.Adapter<FaqAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.faq_list,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return FaqList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=FaqList[position]
        holder.question.text= currentItem.quesList
        holder.answer.text=currentItem.ansList
    }

    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        val question=itemView.findViewById<TextView>(R.id.smallText)
        val answer=itemView.findViewById<TextView>(R.id.detailText)
    }
}