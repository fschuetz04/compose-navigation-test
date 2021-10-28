package de.felix_schuetz.compose_navigation_test.navigation

import android.os.Bundle
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.core.os.bundleOf

/**
 * Controls navigation between destinations defined in a [NavHost].
 */
class NavController {
    /**
     * Back stack of routes.
     */
    private val backStack = ArrayDeque<String>()

    /**
     * Route of the currently visible destination.
     */
    private val currentRoute get() = backStack.lastOrNull()

    /**
     * Listeners to be notified when a route change occurs.
     */
    private val routeChangeListeners = mutableSetOf<() -> Unit>()

    /**
     * Start route used when the back stack is empty.
     */
    private var startRoute: String? = null

    /**
     * Navigate to a destination.
     *
     * @param route Route of the destination.
     * @param builder Additional options.
     */
    fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit = {}) {
        val options = NavOptionsBuilder()
            .apply(builder)
            .build()

        if (options.popUpToRoot) {
            backStack.clear()
        }

        backStack.addLast(route)
        onRouteChanged()
    }

    /**
     * Pop the current destination from the back stack and go to the previous destination. If there
     * is no more than one destination on the back stack, nothing is done.
     *
     * @return Whether there was more than one destination on the back stack.
     */
    fun popBackStack(): Boolean {
        if (backStack.size <= 1) {
            return false
        }

        backStack.removeLast()
        onRouteChanged()
        return true
    }

    /**
     * @return Current route as state.
     */
    @Composable
    fun currentRouteAsState(): State<String?> {
        val state = remember { mutableStateOf(currentRoute) }
        val callback = {
            state.value = currentRoute
        }

        DisposableEffect(this) {
            routeChangeListeners.add(callback)

            onDispose {
                routeChangeListeners.remove(callback)
            }
        }

        return state
    }

    /**
     * Set the route of the initial destination. If the back stack is empty, the initial destination
     * is added to the it. If the initial destination is different from the previous initial
     * destination, the back stack is reset to only contain the initial destination.
     *
     * @param route Route of the initial destination.
     */
    internal fun setStartRoute(route: String) {
        if (route != startRoute) {
            startRoute = route
            navigate(route = route) {
                popUpToRoot()
            }
        }
    }

    /**
     * @return Internal state.
     */
    internal fun saveState(): Bundle {
        return bundleOf(
            BackStackKey to ArrayList(backStack),
            StartRouteKey to startRoute,
        )
    }

    /**
     * Restore the internal state.
     *
     * @param bundle State to be restored, obtained from [saveState].
     */
    internal fun restoreState(bundle: Bundle) {
        val bundledBackStack = bundle.getStringArrayList(BackStackKey)

        backStack.clear()
        backStack.addAll(bundledBackStack!!)

        startRoute = bundle.getString(StartRouteKey)

        onRouteChanged()
    }

    /**
     * Notify all route change listeners.
     */
    private fun onRouteChanged() {
        for (listener in routeChangeListeners) {
            listener()
        }
    }

    companion object {
        private const val BackStackKey = "backStack"
        private const val StartRouteKey = "startRoute"
    }
}

/**
 * @return New [NavController].
 */
@Composable
fun rememberNavController(): NavController {
    return rememberSaveable(saver = navControllerSaver()) {
        NavController()
    }
}

/**
 * @return [Saver] for [NavController].
 */
private fun navControllerSaver(): Saver<NavController, Bundle> {
    return Saver(
        save = {
            it.saveState()
        },
        restore = {
            NavController().apply {
                restoreState(it)
            }
        },
    )
}
