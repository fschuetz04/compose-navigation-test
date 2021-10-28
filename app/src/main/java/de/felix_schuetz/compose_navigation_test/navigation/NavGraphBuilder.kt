package de.felix_schuetz.compose_navigation_test.navigation

import androidx.compose.runtime.Composable

/**
 * Builds a navigation graph for [NavHost].
 *
 * @param wrappers Wrapper composables previously added by calling [wrap].
 */
class NavGraphBuilder(private val wrappers: List<Wrapper> = listOf()) {
    /**
     * Navigation graph as a map from routes to destinations.
     */
    private val navGraph = mutableMapOf<String, Destination>()

    /**
     * Add a destination to the navigation graph.
     *
     * @param route Route of the destination.
     * @param content Composable content to be shown when the destination is visible.
     */
    fun destination(
        route: String,
        content: @Composable () -> Unit,
    ) {
        navGraph[route] = Destination(
            content = content,
            wrappers = wrappers,
        )
    }

    /**
     * Wrap all destinations added via the given [builder] in the given [wrapper] composable.
     *
     * @param wrapper Wrapper composable.
     * @param builder Wrapped navigation graph.
     */
    fun wrap(
        wrapper: Wrapper,
        builder: NavGraphBuilder.() -> Unit,
    ) {
        val wrappedNavGraph = NavGraphBuilder(wrappers = wrappers + wrapper)
            .apply(builder)
            .build()
        navGraph.putAll(wrappedNavGraph)
    }

    /**
     * @return Navigation graph as a map from routes to destinations.
     */
    internal fun build(): Map<String, Destination> {
        return navGraph
    }
}
