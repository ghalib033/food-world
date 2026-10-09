package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.Order
import com.example.ui.theme.NonVegRed
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun CancelOrderDialog(
    order: Order,
    onDismiss: () -> Unit,
    onConfirmCancel: (reason: String) -> Unit
) {
    val predefinedReasons = listOf(
        "Ordered by mistake",
        "Want to change my order",
        "Wrong delivery address",
        "Delivery is taking too long",
        "Payment-related issue",
        "Found a better option",
        "Other"
    )

    var selectedReason by remember { mutableStateOf<String?>(null) }
    var otherReasonText by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isSubmitting by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = {
            if (!isSubmitting) onDismiss()
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color(0xFFFFEBEE), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.WarningAmber,
                        contentDescription = "Cancel Warning",
                        tint = NonVegRed,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Cancel Order ${order.id}",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Amount: ₹${String.format("%.2f", order.totalAmount)}",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 380.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Why do you want to cancel this order?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = RedPrimary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                predefinedReasons.forEach { reason ->
                    val isSelected = selectedReason == reason
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                            .background(
                                if (isSelected) Color(0xFFFFEBEE).copy(alpha = 0.5f) else Color.Transparent,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                selectedReason = reason
                                errorMessage = null
                            }
                            .padding(horizontal = 4.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = {
                                selectedReason = reason
                                errorMessage = null
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = RedPrimary,
                                unselectedColor = Color.Gray
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = reason,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isSelected) TextPrimary else TextSecondary
                        )
                    }
                }

                if (selectedReason == "Other") {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = otherReasonText,
                        onValueChange = {
                            otherReasonText = it
                            if (it.isNotBlank()) errorMessage = null
                        },
                        label = { Text("Specify your reason", fontSize = 12.sp) },
                        placeholder = { Text("Enter detailed reason here...", fontSize = 12.sp) },
                        isError = errorMessage != null && otherReasonText.isBlank(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("other_cancellation_reason_input"),
                        shape = RoundedCornerShape(8.dp),
                        maxLines = 3
                    )
                }

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage ?: "",
                        color = NonVegRed,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (selectedReason == null) {
                        errorMessage = "Please select a cancellation reason before proceeding."
                        return@Button
                    }
                    if (selectedReason == "Other" && otherReasonText.trim().isBlank()) {
                        errorMessage = "Please enter an explanation for 'Other'."
                        return@Button
                    }
                    isSubmitting = true
                    val finalReason = if (selectedReason == "Other") {
                        "Other: ${otherReasonText.trim()}"
                    } else {
                        selectedReason!!
                    }
                    onConfirmCancel(finalReason)
                },
                colors = ButtonDefaults.buttonColors(containerColor = NonVegRed),
                shape = RoundedCornerShape(8.dp),
                enabled = !isSubmitting,
                modifier = Modifier.testTag("confirm_cancellation_button")
            ) {
                Text("Confirm Cancellation", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp),
                enabled = !isSubmitting,
                modifier = Modifier.testTag("keep_my_order_button")
            ) {
                Text("Keep My Order", color = TextPrimary, fontSize = 13.sp)
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    )
}
