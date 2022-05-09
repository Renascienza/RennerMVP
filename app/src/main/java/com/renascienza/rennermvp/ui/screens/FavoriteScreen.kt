package com.renascienza.rennermvp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.renascienza.rennermvp.R
import com.renascienza.rennermvp.model.Produto
import com.renascienza.rennermvp.ui.theme.Desconto
import com.renascienza.rennermvp.ui.theme.RennerMVPTheme
import com.renascienza.rennermvp.ui.viewmodel.HomeUiState
import com.renascienza.rennermvp.ui.viewmodel.HomeViewModel

/**
 * Tela principal de favoritos. 
 * Recebe o viewModel e controla o estado
 */
@Composable
fun FavoriteScreen(
    model               : HomeViewModel,
    onClickProduto      :(Produto) -> Unit
){
    val uiState = model.uiState.collectAsState().value
    when (uiState) {
        is HomeUiState.HasData -> {

            //Retira favorito da lista, se for clicado (e provoca recomposição)
            val onClickFavorito = { prod: Produto ->
                model.favorito(prod.ref)
            }

            if (uiState.favoritos.isNotEmpty()) {

                val produtosFavoritos = uiState.produtos.filter { prod ->
                    uiState.favoritos.contains(prod.ref)
                }
                Column(Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = "Favoritos",
                        style = RennerMVPTheme.typography.h4,
                        color = RennerMVPTheme.colors.onBackground
                    )
                    GridDeFavoritos(
                        favoritos = produtosFavoritos,
                        onClickProduto = onClickProduto,
                        onFavorito = onClickFavorito
                    )
                }

            } else {
                SemFavoritos()
            }
        }
        is HomeUiState.NoData -> {
            SemFavoritos()
        }
    }
}

@Composable
private fun SemFavoritos() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 128.dp, start = 24.dp, end = 24.dp),
            text = stringResource(id = R.string.no_favorite),
            textAlign = TextAlign.Center,
            style = RennerMVPTheme.typography.h4,
            color = RennerMVPTheme.colors.onBackground
        )
        Image(
            imageVector = Icons.Outlined.Favorite,
            contentDescription = stringResource(id = R.string.favorite),
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

@Preview(showBackground = true)
@Composable
fun SemFavoritosPreview(){
    RennerMVPTheme {
        SemFavoritos()
    }
}

@Composable
fun GridDeFavoritos(favoritos :List<Produto>, onClickProduto: (Produto) -> Unit, onFavorito :(Produto) -> Unit) {
    StaggeredVerticalGrid(
        maxColumnWidth = 220.dp,
        modifier = Modifier.padding(4.dp)
    ) {
        favoritos.forEach { produto ->
            FavoritoUI(produto, onClickProduto, onFavorito)
        }
    }
}

/**
 * Basicamente um visual de produto que não requer controle de estado
 * (desmarcar o favorito deverá remover o item da lista)
 */
@Composable
fun FavoritoUI(produto: Produto, onClickProduto: (Produto) -> Unit, onFavorite :(Produto) -> Unit) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .width(200.dp)
            .clickable { onClickProduto(produto) },
        color = MaterialTheme.colors.surface,
        elevation = RennerMVPTheme.elevations.card,
        shape = MaterialTheme.shapes.medium
    ) {
        Column {
            Box{
                Image(
                    painter = painterResource(produto.thumbnail),
                    contentDescription = produto.nome,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
                if(produto.emPromocao()){
                    Surface(
                        modifier = Modifier
                            .padding(4.dp)
                            .width(80.dp)
                            .align(Alignment.TopEnd),
                        color = Desconto,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "${produto.desconto}% OFF",
                            style = RennerMVPTheme.typography.caption,
                            color = RennerMVPTheme.colors.onSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row{
                Column(Modifier.width(180.dp)) {
                    Text(
                        text = produto.nome,
                        color = RennerMVPTheme.colors.onSurface,
                        style = RennerMVPTheme.typography.overline
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        produto.detalhesPgmto,
                        color = RennerMVPTheme.colors.onSurface,
                        style = RennerMVPTheme.typography.caption
                    )
                }
                Icon(
                    imageVector = Icons.Outlined.Favorite,
                    contentDescription = stringResource(id = R.string.favorite),
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .width(48.dp)
                        .clickable { onFavorite(produto) }
                )
            }
        }
    }
}