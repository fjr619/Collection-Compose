package com.collection.compose.collection_ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.collection.compose.collection_ui.util.DefaultPreviewPhone
import com.collection.compose.collection_ui.util.PreviewPhone
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularKcalIndicator(
    modifier: Modifier = Modifier,
    progress: Float, // from 0.0 to 1.0 to indicate the percentage
    maxCalories: Int,
    currentCalories: Int,
    size: Dp = 150.dp,
    ringThickness: Dp = 20.dp,
    progressColor: Color = Color(0xFFFF4A43),
    backgroundColor: Color = Color.LightGray,
    secondaryMarksColor: Color = Color(0xFFFFA299),
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
//            // Draw background circle
//            drawArc(
//                color = backgroundColor,
//                startAngle = 0f,
//                sweepAngle = 360f,
//                useCenter = false,
//                style = Stroke(width = ringThickness.toPx(), cap = StrokeCap.Round)
//            )

            // Draw secondary marks for indicator
            val radius = (size / 2 - ringThickness / 2).toPx()
            val angleStep = 360f / 180  // 40 marks as shown in the image
            for (i in 0 until 180) {
                val angleInRadians = (i * angleStep - 90) * (PI / 180f)
                val startX = size.toPx() / 2 + cos(angleInRadians) * radius
                val startY = size.toPx() / 2 + sin(angleInRadians) * radius
                val endRadius = radius + ringThickness.toPx() / 1.0f
                val endX = size.toPx() / 2 + cos(angleInRadians) * endRadius
                val endY = size.toPx() / 2 + sin(angleInRadians) * endRadius
                drawLine(
                    color = secondaryMarksColor,
                    start = Offset(startX.toFloat(), startY.toFloat()),
                    end = Offset(endX.toFloat(), endY.toFloat()),
                    strokeWidth = 2f
                )
            }

//            // Draw middle marks between outer ring and text
//            val middleRadius =
//                radius - ringThickness.toPx() / 1.5f // Adjust to place between outer and text
//            val angleStepMiddle = 360f / 17  // 17 marks for the inner shorter lines
//            for (i in 0 until 17) {
//                val angleInRadians = (i * angleStepMiddle - 90) * (PI / 180f)
//                val startX = size.toPx() / 2 + cos(angleInRadians) * middleRadius
//                val startY = size.toPx() / 2 + sin(angleInRadians) * middleRadius
//                val endRadius = middleRadius + ringThickness.toPx() / 5.0f
//                val endX = size.toPx() / 2 + cos(angleInRadians) * endRadius
//                val endY = size.toPx() / 2 + sin(angleInRadians) * endRadius
//                drawLine(
//                    color = progressColor,
//                    start = Offset(startX.toFloat(), startY.toFloat()),
//                    end = Offset(endX.toFloat(), endY.toFloat()),
//                    strokeWidth = 4f
//                )
//            }

            // Draw progress arc
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = ringThickness.toPx(), cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$currentCalories",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = "kcal",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@PreviewPhone
@Composable
fun CircularKcalIndicatorPreview() {
    DefaultPreviewPhone(
        modifier = Modifier.safeContentPadding()
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            CircularKcalIndicator(
                progress = 0.6f,
                currentCalories = 600,
                maxCalories = 1000
            )
        }
    }

}