package com.example.registroestudiantes

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    object Formulario : Screen("formulario")
    object Detalle : Screen("detalle/{matricula}/{nombre}/{carrera}/{turno}/{activo}") {
        fun crearRuta(
            matricula: String,
            nombre: String,
            carrera: String,
            turno: String,
            activo: Boolean
        ): String {
            val encodedNombre = Uri.encode(nombre)
            val encodedCarrera = Uri.encode(carrera)
            return "detalle/$matricula/$encodedNombre/$encodedCarrera/$turno/$activo"
        }
    }
}

@Composable
fun AppNavigation(prefsManager: PreferencesManager) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Formulario.route
    ) {
        composable(Screen.Formulario.route) {
            FormularioScreen(
                prefsManager = prefsManager,
                onRegistroExitoso = { matricula, nombre, carrera, turno, activo ->
                    navController.navigate(
                        Screen.Detalle.crearRuta(matricula, nombre, carrera, turno, activo)
                    )
                }
            )
        }

        composable(
            route = Screen.Detalle.route,
            arguments = listOf(
                navArgument("matricula") { type = NavType.StringType },
                navArgument("nombre") { type = NavType.StringType },
                navArgument("carrera") { type = NavType.StringType },
                navArgument("turno") { type = NavType.StringType },
                navArgument("activo") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val matricula = backStackEntry.arguments?.getString("matricula").orEmpty()
            val nombre = Uri.decode(backStackEntry.arguments?.getString("nombre").orEmpty())
            val carrera = Uri.decode(backStackEntry.arguments?.getString("carrera").orEmpty())
            val turno = backStackEntry.arguments?.getString("turno").orEmpty()
            val activo = backStackEntry.arguments?.getBoolean("activo") ?: true

            DetalleScreen(
                matricula = matricula,
                nombre = nombre,
                carrera = carrera,
                turno = turno,
                activo = activo,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}