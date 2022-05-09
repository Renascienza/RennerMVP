package com.renascienza.rennermvp.ui.navigation

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.renascienza.rennermvp.R
import com.renascienza.rennermvp.model.Categoria
import com.renascienza.rennermvp.model.Oferta
import com.renascienza.rennermvp.model.Produto
import com.renascienza.rennermvp.ui.screens.*
import com.renascienza.rennermvp.ui.viewmodel.HomeViewModel

sealed class BottomNavigationScreens(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Home : BottomNavigationScreens("route.home", R.string.home, Icons.Outlined.Home)
    object Favorites : BottomNavigationScreens("route.favorites", R.string.favorite, Icons.Outlined.Favorite)
    object About : BottomNavigationScreens("route.about", R.string.about, Icons.Outlined.Info)
}

@Composable
fun MainNavigationConfig(
    navController: NavHostController
) {
    val homeViewModel :HomeViewModel = viewModel(
        factory = HomeViewModel.provideFactory()
    )
    val onClickProduto: (Produto) -> Unit = { produto ->
        navController.navigate("produto/${produto.ref}")
    }
    val onClickOferta: (Oferta) -> Unit = { oferta ->
        navController.navigate("oferta/${oferta.codigo}")
    }
    val onClickCategoria: (Categoria) -> Unit = { categoria ->
        navController.navigate("categoria/${categoria.codigo}")
    }
    val onNewFavorito : (Produto) -> Unit = { produto ->
        homeViewModel.favorito(produto.ref)
    }

    NavHost(navController, startDestination = BottomNavigationScreens.Home.route) {
        composable(BottomNavigationScreens.Home.route) {
            HomeScreen(
                model = homeViewModel,
                onRefresh = {},
                onClickOferta = onClickOferta,
                onClickCategoria = onClickCategoria,
                onClickProduto = onClickProduto
            )
        }
        composable(BottomNavigationScreens.Favorites.route) {
            FavoriteScreen(
                model = homeViewModel,
                onClickProduto = onClickProduto
            )
        }
        composable(BottomNavigationScreens.About.route) {
            AboutScreen()
        }

        composable(
            route = "oferta/{codigoOferta}",
            arguments = listOf(navArgument("codigoOferta"){ type = NavType.StringType })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("codigoOferta")?.let { codigo ->
                val ofertas = homeViewModel.ofertasSnapshot()
                if (ofertas.isNotEmpty()) {
                    CollectionsScreen(
                        model = homeViewModel,
                        oferta = ofertas.first { oferta -> oferta.codigo == codigo },
                        onClickProduto = onClickProduto,
                        onClickFavorito = onNewFavorito
                    )

                }else{
                    Log.e("RENNER_MVP", "Produto não encontrado!!!s")
                }
            }

        }

        composable(
            route = "categoria/{codigo}",
            arguments = listOf(navArgument("codigo"){ type = NavType.StringType })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("codigo")?.let { codigo ->
                val categorias = homeViewModel.categoriasSnapshot()
                if (categorias.isNotEmpty()) {
                    CategoriesScreen(
                        model = homeViewModel,
                        categoria = categorias.first { categoria -> categoria.codigo == codigo },
                        onClickProduto = onClickProduto,
                        onClickFavorito = onNewFavorito
                    )

                }else{
                    Log.e("RENNER_MVP", "Produto não encontrado!!!s")
                }
            }

        }

        composable(
            route = "produto/{refProduto}",
            arguments = listOf(navArgument("refProduto"){ type = NavType.StringType })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("refProduto")?.let { ref ->
                val produtos = homeViewModel.produtosSnapshot()
                if (produtos.isNotEmpty()) {
                    ProductDetailScreen(produto = produtos.first { prod -> prod.ref == ref })
                }else{
                    Log.e("RENNER_MVP", "Produto não encontrado!!!s")
                }
            }
        }
    }
}