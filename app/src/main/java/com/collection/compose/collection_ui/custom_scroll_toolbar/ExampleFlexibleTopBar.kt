package com.collection.compose.collection_ui.custom_scroll_toolbar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExampleFlexibleTopBar(paddingValues: PaddingValues) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Column(
        modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
    ) {
        Text(
            text = "HEADER",
            modifier = Modifier.padding(16.dp),
            style = TextStyle(fontSize = 30.sp)
        )
        Column(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            CustomFlexibleTopAppBar(scrollBehavior = scrollBehavior) {
                CurrentDiamondView()
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = paddingValues.calculateBottomPadding(), top = 16.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(20) {
                    DiamondCard()
                }
            }
        }
    }

}

@Composable
fun CurrentDiamondView() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .border(
                width = 2.dp,
                color = Color(0xFF6200EA), // Border color similar to the image
                shape = RoundedCornerShape(16.dp) // Rounded corners
            )
            .padding(16.dp) // Internal padding for the content
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Text for "Current Diamond"
            Text(
                text = "Current Value",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Row containing diamond icon and count
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Diamond",
                    tint = Color(0xFFAA00FF), // Icon color similar to the image
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "300",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Sync Icon",
                    tint = Color.Gray, // Optional sync icon similar to the image
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun DiamondCard() {
    val gradientColor = Brush.linearGradient(
        colors = listOf(
            Color(0xFF372674),
            Color(0xFFBE24C1),
            Color(0xFFFDA25D)
        ),
    )

    var size by remember { mutableStateOf(IntSize(0, 0)) }

    //ROOT BOX
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .clip(RoundedCornerShape(12.dp))

    ) {
        //CONTENT
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Green)
//                .paint(painter = painterResource(id = R.drawable.box), contentScale = ContentScale.Fit)
                .onGloballyPositioned {
                    size = it.size
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Diamond",
                tint = Color.White,
                modifier = Modifier.size(60.dp)
            )
            Text(
                text = "40",
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        //PRICE
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(with(LocalDensity.current) { size.height.toDp() + 20.dp })
        ) {

            OutlinedCard(
                modifier = Modifier
                    .height(40.dp)
                    .align(Alignment.BottomCenter),
                border = BorderStroke(
                    width = 1.dp,
                    brush = gradientColor
                ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = Color.White,
                ),
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = 18.dp,
                        vertical = 12.dp
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "IDR 35.0000",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 14.sp,
                        color = Color.Magenta
                    )
                }
            }
        }
    }
}