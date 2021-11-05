package de.felix_schuetz.compose_navigation_test.navigation

import androidx.compose.runtime.Composable

/**
 * Type of the wrapper composable passed to [NavGraphBuilder.wrap].
 */
internal typealias Wrapper = @Composable (content: @Composable () -> Unit) -> Unit

/**
 * Options for a navigation.
 *
 * @param popUpToRoot Whether to clear the back stack before navigating.
 */
internal data class NavOptions(val popUpToRoot: Boolean)

/**
 * Destination in a navigation graph.
 *
 * @param content Composable content to be shown when the destination is visible.
 * @param wrappers Wrapper composables previously added by calling [NavGraphBuilder.wrap].
 */
data class Destination(
    val content: @Composable () -> Unit,
    val wrapper: Wrapper?,
)
