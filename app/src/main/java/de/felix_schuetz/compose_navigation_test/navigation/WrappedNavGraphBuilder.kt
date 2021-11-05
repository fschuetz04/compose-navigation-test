package de.felix_schuetz.compose_navigation_test.navigation

import androidx.compose.runtime.Composable

open class WrappedNavGraphBuilder(private val wrapper: Wrapper?) {
    protected val navGraph = mutableMapOf<String, Destination>()

    fun destination(
        route: String,
        content: @Composable () -> Unit,
    ) {
        navGraph[route] = Destination(
            content = content,
            wrapper = wrapper,
        )
    }

    internal fun build(): Map<String, Destination> {
        return navGraph
    }
}