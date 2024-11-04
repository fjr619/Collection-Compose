package com.collection.compose.collection_ui

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.collection.compose.collection_ui.util.DefaultPreviewPhone
import com.collection.compose.collection_ui.util.PreviewPhone

fun Modifier.shadowCustom(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    shapeRadius: Dp = 0.dp,
) = composed {
    val paint: Paint = remember { Paint() }
    val blurRadiusPx = with((LocalDensity.current)) { blurRadius.toPx() }
    val maskFilter = remember {
        BlurMaskFilter(blurRadiusPx, BlurMaskFilter.Blur.INNER)
    }
    drawBehind {
        drawIntoCanvas { canvas ->
            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter = maskFilter
            }
            frameworkPaint.color = color.toArgb()

            val leftPixel = offsetX.toPx()
            val topPixel = offsetY.toPx()
            val rightPixel = size.width + leftPixel
            val bottomPixel = size.height + topPixel

            if (shapeRadius > 0.dp) {
                val radiusPx = shapeRadius.toPx()
                canvas.drawRoundRect(
                    left = leftPixel,
                    top = topPixel,
                    right = rightPixel,
                    bottom = bottomPixel,
                    radiusX = radiusPx,
                    radiusY = radiusPx,
                    paint = paint,
                )
            }
            else {
                canvas.drawRect(
                    left = leftPixel,
                    top = topPixel,
                    right = rightPixel,
                    bottom = bottomPixel,
                    paint = paint,
                )
            }
        }
    }
}

@Composable
fun TestShadowCustom() {
    Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(16.dp)
                .size(100.dp)
                .shadowCustom(
                    color = Color.Magenta.copy(alpha = 0.1f),
                    offsetX = (0).dp,
                    offsetY = (10).dp,
                    shapeRadius = 10.dp,
                    blurRadius = 10.dp,
                )

                .clip(MaterialTheme.shapes.medium)
                .background(Color.Green)
        ) {
            Text(text = "AA")
        }

        Spacer(modifier = Modifier.size(16.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(16.dp)
                .size(100.dp)
                .shadowCustom(
                    color = Color.Black.copy(alpha = 0.1f),
                    offsetX = (10).dp,
                    offsetY = (10).dp,
                    shapeRadius = 10.dp,
                    blurRadius = 10.dp,
                )
                .border(width = 1.dp, color = Color.Red, MaterialTheme.shapes.medium)
                .clip(MaterialTheme.shapes.medium)
                .background(Color.Green)
        ) {
            Text(text = "AA")
        }

        Spacer(modifier = Modifier.size(16.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(16.dp)
                .size(100.dp)
                .shadowCustom(
                    color = Color.Black.copy(alpha = 0.1f),
                    offsetX = (-10).dp,
                    offsetY = (10).dp,
                    shapeRadius = 10.dp,
                    blurRadius = 10.dp,
                )

                .clip(MaterialTheme.shapes.medium)
                .background(Color.Green)
        ) {
            Text(text = "AA")
        }
    }
}

@PreviewPhone
@Composable
fun ShadowCUstomPreview() {
    DefaultPreviewPhone(
        modifier = Modifier.safeContentPadding()
    ) {
        TestShadowCustom()
    }
}
