package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.RedGradientEnd
import com.example.ui.theme.RedGradientStart
import com.example.ui.theme.RedPrimary

@Composable
fun LiveTimeHeader(
    userName: String,
    city: String,
    state: String,
    liveTimeString: String,
    onLocationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(RedGradientStart, RedGradientEnd)
                ),
                RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        // Top row: Location chip and Live Time badge
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Location Chip (interactive)
            Row(
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
                    .clickable { onLocationClick() }
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .testTag("location_chip"),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (city.isNotBlank()) "$city, $state" else "All Locations",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand",
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }

            // Live Time Indicator
            Row(
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.25f), RoundedCornerShape(14.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(Color(0xFF4CAF50), CircleShape)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Live clock",
                    tint = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier.size(11.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = liveTimeString.ifBlank { "Live Order Service" },
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Greeting
        Text(
            text = "Hello, $userName 👋",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Delivering hot & fresh food to your doorstep in $city",
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.85f)
        )
    }
}
