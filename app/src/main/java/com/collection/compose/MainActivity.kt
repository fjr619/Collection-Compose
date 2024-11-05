package com.collection.compose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.collection.compose.draggable_rating_bar.ExampleDraggableRatingBar
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
                    ExampleDraggableRatingBar()
//                    ExampleFlexibleTopBar(innerPadding)
//                    val items = List(10) { "Item $it" }
//                    CarouselWithScalingEffect(items = items)
                }
            }
        }
    }
}