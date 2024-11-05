package com.collection.compose.draggable_rating_bar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.collection.compose.collection_ui.util.DefaultPreviewPhone
import com.collection.compose.collection_ui.util.PreviewPhone

@Composable
fun ExampleDraggableRatingBar() {
    var rating by remember { mutableFloatStateOf(0f) }
    Column(
        modifier = Modifier.safeContentPadding()
    ) {
        Text("RATING $rating")
        RatingBar(
            rating = rating,
            ratingSize = 50.dp,
            onRatingChanged = { newRating ->
                rating = newRating
            },
        )
    }
}

@PreviewPhone
@Composable
fun TextWithOvalUnderlinePreview() {
    DefaultPreviewPhone(
        modifier = Modifier.safeContentPadding()
    ) {
        ExampleDraggableRatingBar()
    }

}