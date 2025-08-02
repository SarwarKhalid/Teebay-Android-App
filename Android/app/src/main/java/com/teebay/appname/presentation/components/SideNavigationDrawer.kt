package com.teebay.appname.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.teebay.appname.presentation.navigation.navigatePopUpToHome
import com.teebay.appname.presentation.navigation.navigateToAllProducts
import com.teebay.appname.presentation.navigation.navigateToDashboard
import com.teebay.appname.presentation.navigation.navigateToLogin
import kotlinx.coroutines.launch

/**
 * Component for side navigation bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SideNavigationDrawer(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navController: NavController,
    floatingActionButton: @Composable () -> Unit = {},
    title: String = "",
    content: @Composable (PaddingValues) -> Unit
) {
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                NavigationDrawerItem(
                    label = { Text("My Products") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigatePopUpToHome()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text("All Products") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigateToAllProducts()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text("Dashboard") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigateToDashboard()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                HorizontalDivider()

                NavigationDrawerItem(
                    label = { Text("Log Out") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigateToLogin()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(title) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            floatingActionButton = floatingActionButton,
            content = content
        )
    }
}