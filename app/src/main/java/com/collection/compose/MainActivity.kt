package com.collection.compose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.collection.compose.collection_ui.custom_collapse_toolbar.CustomCollapsibleToolbarWithOverlap
import com.collection.compose.collection_ui.custom_scroll_toolbar.ExampleFlexibleTopBar
import com.collection.compose.collection_ui.lazy_column_like_grid.LazyColumnLikeGrid
import com.collection.compose.ui.theme.CollectionComposeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CollectionComposeTheme {
                val windowSizeClass = calculateWindowSizeClass(activity = this)
                println("windowSizeClass width ${windowSizeClass.widthSizeClass}")

                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
//                    CustomCollapsibleToolbarWithOverlap()
                    LazyColumnLikeGrid()

                }
            }
        }
    }
}