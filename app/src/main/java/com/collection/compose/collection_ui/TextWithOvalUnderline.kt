package com.collection.compose.collection_ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.collection.compose.collection_ui.util.DefaultPreviewPhone
import com.collection.compose.collection_ui.util.PreviewPhone

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TextWithOvalUnderline() {
    val arcColor = Color(0xFFFF5722) // Orange color
    val surfaceColor = MaterialTheme.colorScheme.surface

    FlowRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Life is short and the world is ",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
        )
        Column(
            Modifier.width(IntrinsicSize.Min)
        ) {
            Text(
                "wide",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = arcColor
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(intrinsicSize = IntrinsicSize.Max)
            ) {
                // First canvas to draw the orange arc
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                ) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height

                    drawArc(
                        color = arcColor,
                        startAngle = 180f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = Offset(0f, 0f),
                        size = Size(canvasWidth, canvasHeight * 2f)
                    )
                }

                // Second canvas to draw the white overlay
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height

                    // Draw another white arc to overlay the orange arc
                    drawArc(
                        color = surfaceColor,
                        startAngle = 180f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = Offset(0f, 0f),
                        size = Size(canvasWidth, canvasHeight * 2f)
                    )
                }
            }
        }
    }
}

@PreviewPhone
@Composable
fun TextWithOvalUnderlinePreview() {
    DefaultPreviewPhone(
        modifier = Modifier.safeContentPadding()
    ) {
        TextWithOvalUnderline()
    }

}