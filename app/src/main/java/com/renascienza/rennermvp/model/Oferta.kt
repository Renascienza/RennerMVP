package com.renascienza.rennermvp.model

import androidx.annotation.DrawableRes

data class Oferta(
    val codigo      :String,
    val nome        :String,
    val descricao   :String = "",
    val produtos    :List<Produto> = emptyList(),
    /* Como esse é apenas um demo, vamos trabalhar com imagens embarcadas no próprio aplicativo,
        já que isso tira do projeto a dependência da rede e a prova não exige armazenamento
        em database ou qualquer serviço de backend
    */
    @DrawableRes val photo: Int
)