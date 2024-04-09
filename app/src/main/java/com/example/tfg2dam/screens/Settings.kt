package com.example.tfg2dam.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.tfg2dam.footernavtab.FooterNavTab
import com.example.tfg2dam.footernavtab.Property1
import com.example.tfg2dam.header.Header

@Composable
fun Settings(navController: NavController) {
    Box(Modifier.fillMaxSize()) {
        Header(
            modifier = Modifier.align(Alignment.TopCenter),
            onUserIconClicked = {},
            onSettingsIconClicked = {}
        )

        FooterNavTab(
            modifier = Modifier.align(Alignment.BottomCenter),
            property1 = Property1.HomeClicked,
            onListButtonClicked = { navController.navigate("MyList") },
            onDiscoverButtonClicked = { navController.navigate("Discover") },
        )

    }
}