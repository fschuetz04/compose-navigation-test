package de.felix_schuetz.compose_navigation_test.navigation

/**
 * Builds options for a navigation.
 */
class NavOptionsBuilder {
    /**
     * Whether to clear the back stack before navigating.
     */
    private var popUpToRoot = false

    /**
     * Clear the back stack before navigating.
     */
    fun popUpToRoot() {
        popUpToRoot = true
    }


    /**
     * @return Navigation options.
     */
    internal fun build(): NavOptions {
        return NavOptions(popUpToRoot = popUpToRoot)
    }
}
