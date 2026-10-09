package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.ui.FoodDeliveryViewModel
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VegGreen
import com.example.ui.theme.WarmBg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpSupportScreen(
    orderId: String,
    viewModel: FoodDeliveryViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val user by viewModel.currentUser.collectAsState()
    val tickets by viewModel.supportTickets.collectAsState()

    val issueTypes = listOf(
        "Order Delay / Food Delivery Late",
        "Wrong Food Item Received",
        "Food Quality or Packaging Issue",
        "Cash on Delivery / Change Issue",
        "Rider Assistance / Contact Issue",
        "Other Inquiries"
    )

    var selectedIssueType by remember { mutableStateOf(issueTypes[0]) }
    var issueMenuExpanded by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf(user?.email ?: "galib@example.com") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmBg)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Help & Support",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color(0xFFFFEBEE), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Help, contentDescription = null, tint = RedPrimary)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Order Support Request", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text("Linked to Order: $orderId", fontSize = 12.sp, color = RedPrimary, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Select Issue Type", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    Spacer(modifier = Modifier.height(4.dp))

                    ExposedDropdownMenuBox(
                        expanded = issueMenuExpanded,
                        onExpandedChange = { issueMenuExpanded = !issueMenuExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = selectedIssueType,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = issueMenuExpanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = issueMenuExpanded,
                            onDismissRequest = { issueMenuExpanded = false }
                        ) {
                            issueTypes.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(type, fontSize = 13.sp) },
                                    onClick = {
                                        selectedIssueType = type
                                        issueMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text("Describe Your Issue", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .testTag("support_description_input"),
                        placeholder = { Text("Please explain the problem with your order...", fontSize = 13.sp) },
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text("Contact Email", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("support_email_input"),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            viewModel.submitSupportTicket(orderId, selectedIssueType, description, emailInput)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_ticket_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Submit Support Request", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }

            // Existing Tickets
            if (tickets.isNotEmpty()) {
                Text(
                    text = "Your Recent Tickets (${tickets.size})",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                tickets.forEach { ticket ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(ticket.id, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = RedPrimary)
                                Text(ticket.status, fontWeight = FontWeight.SemiBold, fontSize = 11.sp, color = VegGreen)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(ticket.issueType, fontWeight = FontWeight.Medium, fontSize = 13.sp, color = TextPrimary)
                            Text(ticket.description, fontSize = 12.sp, color = TextSecondary)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Created: ${ticket.timestamp} • Order: ${ticket.orderId}", fontSize = 11.sp, color = TextSecondary)
                        }
                    }
                }
            }
        }
    }
}
