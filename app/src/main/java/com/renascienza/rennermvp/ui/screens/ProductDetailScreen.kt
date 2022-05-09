package com.renascienza.rennermvp.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.icu.text.NumberFormat
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.renascienza.rennermvp.R
import com.renascienza.rennermvp.data.repositorios.MockData
import com.renascienza.rennermvp.model.Produto
import com.renascienza.rennermvp.ui.RennerBottomBar
import com.renascienza.rennermvp.ui.RennerTopBar
import com.renascienza.rennermvp.ui.navigation.MainNavigationConfig
import com.renascienza.rennermvp.ui.theme.RennerMVPTheme

@Composable
fun ProductDetailScreen(produto: Produto) {

    val screenState = rememberScaffoldState()
    val navController = rememberNavController()

    ProductDetailContent(produto)

}

@Composable
private fun ProductDetailContent(produto: Produto) {
    val formatoMoeda = NumberFormat.getCurrencyInstance()
    LazyColumn(
        Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {

        item {
            Image(
                painter = painterResource(produto.photo),
                contentDescription = produto.nome,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(256.dp)
                    .fillMaxWidth()
            )
        }
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                text = produto.marca,
                color = RennerMVPTheme.colors.onBackground,
                style = RennerMVPTheme.typography.h6
            )
        }
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
                text = produto.nome,
                color = RennerMVPTheme.colors.onBackground,
                style = RennerMVPTheme.typography.subtitle1
            )
        }
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
                text = "REF: ${produto.ref}",
                color = RennerMVPTheme.colors.onBackground.copy(alpha = 0.5f),
                style = RennerMVPTheme.typography.overline
            )
        }
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
                text = formatoMoeda.format(produto.valor),
                color = RennerMVPTheme.colors.onBackground,
                style = RennerMVPTheme.typography.body1
            )
        }
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
                text = produto.detalhesPgmto,
                color = RennerMVPTheme.colors.onBackground,
                style = RennerMVPTheme.typography.caption
            )
        }
        if (produto.cores.isNotEmpty()) {
            item {
                ColorList(produto = produto) {
                    Log.d("RENNER_MVP", "Selecinada cor ${it.first}")
                    /* Isso deveria ser propagado para o carrinho de compras,
                    * mas não estamos crianado carrinhos ainda */
                }
            }
        }
        if (produto.tamanhos.isNotEmpty()) {
            item {
                SizeList(produto = produto) {
                    Log.d("RENNER_MVP", "Selecinado tamanho $it")
                    /* Isso deveria ser propagado para o carrinho de compras,
                    * mas não estamos criando carrinhos ainda */
                }
            }
        }
        item{
            Spacer(modifier = Modifier.height(96.dp))
        }
    }
}

@Composable
fun ColorList(produto: Produto, onChange :(Pair<String, Color>) -> Unit) {
    var selectedIndex by remember { mutableStateOf(-1) }
    val listState = rememberLazyListState()
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        var corSelecionada =
            if (selectedIndex > -1) {
                val cores = produto.cores.toList()
                onChange(cores[selectedIndex])
                cores[selectedIndex].first
            } else {
                "Selecione a cor desejada"
            }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = corSelecionada,
            color = RennerMVPTheme.colors.onBackground,
            style = RennerMVPTheme.typography.overline
        )

        LazyRow(state = listState, modifier = Modifier.height(64.dp)) {
            items(produto.cores.keys.size) { index ->
                ColorBox(
                    index,
                    color = produto.cores.entries.toList()[index].toPair(),
                    selected = selectedIndex == index
                ) {
                    selectedIndex = index
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ColorListPreview() {
    val produto = MockData.produtos[4]
    RennerMVPTheme {
        ColorList(produto = produto){

        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SizeListPreview() {
    val produto = MockData.produtos[4]
    RennerMVPTheme {
        SizeList(produto = produto){

        }
    }
}

@Composable
fun ColorBox(
    index: Int,
    color: Pair<String, Color>,
    selected: Boolean,
    oneItemClick: (Int) -> Unit
) {
    if (selected) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .padding(8.dp)
                .background(RennerMVPTheme.colors.background)
                .border(width = 4.dp, color = RennerMVPTheme.colors.onBackground)
                .clickable { oneItemClick(index) }
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
                    .background(color.second)
                    .border(width = 4.dp, color = RennerMVPTheme.colors.background)
                    .clickable { oneItemClick(index) }
            )
        }
    } else {
        Box(
            modifier = Modifier
                .size(48.dp)
                .padding(8.dp)
                .background(color.second)
                .border(width = 4.dp, color = RennerMVPTheme.colors.background)
                .clickable { oneItemClick(index) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ColorBoxPreview() {
    RennerMVPTheme {
        ColorBox(index = 0, color = Pair("Azul", Color.Blue), selected = true, oneItemClick = {})
    }
}

@Composable
fun SizeList(produto: Produto, onChange: (String) -> Unit) {
    var selectedIndex by remember { mutableStateOf(-1) }
    val listState = rememberLazyListState()
    Column(
        Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {

        var tamanhoSelecionado =
            if (selectedIndex > -1) {
                val tamanhos = produto.tamanhos
                onChange(tamanhos[selectedIndex])
                tamanhos[selectedIndex]
            } else {
                "Selecione tamanho"
            }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = "Tamanho: $tamanhoSelecionado",
            color = RennerMVPTheme.colors.onBackground,
            style = RennerMVPTheme.typography.overline
        )

        LazyRow(state = listState, modifier = Modifier.height(64.dp)) {
            items(produto.cores.keys.size) { index ->
                SizeBox(
                    index,
                    tamanho = produto.tamanhos[index],
                    selected = selectedIndex == index
                ) {
                    selectedIndex = index
                }
            }
        }
    }
}

@Composable
fun SizeBox(
    index           :Int,
    tamanho         :String,
    selected        : Boolean,
    oneItemClick    :(Int) -> Unit
) {

    if (selected) {
        Box(
            modifier = Modifier
                .width(128.dp)
                .height(88.dp)
                .padding(start = 8.dp, end = 8.dp)
                .background(RennerMVPTheme.colors.background)
                .border(width = 4.dp, color = RennerMVPTheme.colors.onBackground)
                .clickable { oneItemClick(index) }
        ) {
            Box(
                modifier = Modifier
                    .width(128.dp)
                    .height(88.dp)
                    .padding(start = 8.dp, end = 8.dp)
                    .border(width = 4.dp, color = RennerMVPTheme.colors.background)
                    .clickable { oneItemClick(index) }
            ){
                Text(
                    text = tamanho,
                    style = RennerMVPTheme.typography.button,
                    color = RennerMVPTheme.colors.onBackground
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .width(128.dp)
                .height(88.dp)
                .padding(start = 8.dp, end = 8.dp)
                .border(width = 4.dp, color = RennerMVPTheme.colors.background)
                .clickable { oneItemClick(index) }
        ){
            Text(
                text = tamanho,
                style = RennerMVPTheme.typography.button,
                color = RennerMVPTheme.colors.onBackground
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    RennerMVPTheme {
        ProductDetailScreen(MockData.produtos[0])
    }
}