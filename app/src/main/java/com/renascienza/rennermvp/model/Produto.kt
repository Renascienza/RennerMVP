package com.renascienza.rennermvp.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import java.math.BigDecimal

data class Produto(
    val ref             :String,
    val nome            :String,
    val marca           :String,
    val valor           :BigDecimal,
    val detalhesPgmto   :String,
    val cores           :Map<String, Color> = emptyMap(),
    val tamanhos        :List<String> = emptyList(),
    val categorias      :List<Categoria> = emptyList(),
    var destaque        :Boolean,
    var desconto        :Double,
    /*Como esse é apenas um demo, vamos trabalhar com imagens embarcadas no próprio aplicativo,
        já que isso tira do projeto a dependência da rede e a prova não exige armazenamento
        em database ou qualquer serviço de backend
     */
    @DrawableRes val photo: Int,
    @DrawableRes val thumbnail: Int
) {

    fun emPromocao() :Boolean{
        return desconto > 0
    }

}