package com.vanshika.foodpanda

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.compose.ui.res.stringResource
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FAQsFragment : Fragment() {

    lateinit var faqRecyclerView: RecyclerView
    lateinit var quesList: ArrayList<String>
    lateinit var ansList: ArrayList<String>
    lateinit var faqList:ArrayList<FaqData>

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view=inflater.inflate(R.layout.fragment_f_a_qs, container, false)

        val expandable= view.findViewById<CardView>(R.id.expandable1)
        val layout = view.findViewById<LinearLayout>(R.id.layout1)
        val detailText = view.findViewById<TextView>(R.id.detailText1)

        layout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        expandable.setOnClickListener{
            if (detailText.visibility == View.GONE){
                detailText.visibility=View.VISIBLE
            } else{
                detailText.visibility=View.GONE
            }
        }
        val expandable2= view.findViewById<CardView>(R.id.expandable2)
        val layout2 = view.findViewById<LinearLayout>(R.id.layout2)
        val detailText2 = view.findViewById<TextView>(R.id.detailText2)

        layout2.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        expandable2.setOnClickListener{
            if (detailText2.visibility == View.GONE){
                detailText2.visibility=View.VISIBLE
            } else{
                detailText2.visibility=View.GONE
            }
        }
        val expandable3= view.findViewById<CardView>(R.id.expandable3)
        val layout3= view.findViewById<LinearLayout>(R.id.layout3)
        val detailText3= view.findViewById<TextView>(R.id.detailText3)

        layout3.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        expandable3.setOnClickListener{
            if (detailText3.visibility == View.GONE){
                detailText3.visibility=View.VISIBLE
            } else{
                detailText3.visibility=View.GONE
            }
        }
        val expandable4= view.findViewById<CardView>(R.id.expandable4)
        val layout4= view.findViewById<LinearLayout>(R.id.layout4)
        val detailText4= view.findViewById<TextView>(R.id.detailText4)

        layout4.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        expandable4.setOnClickListener{
            if (detailText4.visibility == View.GONE){
                detailText4.visibility=View.VISIBLE
            } else{
                detailText4.visibility=View.GONE
            }
        }


        return view
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()

        faqRecyclerView=view.findViewById(R.id.faqRecycler)
        faqRecyclerView.layoutManager=LinearLayoutManager(context)

        faqRecyclerView.adapter= context?.let { FaqAdapter(it,faqList) }

    }

    fun getData(){
        faqList = arrayListOf()

        quesList= arrayListOf("How do I place an order?","What payment methods are accepted?",
            "How can I track my order?","Can I customize my order?","Can I cancel my order?",
            "How do I request a refund?","Can I change my delivery address after placing the order?",
            "How do I contact customer support?","Can I give feedback on my order?")

        ansList= arrayListOf(getString(R.string.faqText1),
            getString(R.string.faqText2),
            getString(R.string.faqText3),
            getString(R.string.faqText4),
            getString(R.string.faqText5),
            getString(R.string.faqText6),
            getString(R.string.faqText7),
            getString(R.string.faqText8),
            getString(R.string.faqText9))

        for (i in quesList.indices){
            val data=FaqData(quesList[i],ansList[i])
            faqList.add(data)
        }
    }*/
}