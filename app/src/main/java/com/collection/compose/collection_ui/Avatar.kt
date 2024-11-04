package com.collection.compose.collection_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.collection.compose.R
import com.collection.compose.collection_ui.util.DefaultPreviewPhone
import com.collection.compose.collection_ui.util.PreviewPhone

@Composable
fun Avatar() {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = "Dog",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(100.dp)
            .aspectRatio(1f)
//            .background(
//                Brush.linearGradient(
//                    listOf(
//                        Color(0xFFC5E1A5),
//                        Color(0xFF80DEEA)
//                    )
//                )
//            )
//            .padding(8.dp)
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .drawWithCache {
                val path = Path()
                path.addOval(
                    Rect(
                        topLeft = Offset.Zero,
                        bottomRight = Offset(size.width, size.height)
                    )
                )
                onDrawWithContent {
                    clipPath(path) {
                        // this draws the actual image - if you don't call drawContent, it wont
                        // render anything
                        this@onDrawWithContent.drawContent()
                    }
                    val dotSize = size.width / 5.5f
                    // Clip a white border for the content
                    drawCircle(
                        Color.Black,
                        radius = dotSize,
                        center = Offset(
                            x = size.width - dotSize,
                            y = size.height - dotSize
                        ),
                        blendMode = BlendMode.Clear
                    )
                    // draw the red circle indication
                    drawCircle(
                        Color.Red, radius = dotSize * 0.8f,
                        center = Offset(
                            x = size.width - dotSize,
                            y = size.height - dotSize
                        )
                    )
                }
            }
    )
}

@PreviewPhone
@Composable
fun AvatarPreview() {
    DefaultPreviewPhone(
        modifier = Modifier.safeContentPadding()
    ) {
        Box {
            Avatar()
        }
    }

}