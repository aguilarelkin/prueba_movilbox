package com.app.movilbox.ui.navigation

sealed class Routes(val route: String) {
    data object MainApp : Routes("main")
    data object InfoApp : Routes("info")
}
