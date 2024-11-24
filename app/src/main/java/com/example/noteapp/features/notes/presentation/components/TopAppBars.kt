package com.example.noteapp.features.notes.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import com.example.noteapp.features.core.ui.theme.poppinsFontFamily
import com.example.noteapp.features.core.ui.theme.ubuntuFontFamily

@OptIn(ExperimentalMaterial3Api::class)
object TopAppBars {
    // main screen
    lateinit var scrollBehavior: TopAppBarScrollBehavior
    lateinit var onSearchClickListener: () -> Unit

    val mainScreenTopAppBar = @Composable {
        CenterAlignedTopAppBar(
            // app icon
            navigationIcon = {
                IconButton(
                    onClick = {},
                    enabled = false,
                    colors = IconButtonColors(
                        containerColor = Color.Unspecified,
                        contentColor = Color.Unspecified,
                        disabledContentColor = MaterialTheme.colorScheme.onSurface,
                        disabledContainerColor = Color.Unspecified
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.StickyNote2,
                        contentDescription = "Notes App"
                    )
                }
            },
            // app name
            title = {
                Text(
                    text = "Notes",
                    fontFamily = ubuntuFontFamily,
                )
            },
            // search button
            actions = {
                IconButton(
                    onClick = { onSearchClickListener() } // navigating to search screen
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors().copy(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            scrollBehavior = scrollBehavior
        )
    }

    // favourite screen
    lateinit var onClickFavScreen: () -> Unit
    lateinit var favScrollBehavior: TopAppBarScrollBehavior
    val favouriteScreenTopAppBar = @Composable {
        TopAppBar(
            // back arrow
            navigationIcon = {
                IconButton(
                    onClick = onClickFavScreen
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Notes App"
                    )
                }
            },
            title = {
                Text(
                    text = "Favourite",
                    fontFamily = ubuntuFontFamily,
                )
            },
            scrollBehavior = favScrollBehavior
        )
    }

    // info screen
    lateinit var onClickInfoScreen: () -> Unit
    lateinit var infoScrollBehavior: TopAppBarScrollBehavior
    val infoScreenTopAppBar = @Composable {
        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = onClickInfoScreen
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "back"
                    )
                }
            },
            title = {
                Text(
                    text = "About",
                    fontFamily = ubuntuFontFamily
                )
            },
            scrollBehavior = infoScrollBehavior
        )
    }

    // search screen
    lateinit var searchScrollBehavior: TopAppBarScrollBehavior
    lateinit var searchScreenBackClick: () -> Unit
    lateinit var focusRequester: FocusRequester
    lateinit var liveSearch: () -> Unit
    lateinit var doToast: () -> Unit
    lateinit var search: () -> Unit
    lateinit var searchData: MutableState<String>
    lateinit var wantsToSearch: MutableState<Boolean>

    val searchScreenTopAppBar = @Composable {

        var showClearButton by remember {
            mutableFloatStateOf(0f)
        }

        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = searchScreenBackClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "back"
                    )
                }
            },
            title = {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceContainer,
                            RoundedCornerShape(50)
                        )
                        .focusRequester(focusRequester),
                    value = searchData.value,
                    onValueChange = {
                        searchData.value = it
                        showClearButton = 1f
                        if (searchData.value.isNotEmpty()) {
                            liveSearch()
                        }
                        wantsToSearch.value = false
                    },
                    placeholder = {
                        Text("Search")
                    },
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier
                                .alpha(showClearButton),
                            onClick = {
                                searchData.value = ""
                                showClearButton = 0f
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "clear"
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if (searchData.value.isEmpty()) {
                                doToast()
                            } else {
                                search()
                            }
                        }
                    ),
                    singleLine = true,
                    maxLines = 1,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = poppinsFontFamily
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
            },
            scrollBehavior = searchScrollBehavior
        )
    }
    // show screen
    lateinit var titleComposable: @Composable () -> Unit
    lateinit var showScreenToggleButton: @Composable () -> Unit
    lateinit var showScreenBackButton: @Composable () -> Unit
    lateinit var deleteButton: @Composable () -> Unit
    lateinit var editButton: @Composable () -> Unit
    lateinit var showScrollBehavior: TopAppBarScrollBehavior
    val showScreenTopAppBar = @Composable {
        MediumTopAppBar(
            // back button
            navigationIcon = {
                showScreenBackButton()
            },
            // note title
            title = titleComposable,
            actions = {
                // favourites button
                showScreenToggleButton()
                // delete icon
                deleteButton()
                // edit icon
                editButton()
            },
            scrollBehavior = showScrollBehavior
        )
    }

    // add/edit screen
    lateinit var addEditBackClick: () -> Unit
    lateinit var saveButtonClickListener: () -> Unit
    lateinit var noteId: String
    lateinit var addEditToggleButton: @Composable () -> Unit // is initialized in add/edit screen because of the bookmark
    lateinit var addEditScrollBehavior: TopAppBarScrollBehavior
    val addEditScreenTopAppBar = @Composable {
        TopAppBar(
            // app icon
            navigationIcon = {
                IconButton(
                    onClick = addEditBackClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            // app name
            title = {
                Text(
                    text = if (noteId.toInt() > 0) "Edit Note" else "Add Note",
                    fontFamily = ubuntuFontFamily
                )
            },
            actions = {
                // favourites button
                addEditToggleButton()
                // save button
                IconButton(
                    onClick = saveButtonClickListener
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save Note"
                    )
                }
            },
            scrollBehavior = addEditScrollBehavior
        )
    }

}