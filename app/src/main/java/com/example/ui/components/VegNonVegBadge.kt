package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NonVegRed
import com.example.ui.theme.NonVegRedBg
import com.example.ui.theme.VegGreen
import com.example.ui.theme.VegGreenBg

@Composable
fun VegNonVegBadge(
    isVeg: Boolean,
    modifier: Modifier = Modifier,
    showLabel: Boolean = false
) {
    val borderColor = if (isVeg) VegGreen else NonVegRed
    val dotColor = if (isVeg) VegGreen else NonVegRed
    val bgColor = if (isVeg) VegGreenBg else NonVegRedBg
    val label = if (isVeg) "VEG" else "NON-VEG"

    Row(
        modifier = modifier
            .background(bgColor, RoundedCornerShape(4.dp))
            .padding(horizontal = if (showLabel) 6.dp else 2.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Square icon with dot inside (Indian food symbol standard)
        Box(
            modifier = Modifier
                .size(14.dp)
                .border(BorderStroke(1.5.dp, borderColor), RoundedCornerShape(2.dp)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(dotColor, CircleShape)
            )
        }
        if (showLabel) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                color = borderColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        }
    }
}
