package com.jikana.app.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jikana.app.ui.screens.SplashScreen
import com.jikana.app.ui.screens.auth.LoginScreen
import com.jikana.app.ui.screens.auth.RegisterScreen
import com.jikana.app.ui.screens.home.MainHomeScreen
import com.jikana.app.ui.screens.kanji.KanjiScreen
import com.jikana.app.viewmodel.AuthViewModel
import com.jikana.app.viewmodel.KanjiViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val kanjiViewModel: KanjiViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.SPLASH
    ) {
        composable(NavRoutes.SPLASH) {
            SplashScreen(navController = navController)
        }
        composable(NavRoutes.LOGIN) {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable(NavRoutes.REGISTER) {
            RegisterScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable(NavRoutes.HOME) {
            MainHomeScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable(NavRoutes.KANJI) {
            KanjiScreen(
                navController = navController,
                kanjiViewModel = kanjiViewModel
            )
        }
    }
}
