package com.collection.compose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.collection.compose.animated_placeholder.ExampleAnimatedPlaceholder
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

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    CustomCollapsibleToolbarWithOverlap()
//                    ExampleDraggableRatingBar()
//                    ExamplePulseEffect()
//                    ExampleAnimatedPlaceholder()
                    ExampleAnimatedPlaceholder()
//                    ExampleFlexibleTopBar(innerPadding)
//                    val items = List(10) { "Item $it" }
//                    CarouselWithScalingEffect(items = items)
                }
            }
        }
    }
}