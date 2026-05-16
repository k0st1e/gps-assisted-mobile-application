package dev.kostie.bdora.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val label : String,
    val icon : ImageVector
)

// Page we get after we successfully log in into the application.
@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               username: String) {

    val navigationItemList = listOf(
        NavigationItem("Home", Icons.Default.Home),
        NavigationItem("Routes", Icons.Default.Place),
        NavigationItem("Explore", Icons.Default.Search),
        NavigationItem("Trophies", Icons.Default.Star),
    )

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(

        bottomBar = {

            NavigationBar {
                navigationItemList.forEachIndexed { index, navigationItem ->

                    NavigationBarItem(
                        selected = index == selectedIndex,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(imageVector = navigationItem.icon, contentDescription = navigationItem.label)
                        },
                        label = {
                            Text(text = navigationItem.label)
                        }
                    )
                }
            }
        }
    ) {
        ContentScreen(modifier = modifier.padding(it), selectedIndex, username)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int, username: String) {

    when (selectedIndex) {
        0 -> MapScreen(modifier, username = username)
        1 -> RoutesPage(modifier)
        2 -> ExplorePage(modifier)
        3 -> TrophiesPage(modifier)
    }
}