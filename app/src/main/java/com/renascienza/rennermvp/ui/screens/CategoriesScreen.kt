package com.renascienza.rennermvp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.renascienza.rennermvp.R
import com.renascienza.rennermvp.model.Categoria
import com.renascienza.rennermvp.model.Produto
import com.renascienza.rennermvp.ui.theme.RennerMVPTheme
import com.renascienza.rennermvp.ui.viewmodel.HomeUiState
import com.renascienza.rennermvp.ui.viewmodel.HomeViewModel

/**
 * Tela com uma coleção (o conteúdo de uma oferta)
 */
@Composable
fun CategoriesScreen(
    model               : HomeViewModel,
    categoria           : Categoria,
    onClickProduto      :(Produto) -> Unit,
    onClickFavorito     :(Produto) -> Unit
){
    val uiState = model.uiState.collectAsState().value
    when (uiState) {
        is HomeUiState.HasData -> {

            val produtosDaCategoria = uiState.produtos.filter { prod ->
                prod.categorias.contains(categoria)
            }

            if (produtosDaCategoria.isNotEmpty()) {

                Column(Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = categoria.nome,
                        style = RennerMVPTheme.typography.h4,
                        color = RennerMVPTheme.colors.onBackground
                    )
                    GridSimplesDeProdutos(
                        uiState = uiState,
                        produtos = produtosDaCategoria,
                        onClickProduto = onClickProduto,
                        onFavorito = onClickFavorito
                    )
                }

            } else {
                SemConteudo()
            }
        }
        is HomeUiState.NoData -> {
            SemConteudo()
        }
    }
}

@Composable
private fun SemConteudo() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 128.dp, start = 24.dp, end = 24.dp),
            text = stringResource(id = R.string.no_offer),
            textAlign = TextAlign.Center,
            style = RennerMVPTheme.typography.h4,
            color = RennerMVPTheme.colors.onBackground
        )
        Image(
            imageVector = Icons.Outlined.List,
            contentDescription = stringResource(id = R.string.no_products_category),
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(RennerMVPTheme.colors.secondary),
            modifier = Modifier
                .padding(top = 48.dp)
                .width(128.dp)
                .height(128.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}