package com.collection.compose.animated_placeholder

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun AnimatedPlaceholder(
    hints: List<String>,
    delayMillis: Long = 2000,
    slideInMillis: Int = 1500,
    slideOutMillis: Int = 1000,
    fontColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    fontSize: TextUnit = 14.sp
) {
    var currentPlaceholderIndex by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(delayMillis) // Delay before switching to the next placeholder
            currentPlaceholderIndex = (currentPlaceholderIndex + 1) % hints.size
        }
    }

    val currentPlaceholder = hints[currentPlaceholderIndex]

    AnimatedContent(
        targetState = currentPlaceholder,
        transitionSpec = {
            slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(
                    durationMillis = slideInMillis
                )
            ) + fadeIn() togetherWith slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(
                    durationMillis = slideOutMillis,
                )
            )
        },
        label = "AnimatedContentPlaceholder"
    ) { str ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = str, style = TextStyle(
                    fontSize = fontSize,
                    color = fontColor
                )
            )
        }
    }
}

