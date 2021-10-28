package de.felix_schuetz.compose_navigation_test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import de.felix_schuetz.compose_navigation_test.navigation.NavController

data class Screen(
    val route: String,
    val label: String,
    val icon: ImageVector,
)

val navigationBarScreens = listOf(
    Screen(
        route = Route.Gallery,
        label = "Gallery",
        icon = Icons.Rounded.Image,
    ),
    Screen(
        route = Route.Folders,
        label = "Folders",
        icon = Icons.Rounded.Folder,
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    navController: NavController,
    content: @Composable () -> Unit,
) {
    val currentRoute by navController.currentRouteAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            MediumTopAppBar(title = {
                Text("Test")
            })
        },
        bottomBar = {
            NavigationBar {
                for (screen in navigationBarScreens) {
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(route = screen.route) {
                                popUpToRoot()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = null,
                            )
                        },
                        label = {
                            Text(screen.label)
                        },
                    )
                }
            }
        },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            content()
        }
    }
}
