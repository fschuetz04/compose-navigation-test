package de.felix_schuetz.compose_navigation_test.navigation

class NavGraphBuilder : WrappedNavGraphBuilder(wrapper = null) {
    fun wrap(
        wrapper: Wrapper,
        builder: WrappedNavGraphBuilder.() -> Unit,
    ) {
        val wrappedNavGraph = WrappedNavGraphBuilder(wrapper = wrapper)
            .apply(builder)
            .build()
        navGraph.putAll(wrappedNavGraph)
    }
}
