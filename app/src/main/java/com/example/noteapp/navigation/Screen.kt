package com.example.noteapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material.icons.automirrored.outlined.StickyNote2
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    data object NoteScreen: Screen("note_screen")
    data object FavouriteScreen: Screen("favourite_screen")
    data object AddEditNoteScreen: Screen("add_edit_note_screen")
    data object ShowNoteScreen: Screen("show_note_screen")
    data object SearchScreen: Screen("search_screen")
    data object InfoScreen: Screen("info_screen")
}

sealed class Tab(
    val route: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val label: String
) {
    data object Notes: Tab(
        route = "notes_tab",
        icon = NavIcons.noteIconOutlined,
        selectedIcon = NavIcons.noteIconFilled,
        label = "Notes"
    )
    data object Favourites: Tab(
        route = "favourites_tab",
        icon = NavIcons.favIconOutlined,
        selectedIcon = NavIcons.favIconFilled,
        label = "Favourites"
    )
    data object About: Tab(
        route = "About_tab",
        icon = NavIcons.infoIconOutlined,
        selectedIcon = NavIcons.infoIconFilled,
        label = "About"
    )
}

private object NavIcons {
    val noteIconOutlined = Icons.AutoMirrored.Outlined.StickyNote2
    val noteIconFilled = Icons.AutoMirrored.Filled.StickyNote2

    val favIconOutlined = Icons.Outlined.Bookmarks
    val favIconFilled = Icons.Filled.Bookmarks

    val infoIconOutlined = Icons.Outlined.Info
    val infoIconFilled = Icons.Filled.Info
}

val navList = listOf(
    Tab.Notes,
    Tab.Favourites,
    Tab.About
)