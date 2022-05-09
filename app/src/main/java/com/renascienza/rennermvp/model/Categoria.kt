package com.renascienza.rennermvp.model

import androidx.annotation.DrawableRes


data class Categoria(
    val codigo      :String,
    val nome        :String,
    @DrawableRes val photo: Int
)
