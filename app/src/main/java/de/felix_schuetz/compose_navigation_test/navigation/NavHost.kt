package de.felix_schuetz.compose_navigation_test.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
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
@OptIn(ExperimentalAnimationApi::class)
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
        if (destination.wrapper != null) {
            destination.wrapper.invoke {
                AnimatedContent(targetState = destination.content) { targetContent ->
                    targetContent()
                }
            }
        } else {
            destination.content()
        }
    }
}
