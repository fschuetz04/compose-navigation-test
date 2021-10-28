package de.felix_schuetz.compose_navigation_test.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

/**
 * Always display the currently visible destination of [navController] in the navigation graph
 * produced by [builder]. Initially, [startRoute] is shown.
 *
 * @param navController Nav controller to be used.
 * @param startRoute Route of the destination to be shown initially.
 * @param builder Navigation graph.
 */
@Composable
fun NavHost(
    navController: NavController,
    startRoute: String,
    builder: NavGraphBuilder.() -> Unit
) {
    LaunchedEffect(key1 = startRoute) {
        navController.setStartRoute(startRoute)
    }

    val navGraph = remember(builder) {
        NavGraphBuilder()
            .apply(builder)
            .build()
    }

    val route by navController.currentRouteAsState()

    val destination = navGraph[route]
    if (destination != null) {
        if (destination.wrappers.isEmpty()) {
            destination.content()
        } else {
            val wrapper = destination.wrappers.reduce { acc, wrapper ->
                { content ->
                    acc {
                        wrapper(content)
                    }
                }
            }

            wrapper(destination.content)
        }
    }
}
