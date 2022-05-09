package com.renascienza.rennermvp.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.renascienza.rennermvp.R
import com.renascienza.rennermvp.data.repositorios.MockData
import com.renascienza.rennermvp.model.Categoria
import com.renascienza.rennermvp.model.Oferta
import com.renascienza.rennermvp.model.Produto
import com.renascienza.rennermvp.ui.theme.Desconto
import com.renascienza.rennermvp.ui.theme.RennerMVPTheme
import com.renascienza.rennermvp.ui.viewmodel.HomeUiState
import com.renascienza.rennermvp.ui.viewmodel.HomeViewModel
import kotlin.math.ceil

/**
 * Tela principal da Home.
 * Contém cartazes de vitrine, um menu de categorias
 * e um staggered grid (grid no estilo Pinterest)
 * com a listagem de produtos
 */
@Composable
fun HomeScreen(
    model               :HomeViewModel,
    onRefresh           :() -> Unit,
    onClickOferta       :(Oferta) -> Unit,
    onClickCategoria    :(Categoria) -> Unit,
    onClickProduto      :(Produto) -> Unit
){

    val onFavorito : (Produto) -> Unit = { produto ->
        model.favorito(produto.ref)
    }

    val uiState = model.uiState.collectAsState().value

    HomeScreenContent(
        uiState,
        onClickOferta,
        onClickCategoria,
        onRefresh,
        onClickProduto,
        onFavorito
    )

}

/**
 * Conteúdo da tela de Home que delega ao chamador
 * qualquer lógica de estado
 */
@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onClickOferta: (Oferta) -> Unit,
    onClickCategoria: (Categoria) -> Unit,
    onRefresh: () -> Unit,
    onClickProduto: (Produto) -> Unit,
    onFavorito: (Produto) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Vitrine(uiState, onClickOferta)
        }
        item {
            Spacer(Modifier.height(8.dp))
        }
        item {
            Categorias(uiState, onClickCategoria)
        }
        item {
            Spacer(Modifier.height(8.dp))
        }
        item {
            GridDeProdutos(uiState, onRefresh, onClickProduto, onFavorito)
        }
        item{
            Spacer(modifier = Modifier.height(96.dp))
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, name = "Home Screen Nexus 6", device = Devices.NEXUS_6)
@Preview(showBackground = true, name = "Home Screen Nexus 6P", device = Devices.NEXUS_6P)
@Preview(showBackground = true, name = "Home Screen Nexus 7", device = Devices.NEXUS_7)
@Preview(showBackground = true, name = "Home Screen Nexus C", device = Devices.PIXEL_C)
@Composable
fun HomeScreenContentPreview(){
    val uiState :HomeUiState = HomeUiState.HasData(
        produtos = MockData.produtos,
        categorias = MockData.categorias,
        ofertas = MockData.ofertas,
        favoritos = mutableSetOf(),
        isLoading = false,
        searchInput = ""
    )
    RennerMVPTheme {
        HomeScreenContent(
            uiState = uiState,
            onClickOferta = {},
            onClickCategoria = {} ,
            onRefresh = { /*TODO*/ },
            onClickProduto = {},
            onFavorito = {}
        )
    }
}

/**
 * Placeholder de loading enquanto a tela não carrega o conteúdo
 * (o HomeUIState está no estado "loading")
 */
@Composable
fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(loading),
            onRefresh = onRefresh,
            content = content,
        )
    }
}

/**
 * Um simples componente de espera
 */
@Composable
fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

/**
 * Um carrossel horizontal mostrando os cartazes de vitrine (ofertas)
 */
@Composable
fun Vitrine(
    uiState         :HomeUiState,
    onClick         :(Oferta) -> Unit
    ) {

    if(uiState is HomeUiState.HasData){
        LazyRow{
            uiState.ofertas.forEach { oferta ->
                item{
                    OfertaUI(oferta){
                        onClick(oferta)
                    }
                }
                item{
                    Spacer(Modifier.width(8.dp))
                }
            }
        }
    }
}

/**
 * O grid principal de produtos
 */
@Composable
fun GridDeProdutos(uiState: HomeUiState, onRefresh: () -> Unit = {}, onClickProduto: (Produto) -> Unit, onFavorito :(Produto) -> Unit) {
    LoadingContent(
        empty = when (uiState) {
            is HomeUiState.HasData -> false
            is HomeUiState.NoData -> uiState.isLoading
        },
        emptyContent = { FullScreenLoading() },
        loading = uiState.isLoading,
        onRefresh = onRefresh,
        content = {
            when (uiState) {
                is HomeUiState.HasData -> {
                    StaggeredVerticalGrid(
                        maxColumnWidth = 220.dp,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        uiState.produtos.forEach { produto ->
                            ProdutoUI(uiState, produto, onClickProduto, onFavorito)
                        }
                    }
                }
                is HomeUiState.NoData -> {
                    TextButton(
                        onClick = onRefresh,
                        Modifier.fillMaxSize()
                    ) {
                        Text(
                            stringResource(id = R.string.tap_load),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    )
}

/**
 * Vertical grid no estilo Pinterest.
 * Agnóstico em relação aos dados que exibe.
 * qualquer lista de elementos em @content será
 * exibida como um grid
 */
@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    maxColumnWidth: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth = constraints.maxWidth / columns
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(columns) { 0 } // track each column's height
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val colY = IntArray(columns) { 0 }
            placeables.forEach { placeable ->
                val column = shortestColumn(colY)
                placeable.place(
                    x = columnWidth * column,
                    y = colY[column]
                )
                colY[column] += placeable.height
            }
        }
    }
}

/**
 * Função utilitária para StaggeredVerticalGrid,
 * que calcula a altura da coluna mais curta
 */
private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}

/**
 * Carrossel horizontal no qual as categorias são exibidas como
 * círculos
 */
@Composable
fun Categorias(uiState: HomeUiState, onClick :(Categoria) -> Unit) {
    if(uiState is HomeUiState.HasData){
        Box(Modifier.fillMaxWidth()) {
            LazyRow(modifier = Modifier.padding(8.dp).align(Alignment.Center)){
                uiState.categorias.forEach { categoria ->
                    item{
                        CategoriaUI(categoria){
                            onClick(categoria)
                        }
                    }
                    item{
                        Spacer(Modifier.width(16.dp))
                    }
                }
            }
        }
    }
}

/**
 * Layout de cada produto.
 * Contém foto, nome, descrição curta e um botão para favoritar
 */
@Composable
fun ProdutoUI(uiState :HomeUiState.HasData, produto: Produto, onClickProduto: (Produto) -> Unit, onFavorite :(Produto) -> Unit) {
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
                    imageVector = favoriteIcon(isFavorite = isFavorite(uiState, produto)),
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

/**
 * Botão de favorito que se mostra cheio ou vazado conforme o estado.
 */
@Composable
fun favoriteIcon(isFavorite :Boolean) :ImageVector{
    return if(isFavorite) {
        Icons.Outlined.Favorite
    } else {
        Icons.Filled.FavoriteBorder
    }
}

/**
 * Checa se um produto está favoritado
 */
private fun isFavorite(uiState: HomeUiState.HasData, produto: Produto) :Boolean{
    return uiState.favoritos.contains(produto.ref)
}

@Preview(showBackground = true)
@Composable
fun ProdutoUIPreview(){
    val preco = 449.90.toBigDecimal()
    val categoria = Categoria("PF", "Perfumaria", R.drawable.categoria_beleza)
    val produto = Produto(
        "PFI001",
        "PERFUME LANCÔME LA VIE EST BELLE FEMININO EAU DE PARFUM 50ML",
        "Lancome",
        preco,
        "10x de ${preco.div(10.toBigDecimal()).toPlainString()} sem juros no cartão Renner",
        emptyMap(),
        listOf("50ml"),
        listOf(categoria),
        true,
        0.0,
        R.drawable.lancome,
        R.drawable.lancome
    )
    val uiState = HomeUiState.HasData(
        produtos = listOf(produto),
        categorias = listOf(categoria),
        ofertas = emptyList(),
        favoritos = setOf(),
        isLoading = false,
        searchInput = ""
    )
    RennerMVPTheme {
        ProdutoUI(
            produto = produto,
            uiState = uiState,
            onClickProduto = {

            },
            onFavorite = {

            }
        )
    }
}

/**
 * Layout de um cartaz de vitrine (oferta)
 */
@Composable
fun OfertaUI(oferta: Oferta, onOfertaClick :() -> Unit) {

    val res = LocalContext.current.resources
    /*Melhor performance seria rememberAsSaveable com um custom saver, mas para
    um demo é suficiente */
    val palette = remember {
        Palette.from(BitmapFactory.decodeResource(res, oferta.photo)).generate()
    }
    val back = palette.getLightMutedColor(0xFFFFFF)
    val titleText = palette.lightMutedSwatch?.titleTextColor ?: 0x000000
    val subTitleText = palette.lightMutedSwatch?.bodyTextColor ?: 0x000000
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .width(ofertaWidth()),
        color = Color(back),
        elevation = RennerMVPTheme.elevations.card,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.fillMaxSize()){
            Image(
                painter = painterResource(oferta.photo),
                contentDescription = oferta.nome,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Text(modifier = Modifier.padding(8.dp), text = oferta.nome, style = RennerMVPTheme.typography.h6, color = Color(titleText))
            Text(modifier = Modifier.padding(8.dp), text = oferta.descricao, style = RennerMVPTheme.typography.caption, color = Color(subTitleText))
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { onOfertaClick() }
            ){
                Text("Comprar", color = RennerMVPTheme.colors.secondary)
            }
        }
    }
}

/**
 * Ajustar a largura de um item da vitrine de forma a deixar
 * sempre o último elemento pela metade, não importa o tamanho
 * ou densidade da tela.
 *
 * Esse método não ajuda no caso de telas menores que a largura de
 * um elemento, mas atualmente não existem devices mais estreitos que 200dp
 */
@Composable
private fun ofertaWidth() :Dp{
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val mod = screenWidth % (200 + 8)
    return if( mod > 0 ){
       Dp(250.0f)
    }else{
        Dp(200.0f)
    }
}

@Preview(showBackground = false)
@Composable
fun OfertaUIPreview(){

    val oferta = Oferta(
        codigo ="mai001",
        nome = "Blusas 50% OFF",
        descricao = "para ela estrear um look novo",
        produtos = emptyList(),
        R.drawable.oferta_blusas
    )

    RennerMVPTheme{
        OfertaUI(oferta = oferta) {

        }
    }
}

/**
 * Layout de uma categoria.
 * Contém foto com clipping circular e um nome
 */
@Composable
fun CategoriaUI(categoria: Categoria, onClick: () -> Unit) {
    Column {
        Image(
            painter = painterResource(categoria.photo),
            contentDescription = categoria.nome,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(48.dp)
                .clip(CircleShape)
                .clickable {
                    onClick()
                }
                .align(Alignment.CenterHorizontally)
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = categoria.nome,
            textAlign = TextAlign.Center,
            style = RennerMVPTheme.typography.caption,
            color = RennerMVPTheme.colors.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriaUIPreview(){
    val categoria = Categoria(
        codigo = "cat:masc",
        "Masculino",
        R.drawable.categoria_masc
    )
    RennerMVPTheme {
        CategoriaUI(categoria = categoria) {

        }
    }
}
