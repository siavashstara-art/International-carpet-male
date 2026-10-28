package ir.farshbazaar.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ir.farshbazaar.FarshApp
import ir.farshbazaar.feature.about.AboutScreen
import ir.farshbazaar.feature.education.EducationScreen
import ir.farshbazaar.feature.home.HomeScreen
import ir.farshbazaar.feature.inspection.InspectionRequestScreen
import ir.farshbazaar.feature.principles.PrinciplesScreen
import ir.farshbazaar.feature.profile.ProfileScreen
import ir.farshbazaar.feature.rugdetail.RugDetailScreen
import ir.farshbazaar.feature.search.SearchScreen
import ir.farshbazaar.feature.seller.SellerScreen
import ir.farshbazaar.feature.settings.SettingsScreen
import ir.farshbazaar.feature.splash.SplashScreen

object Routes {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val SEARCH = "search"
    const val PRINCIPLES = "principles"
    const val EDUCATION = "education"
    const val ABOUT = "about"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"
    const val KNOT_GUIDE = "rug-knot-guide"
    const val RUG_DETAIL = "rug/{rugId}"
    const val SELLER = "seller/{sellerId}"
    const val INSPECTION = "inspection/{rugId}"

    fun rugDetail(id: String) = "rug/$id"
    fun seller(id: String) = "seller/$id"
    fun inspection(id: String) = "inspection/$id"
}

@Composable
fun AppNavGraph(app: FarshApp) {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            SplashScreen(onDone = {
                nav.navigate(Routes.HOME) { popUpTo(Routes.SPLASH) { inclusive = true } }
            })
        }

        composable(Routes.HOME) {
            HomeScreen(
                app = app,
                onRugClick = { nav.navigate(Routes.rugDetail(it)) },
                onSearch = { nav.navigate(Routes.SEARCH) },
                onPrinciples = { nav.navigate(Routes.PRINCIPLES) },
                onEducation = { nav.navigate(Routes.EDUCATION) },
                onAbout = { nav.navigate(Routes.ABOUT) },
                onProfile = { nav.navigate(Routes.PROFILE) },
                onKnotGuide = { nav.navigate(Routes.KNOT_GUIDE) }
            )
        }

        composable(
            Routes.RUG_DETAIL,
            arguments = listOf(navArgument("rugId") { type = NavType.StringType })
        ) { back ->
            val id = back.arguments?.getString("rugId") ?: return@composable
            RugDetailScreen(
                app = app, rugId = id,
                onBack = { nav.popBackStack() },
                onSellerClick = { nav.navigate(Routes.seller(it)) },
                onInspection = { nav.navigate(Routes.inspection(id)) },
                onPrinciples = { nav.navigate(Routes.PRINCIPLES) }
            )
        }

        composable(
            Routes.SELLER,
            arguments = listOf(navArgument("sellerId") { type = NavType.StringType })
        ) { back ->
            val id = back.arguments?.getString("sellerId") ?: return@composable
            SellerScreen(
                app = app, sellerId = id,
                onBack = { nav.popBackStack() },
                onRugClick = { nav.navigate(Routes.rugDetail(it)) }
            )
        }

        composable(Routes.SEARCH) {
            SearchScreen(
                app = app,
                onBack = { nav.popBackStack() },
                onRugClick = { nav.navigate(Routes.rugDetail(it)) }
            )
        }

        composable(Routes.PRINCIPLES) { PrinciplesScreen(onBack = { nav.popBackStack() }) }

        composable(Routes.EDUCATION) {
            EducationScreen(app = app, onBack = { nav.popBackStack() })
        }

        composable(Routes.ABOUT) { AboutScreen(onBack = { nav.popBackStack() }) }

        composable(Routes.PROFILE) {
            ProfileScreen(
                app = app,
                onBack = { nav.popBackStack() },
                onSettings = { nav.navigate(Routes.SETTINGS) }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBack = { nav.popBackStack() },
                onAbout = { nav.navigate(Routes.ABOUT) },
                onPrinciples = { nav.navigate(Routes.PRINCIPLES) },
                onEducation = { nav.navigate(Routes.EDUCATION) },
                onKnotGuide = { nav.navigate(Routes.KNOT_GUIDE) }
            )
        }

        composable(Routes.KNOT_GUIDE) {
            ir.farshbazaar.feature.knotguide.RugKnotGuideScreen(onBack = { nav.popBackStack() })
        }

        composable(
            Routes.INSPECTION,
            arguments = listOf(navArgument("rugId") { type = NavType.StringType })
        ) { back ->
            val id = back.arguments?.getString("rugId") ?: return@composable
            InspectionRequestScreen(app = app, onBack = { nav.popBackStack() }, rugId = id)
        }
    }
}
