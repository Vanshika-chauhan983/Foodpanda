package com.vanshika.foodpanda.DataClasses

data class FaqData(
    val question: String,
    val answer: String,
    var isExpanded: Boolean = false
)
