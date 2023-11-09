package com.news.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.news.app.view.DetailPage
import com.news.app.view.ListingPage

@Composable
fun CreateNavigationGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screens.LIST){
        composable(Screens.LIST){
            ListingPage(navController)
        }
        composable(Screens.DETAIL){
            DetailPage(navController)
        }
    }
}