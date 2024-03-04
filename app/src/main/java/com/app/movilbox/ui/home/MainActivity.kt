package com.app.movilbox.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.movilbox.ui.info.Info
import com.app.movilbox.ui.info.InfoViewModel
import com.app.movilbox.ui.navigation.Routes
import com.app.movilbox.ui.product.Product
import com.app.movilbox.ui.product.ProductViewModel
import com.app.movilbox.ui.theme.MovilboxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovilboxTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation()
                }
            }
        }
    }

    @Composable
    fun MainNavigation() {
        val navController = rememberNavController()
        NavigationHost(navController = navController)
    }

    @Composable
    fun NavigationHost(navController: NavHostController) {
        NavHost(navController = navController, startDestination = Routes.MainApp.route) {
            composable(Routes.MainApp.route) {
                val productViewModel = hiltViewModel<ProductViewModel>()
                Product(navController, productViewModel)
            }
            composable(Routes.InfoApp.route + "/{id}") {
                val infoViewModel = hiltViewModel<InfoViewModel>()
                Info(navController, infoViewModel, it.arguments?.getString("id"))
            }
        }

    }
}