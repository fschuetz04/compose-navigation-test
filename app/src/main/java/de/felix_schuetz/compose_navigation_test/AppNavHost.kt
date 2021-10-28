package de.felix_schuetz.compose_navigation_test

import androidx.compose.runtime.Composable
import de.felix_schuetz.compose_navigation_test.navigation.NavHost
import de.felix_schuetz.compose_navigation_test.navigation.rememberNavController
import de.felix_schuetz.compose_navigation_test.screens.DetailsScreen
import de.felix_schuetz.compose_navigation_test.screens.FoldersScreen
import de.felix_schuetz.compose_navigation_test.screens.GalleryScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startRoute = Route.Gallery,
    ) {
        // TODO(fschuetz04): provide a way to pass e.g. paddingValues to screen?
        wrap(wrapper = { content ->
            AppScaffold(
                navController = navController,
                content = content,
            )
        }) {
            destination(route = Route.Gallery) {
                GalleryScreen(
                    onNavigateToDetails = {
                        navController.navigate(Route.Details)
                    },
                )
            }

            destination(route = Route.Folders) {
                FoldersScreen()
            }
        }

        destination(route = Route.Details) {
            DetailsScreen(onNavigateUp = {
                navController.popBackStack()
            })
        }
    }
}

object Route {
    const val Gallery = "gallery"
    const val Folders = "folders"
    const val Details = "details"
}
