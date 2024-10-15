package com.collection.compose.collection_ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.collection.compose.ui.theme.CollectionComposeTheme
import de.drick.compose.edgetoedgepreviewlib.CameraCutoutMode
import de.drick.compose.edgetoedgepreviewlib.EdgeToEdgeTemplate
import de.drick.compose.edgetoedgepreviewlib.NavigationMode
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileCard() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val toolbarHeight = 200.dp
    val defaultGridPaddingTop = 180.dp
    val defaultProfileImageSize = 80.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }
    val toolbarOffsetHeightPx = remember { mutableFloatStateOf(0f) }
    var tempToolbarOffsetHeightPx by remember { mutableFloatStateOf(0f) }

    val gridScrollState = rememberLazyGridState()

    // Variables to track previous scroll position
    var previousIndex by remember { mutableIntStateOf(0) }
    var previousScrollOffset by remember { mutableIntStateOf(0) }

    // Variable to track scroll direction
    var isScrollDown by remember { mutableStateOf(false) }

    // Track scroll changes and determine direction
    LaunchedEffect(gridScrollState) {
        snapshotFlow {
            Pair(
                gridScrollState.firstVisibleItemIndex,
                gridScrollState.firstVisibleItemScrollOffset
            )
        }.collect { (currentIndex, currentScrollOffset) ->
            isScrollDown = if (currentIndex == previousIndex) {
                // Compare scroll offset if the index is the same
                currentScrollOffset < previousScrollOffset
            } else {
                // Compare item index if it changes
                currentIndex < previousIndex
            }
            // Update previous values
            previousIndex = currentIndex
            previousScrollOffset = currentScrollOffset
        }
    }


    // Modifier to handle collapsing toolbar
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.floatValue + delta
                toolbarOffsetHeightPx.floatValue = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    // Calculate scroll progress and dynamic corner radius using derivedStateOf
    val scrollProgress by remember {
        derivedStateOf {
            // Reverse progress: 1f when not scrolled, 0f when fully scrolled
            (1f - (-toolbarOffsetHeightPx.floatValue / toolbarHeightPx).coerceIn(0f, 1f))
        }
    }

    val cornerRadius by remember {
        derivedStateOf {
            // Start at 24.dp and decrease to 0.dp
            (24.dp * scrollProgress).coerceAtLeast(0.dp)
        }
    }

    // Dynamically calculate topPadding based on scrollProgress
    val gridPaddingTop by remember {
        derivedStateOf {
            // Interpolating between 180.dp and 0.dp based on scrollProgress
            (defaultGridPaddingTop * scrollProgress).coerceAtLeast(0.dp)
        }
    }

    //hanya 30% dari image yang akan overlapping
    //offset di hitung dari jarak defaultGridPaddingTop dikurang dengan 30% dari image size
    val profileImageOffset by remember {
        derivedStateOf {
            ((defaultGridPaddingTop - (defaultProfileImageSize * 30/100)) * scrollProgress).coerceAtLeast(0.dp)
        }
    }
    val profileImageContainerOffsetPx = with(LocalDensity.current) { profileImageOffset.toPx() }

    val profileImageSize by remember {
        derivedStateOf {
            // Interpolate the size between 80.dp and 50.dp based on scrollProgress
            lerp(start = 50.dp, stop = defaultProfileImageSize, fraction = scrollProgress)
        }
    }

    val profileAddImageSize by remember {
        derivedStateOf {
            lerp(start = 20.dp, stop = 32.dp, fraction = scrollProgress)
        }
    }

//    val spacerProfileImage by remember {
//        derivedStateOf {
//            lerp(start = 60.dp, stop = 60.dp, fraction = scrollProgress)
//        }
//    }

    val safeDrawingTop = WindowInsets.safeDrawing
        .only(WindowInsetsSides.Top).asPaddingValues().calculateTopPadding()

    val stickyHeaderContainerPaddingTop by remember {
        derivedStateOf {
            lerp(start = safeDrawingTop, stop = 0.dp, fraction = scrollProgress)
        }
    }

    val profileImageContainerPaddingTop by remember {
        derivedStateOf {
            lerp(start = safeDrawingTop, stop = 0.dp, fraction = scrollProgress)
        }
    }

    LaunchedEffect(key1 = gridPaddingTop, key2 = toolbarOffsetHeightPx) {
        if (gridPaddingTop == 0.dp) {
            tempToolbarOffsetHeightPx = toolbarOffsetHeightPx.floatValue
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
            .then(
                if (!isScrollDown) {
                    Modifier.nestedScroll(nestedScrollConnection)
                } else {
                    if (remember { derivedStateOf { gridScrollState.firstVisibleItemIndex } }.value == 0) {
                        Modifier.nestedScroll(nestedScrollConnection)
                    } else {
                        toolbarOffsetHeightPx.floatValue = tempToolbarOffsetHeightPx
                        Modifier
                    }
                }
            )
    ) {
        // Collapsing toolbar


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(toolbarHeight)
                .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.floatValue.roundToInt()) }
                .background(Color.Green)
        )

        IconButton(
            modifier = Modifier
                .padding(start = 16.dp, top = safeDrawingTop)
                .alpha(((scrollProgress - 0.3f) * (1f / 0.7f)).coerceIn(0f, 1f)),
            onClick = {}
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Filled.Close,
                contentDescription = "",
                tint = Color.Magenta
            )
        }

        LazyVerticalGrid(
            state = gridScrollState,
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = gridPaddingTop)
                .clip(RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius))
                .background(Color.White) // Incremental rounded corners
            ,

            contentPadding = PaddingValues(
                start = 16.dp, end = 16.dp, bottom = WindowInsets.safeDrawing
                    .only(WindowInsetsSides.Bottom).asPaddingValues().calculateBottomPadding()
            )
        ) {
            // Populate grid items
            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(top = stickyHeaderContainerPaddingTop)

                ) {
                    Spacer(modifier = Modifier.height(60.dp)) //spacerProfileImage
                    Text(

                        text = "NAME",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(8.dp)
                    )


                    Text(
                        text = "Bio",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )

//                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            items(12) { index ->
                GridItem(index)
            }
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding( top = profileImageContainerPaddingTop)
                .offset {
                    IntOffset(
                        x = 0,
                        y = profileImageContainerOffsetPx.roundToInt()
                    )
                }
        ) {
            Row(Modifier.height(IntrinsicSize.Max), verticalAlignment = Alignment.Bottom) {
                ProfileImageWithAddButton(
                    modifier = Modifier.size(profileImageSize),
                    modifierAdd = Modifier.size(profileAddImageSize)
                )
                Spacer(Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFE100FF), Color(0xFFFF5C00))
                            ),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite, // replace with your diamond icon
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "20/min",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .border(
                            BorderStroke(1.dp, SolidColor(Color(0xFFDBDBDB))),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "120 Followers",
                        color = Color(0xFF9B51E0),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    IconButton(
                        modifier = Modifier
                            .alpha((1f - (scrollProgress / 0.3f)).coerceIn(0f, 1f)),
                        onClick = {}
                    ) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            imageVector = Icons.Filled.Close,
                            contentDescription = "",
                            tint = Color.Magenta
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GridItem(index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f), // Make grid items square
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Item $index", color = Color.White)
        }
    }
}

@Composable
fun ProfileImageWithAddButton(
    modifier: Modifier = Modifier,
    modifierAdd: Modifier = Modifier,
) {
    Box {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(Color.Blue)
                .border(3.dp, Color.White, CircleShape)
        )

        // Add button (plus icon)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifierAdd
                .size(32.dp)
                .clip(CircleShape)
                .border(3.dp, Color.White, CircleShape)
                .background(Color.Red)
                .align(Alignment.BottomEnd)
                .clickable {

                }
        ) {
            Icon(

                modifier = Modifier.padding(4.dp),
                imageVector = Icons.Filled.Add,
                contentDescription = "",
            )
        }
    }

}


@Preview
@Composable
fun ProfileCardPreview(modifier: Modifier = Modifier) {
    EdgeToEdgeTemplate(
        navMode = NavigationMode.ThreeButton,
        cameraCutoutMode = CameraCutoutMode.Middle,
        showInsetsBorder = true,
        isStatusBarVisible = true,
        isNavigationBarVisible = true,
        isInvertedOrientation = false
    ) {
        CollectionComposeTheme {
            ProfileCard()
        }
    }
}


