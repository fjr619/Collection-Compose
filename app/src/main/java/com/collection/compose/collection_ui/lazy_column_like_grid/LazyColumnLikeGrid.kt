package com.collection.compose.collection_ui.lazy_column_like_grid

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.collection.compose.collection_ui.util.DefaultPreviewPhone
import com.collection.compose.collection_ui.util.PreviewPhone
import kotlin.math.min

@Composable
fun LazyColumnLikeGrid() {
    val items = List(40) { it + 1 }
    val toolbarHeight = 200.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }
    val listState = rememberLazyListState()
    val topStatusBar = WindowInsets.statusBars.asPaddingValues()
        .calculateTopPadding() // Gets the status bar padding
    val topStatusBarPx = with(LocalDensity.current) { topStatusBar.toPx() }

    // Calculate scroll progress as a percentage (0 to 100)
    val scrollProgress by remember {
        derivedStateOf {
            if (listState.firstVisibleItemIndex == 0) {
                min((listState.firstVisibleItemScrollOffset / toolbarHeightPx) * 100, 100f)
            } else 100f
        }
    }

    val dynamicTopPadding = with(LocalDensity.current) {
        (topStatusBarPx * scrollProgress / 100).toDp()

//        //jika mau 50$ progress sudah langsung full
//        if (scrollProgress <= 50f) {
//            (scrollProgress / 50 * topStatusBarPx).toDp()
//        } else {
//            topStatusBarPx.toDp()
//        }
    }

    println("topStatusBarPx $topStatusBarPx $scrollProgress $dynamicTopPadding")

    LazyColumn(
        state = listState,
        modifier = Modifier.padding(top = dynamicTopPadding),
        contentPadding = PaddingValues(
            bottom = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding(),
        )
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(toolbarHeight)
                    .background(Color.Green),
                contentAlignment = Alignment.BottomStart
            ) {
                AsyncImage(
                    model = "https://picsum.photos/seed/picsum/400/200",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .height(30.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .background(MaterialTheme.colorScheme.surface)
                )

                Box(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.Blue)
                            .border(3.dp, Color.White, CircleShape)
                    )

                }
            }
        }

        item {
            Text("Header", modifier = Modifier.padding(horizontal = 16.dp))
        }

        // Chunk items by the number of columns, filling in any remaining space with nulls
        items(items.chunked(3), key = {
            it.hashCode()
        }) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                rowItems.forEach { item ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .aspectRatio(1f) // Ensures each box is square
                            .background(
                                color = Color.Yellow
                            )
                    ) {
                        Text(
                            text = item.toString(),
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.Black
                        )
                    }
                }

                // Fill the remaining columns with empty boxes if rowItems.size < columns
                repeat(3 - rowItems.size) {
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}

@PreviewPhone
@Composable
fun LazyColumnLikeGridPreview() {
    DefaultPreviewPhone(
        modifier = Modifier.displayCutoutPadding()
    ) {
        LazyColumnLikeGrid()
    }
}