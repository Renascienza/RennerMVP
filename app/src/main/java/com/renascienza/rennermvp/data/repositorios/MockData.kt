package com.renascienza.rennermvp.data.repositorios

import androidx.compose.ui.graphics.Color
import com.renascienza.rennermvp.R
import com.renascienza.rennermvp.model.Categoria
import com.renascienza.rennermvp.model.Oferta
import com.renascienza.rennermvp.model.Produto

object MockData {

    val categorias = listOf<Categoria>(
        Categoria(
            codigo = "cat:fem",
            nome = "Feminino",
            R.drawable.categoria_fem
        ),
        Categoria(
            codigo = "cat:masc",
            nome = "Masculino",
            R.drawable.categoria_masc
        ),
        Categoria(
            codigo = "cat:inf",
            nome = "Infantil",
            R.drawable.categoria_infantil
        ),
        Categoria(
            codigo = "cat:bel",
            nome = "Beleza",
            R.drawable.categoria_beleza
        ),
        Categoria(
            codigo = "cat:casa",
            nome = "Casa",
            R.drawable.categoria_casa
        ),
        Categoria(
            codigo = "cat:acc",
            nome = "Acessórios",
            R.drawable.categoria_acessorios
        )
    )

    val produtos = listOf<Produto>(
        //#0
        Produto(
            ref ="PFI001",
            nome ="PERFUME LANCÔME LA VIE EST BELLE FEMININO EAU DE PARFUM",
            marca ="Lancome",
            valor = 449.90.toBigDecimal(),
            detalhesPgmto = "10x de R\$ 49,90 sem juros no cartão Renner",
            cores = emptyMap(),
            tamanhos = listOf("50ml"),
            categorias = listOf(categorias[3]),
            true,
            0.0,
            R.drawable.lancome,
            R.drawable.lancome
        ),
        //#1
        Produto(
            ref ="INF001",
            nome ="BLUSÃO INFANTIL COM ESTAMPA FORTNITE - TAM PP AO G AZUL",
            marca ="Fortnite",
            valor = 139.90.toBigDecimal(),
            detalhesPgmto = "8x de R\$ 17,49* sem juros no Cartão Renner",
            cores = mapOf ("Azul" to Color.Blue),
            tamanhos = listOf("PP", "P", "M", "G"),
            categorias = listOf(categorias[2]),
            false,
            0.0,
            R.drawable.blusao_fortnite,
            R.drawable.blusao_fortnite
        ),
        //#2
        Produto(
            ref ="MASC001",
            nome ="CAMISETA MANGA LONGA ESTAMPA VENICE BEACH PRETO",
            marca ="Ripping",
            valor = 29.90.toBigDecimal(),
            detalhesPgmto = "1x de R\$ 29,90* sem juros no Cartão Renner",
            cores = mapOf("Preto" to Color.Black),
            tamanhos = listOf("PP", "P", "M", "GG"),
            categorias = listOf(categorias[1]),
            false,
            50.0,
            R.drawable.camiseta_longa,
            R.drawable.camiseta_longa
        ),
        //#3
        Produto(
            ref ="FEM001",
            nome ="CASACO ALONGADO EM POLIVELOUR XADREZ COM ABOTOAMENTO FRONTAL PRETO",
            marca ="Blue Steel",
            valor = 339.90.toBigDecimal(),
            detalhesPgmto = "10x de R\$ 39,99* sem juros no Cartão Renner",
            cores = mapOf("Preto" to Color.Black),
            tamanhos = listOf("PP", "P", "M", "G", "GG"),
            categorias = listOf(categorias[0]),
            true,
            0.0,
            R.drawable.casaco_xadrez,
            R.drawable.casaco_xadrez
        ),
        //#4
        Produto(
            ref ="MASC002",
            nome ="CAMISETA MANGA LONGA ESTAMPA LETTERING LOS ANGELES",
            marca ="Ripping",
            valor = 29.90.toBigDecimal(),
            detalhesPgmto = "1x de R\$ 29,90*  sem juros no Cartão Renner",
            cores = mapOf("Preto" to Color.Black, "Branco" to Color.White),
            tamanhos = listOf("PP", "P", "M", "G", "GG"),
            categorias = listOf(categorias[1]),
            true,
            50.0,
            R.drawable.camiseta_lettering,
            R.drawable.camiseta_lettering
        ),
        //#5
        Produto(
            ref ="FEM015",
            nome ="Kit de maquiagem: paleta multifuncional, glos e máscara de cílios Alchemia",
            marca ="Alchemia",
            valor = 99.90.toBigDecimal(),
            detalhesPgmto = "5x de R\$ 19,98*  s/ juros no Cartão Renner",
            cores = emptyMap(),
            tamanhos = emptyList(),
            categorias = listOf(categorias[3], categorias[0]),
            false,
            0.0,
            R.drawable.kit_maquiagem,
            R.drawable.kit_maquiagem
        ),
        //#6
        Produto(
            ref ="MASC015",
            nome ="Relógio Lince com Pulseira em aço e caixa em metal",
            marca ="Lince",
            valor = 279.90.toBigDecimal(),
            detalhesPgmto = "10x de R\$ 27,99*  sem juros no Cartão Renner",
            cores = mapOf("Preto" to Color.Black),
            tamanhos = listOf("U"),
            categorias = listOf(categorias[1], categorias[5]),
            false,
            0.0,
            R.drawable.relogio_lince,
            R.drawable.relogio_lince
        )
    )

    val ofertas = listOf<Oferta>(
        Oferta(
            codigo = "of:masc001",
            nome   = "No mood de camisas",
            descricao = "Visual arrumado com as cores da estação",
            produtos = listOf(produtos[2], produtos[4]),
            R.drawable.mood_camisas
        ),
        Oferta(
            codigo = "of:fem001",
            nome   = "Versáteis e Elegantes",
            descricao = "Looks do PP ao G2 que te deixam pronta para qualquer ocasião",
            produtos = listOf(produtos[3]),
            R.drawable.elegantes
        ),
        Oferta(
            codigo = "of:bel001",
            nome   = "Alchemia",
            descricao = "Cuidado, beleza e bem estar",
            produtos = listOf(produtos[5]),
            R.drawable.alchemia
        ),
        Oferta(
            codigo = "of:bel002",
            nome   = "Dia das Mães",
            descricao = "Perfumes e kits para surpreender nesse dia especial",
            produtos = listOf(produtos[0]),
            R.drawable.dia_das_maes
        ),
        Oferta(
            codigo = "of:acc002",
            nome   = "É tempo de presentear",
            descricao = "Relógios e kits para todos os estilos",
            produtos = listOf(produtos[6]),
            R.drawable.tempo_de_presentear
        )
    )
}