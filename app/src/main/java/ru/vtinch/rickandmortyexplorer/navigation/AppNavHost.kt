package ru.vtinch.rickandmortyexplorer.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    SharedTransitionLayout {
        NavHost(
            navController = navController, startDestination = SearchScreen
        ) {
            composable<SearchScreen> {
                val screen: SearchScreen = it.toRoute()
                screen.Show(
                    modifier = modifier,
                    navController = navController,
                    shareTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this,
                )
            }
            composable<DetailScreen> {
                val detailScreen: DetailScreen = it.toRoute()
                detailScreen.Show(
                    modifier = modifier,
                    navController = navController,
                    shareTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this,
                )
            }
        }
    }

}


