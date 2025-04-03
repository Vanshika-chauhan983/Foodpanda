package com.vanshika.foodpanda.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vanshika.foodpanda.DataClasses.FaqData
import com.vanshika.foodpanda.R
import com.vanshika.foodpanda.adapters.FaqAdapter

class FAQsFragment : Fragment() {
    private val faqList = mutableListOf<FaqData>()
    private lateinit var faqRecyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_f_a_qs, container, false)

        faqRecyclerView = view.findViewById(R.id.faqRecycler)
        faqRecyclerView.layoutManager = LinearLayoutManager(context)

        addData()

        faqRecyclerView.adapter = context?.let { FaqAdapter(it, faqList) }

        return view
    }

    private fun addData() {
        faqList.add(
            FaqData(
                "How do I place an order?",
                "Browse the menu, select the items you want, customize them if needed, and add them to your cart. When ready, tap Checkout, choose your delivery address, and confirm the payment method."
            )
        )
        faqList.add(
            FaqData(
                "What payment methods are accepted?",
                "We accept credit/debit cards, mobile wallets, cash on delivery, and other popular payment methods in your region."
            )
        )
        faqList.add(
            FaqData(
                "How do I request a refund?",
                "If there’s an issue with your order, contact our support team through the app with details, and we’ll investigate and process a refund if applicable."
            )
        )
        faqList.add(
            FaqData(
                "How do I contact costumer support?",
                "Use the in-app chat feature or email us at support@yourapp.com for help with your queries."
            )
        )
        faqList.add(
            FaqData(
                "Where should I share my feedback?",
                "Absolutely! Rate your experience and provide feedback after your order is delivered through the \"Rate &amp; Review\" section."
            )
        )
        faqList.add(
            FaqData(
                "How do I cancel my order?",
                "Orders can be canceled only before they are accepted by the restaurant. Go to \"My Orders,\" select the order, and tap \"Cancel.\""
            )
        )
    }
}