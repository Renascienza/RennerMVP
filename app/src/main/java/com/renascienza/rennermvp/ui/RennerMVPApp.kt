package com.renascienza.rennermvp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.renascienza.rennermvp.R
import com.renascienza.rennermvp.ui.navigation.BottomNavigationScreens
import com.renascienza.rennermvp.ui.navigation.MainNavigationConfig
import com.renascienza.rennermvp.ui.theme.RennerMVPTheme

val bottomNavItems = listOf(
    BottomNavigationScreens.Home,
    BottomNavigationScreens.Favorites,
    BottomNavigationScreens.About
)

@Composable
fun RennerMVPApp(){

    val screenState = rememberScaffoldState()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = screenState,
        topBar = {
            RennerTopBar(
                titulo      = "Lojas Renner",
                onSearch    = {}
            )
        },
        bottomBar = {
            RennerBottomBar(navController = navController)
        },
        modifier = Modifier.fillMaxSize()
    ){
        MainNavigationConfig(navController = navController)
    }

}

@Composable
fun RennerTopBar(
    titulo      :String,
    onSearch    :() -> Unit
){
    TopAppBar(
        elevation = 8.dp,
        title = {
            Icon(
                painter = painterResource(R.drawable.ic_logomarca),
                contentDescription = titulo,
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .fillMaxSize(0.7f)
                    .padding(bottom = 4.dp, top = 10.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick = onSearch) {
                Icon(
                    tint = MaterialTheme.colors.onSurface,
                    imageVector = Icons.Outlined.Search,
                    contentDescription = stringResource(id = R.string.search)
                )
            }
        },

        actions = {
            IconButton(onClick = onSearch) {
                Icon(
                    tint = MaterialTheme.colors.onSurface,
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = stringResource(id = R.string.cart)
                )
            }
        }
    )
}

@Composable
fun RennerBottomBar(
    navController: NavHostController
){
    BottomNavigation(
        modifier = Modifier.padding(top = 16.dp),
        elevation = 8.dp
    ){
        val currentRoute = currentDestination(navController)
        bottomNavItems.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        tint = menuItemColor(currentRoute, screen),
                        imageVector = screen.icon,
                        contentDescription = stringResource(id = R.string.home)
                    )
                },
                label = {
                    Text(
                        color = menuItemColor(currentRoute, screen),
                        text = stringResource(id = screen.resourceId)
                    )
                },
                selected = selected(currentRoute, screen),
                alwaysShowLabel = true,
                onClick = {
                    if (currentRoute?.route != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }

    }
}

@Composable
private fun menuItemColor(
    currentRoute: NavDestination?,
    screen: BottomNavigationScreens
) = if (selected(currentRoute, screen)) MaterialTheme.colors.onSurface
    else MaterialTheme.colors.onSurface.copy(alpha = 0.5f)

@Composable
private fun selected(
    currentRoute: NavDestination?,
    screen: BottomNavigationScreens
) = currentRoute?.hierarchy?.any { it.route == screen.route } == true

@Composable
private fun currentDestination(navController: NavHostController): NavDestination? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination
}

@Preview(showBackground = true)
@Composable
fun RennerTopBarPreview(){
    RennerMVPTheme {
        RennerTopBar(
            titulo      = "Lojas Renner",
            onSearch    = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RennerBottomBarPreview(){
    RennerMVPTheme {
        val navController = rememberNavController()
        RennerBottomBar (navController)
    }
}

@Preview(showBackground = true)
@Composable
fun RennerMVPAppPreview(){
    RennerMVPTheme {
        RennerMVPApp()
    }
}