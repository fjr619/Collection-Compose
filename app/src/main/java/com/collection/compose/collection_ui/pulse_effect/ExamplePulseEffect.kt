package com.collection.compose.collection_ui.pulse_effect

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp

@Composable
fun ExamplePulseEffect() {
    Column (
        modifier = Modifier.safeContentPadding().padding(horizontal = 16.dp)
    ) {
        FilledIconButton(
            onClick = { /*TODO*/ },
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier.pulseEffect().size(42.dp)
        ) {
            Icon(Icons.Rounded.Call, null)
        }

        Spacer(Modifier.size(32.dp))

        FilledIconButton(
            onClick = { /*TODO*/ },
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier
                .doublePulseEffect(
                    duration = 500,
                    targetScale = 2f,
                    brush = Brush.radialGradient(
                        0.6f to Color.Yellow,
                        1f to Color.Green,
                    )
                )
                .size(42.dp)
        ) {
            Icon(Icons.Rounded.Call, null)
        }

        Spacer(modifier = Modifier.size(32.dp))

        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            ),
            modifier = Modifier
                .doublePulseEffect(
                    initialScale = 0.5f,
                    targetScale = 1.2f,
                    brush = SolidColor(Color.Red),
                    shape = RoundedCornerShape(16.dp)
                )
                .height(42.dp)
        ) {
            Text("CLICK ME")
        }
    }
}