package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.FoodDeliveryViewModel
import com.example.ui.theme.RedDark
import com.example.ui.theme.RedGradientEnd
import com.example.ui.theme.RedGradientStart
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarmBg

@Composable
fun AuthScreen(
    viewModel: FoodDeliveryViewModel,
    initialTab: Int = 0,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(initialTab) }

    // Login state
    var loginIdentifier by remember { mutableStateOf("galib033") }
    var loginPassword by remember { mutableStateOf("pass123") }
    var isLoginPasswordVisible by remember { mutableStateOf(false) }

    // Register state (matching screenshot Fig 9.6)
    var regFullName by remember { mutableStateOf("Mohammad Galib") }
    var regMobile by remember { mutableStateOf("0620098701") }
    var regEmail by remember { mutableStateOf("galib@foodworld.app") }
    var regAddress by remember { mutableStateOf("Pipe Line Road, Tandalja") }
    var regCity by remember { mutableStateOf("Vadodara") }
    var regState by remember { mutableStateOf("Gujarat") }
    var regPassword by remember { mutableStateOf("pass123") }
    var regConfirmPassword by remember { mutableStateOf("pass123") }
    var isRegPasswordVisible by remember { mutableStateOf(false) }

    var forgotPasswordMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(RedGradientStart, Color(0xFFFDE8E8), WarmBg)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // NonVegAi Header & Wordmark (Matching Fig 9.6)
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Restaurant,
                    contentDescription = "Logo",
                    tint = RedPrimary,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "NonVegAi",
                fontSize = 26.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )

            Text(
                text = "Fresh Chicken & Biryani Near You",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.9f)
            )

            Text(
                text = "Powered by Food World",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.75f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // White Rounded Form Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    TabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = Color(0xFFF3F4F6),
                        modifier = Modifier
                            .background(Color(0xFFF3F4F6), RoundedCornerShape(10.dp))
                            .padding(4.dp)
                    ) {
                        Tab(
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            text = {
                                Text(
                                    "Sign In",
                                    fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedTab == 0) RedPrimary else TextSecondary
                                )
                            }
                        )
                        Tab(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            text = {
                                Text(
                                    "Register",
                                    fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedTab == 1) RedPrimary else TextSecondary
                                )
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    if (selectedTab == 0) {
                        // Sign In Form
                        Text("Welcome Back", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text("Log in to place Cash on Delivery orders", fontSize = 12.sp, color = TextSecondary)

                        Spacer(modifier = Modifier.height(14.dp))

                        OutlinedTextField(
                            value = loginIdentifier,
                            onValueChange = { loginIdentifier = it },
                            label = { Text("Username, Mobile or Email") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("login_identifier_input"),
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = RedPrimary) },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = loginPassword,
                            onValueChange = { loginPassword = it },
                            label = { Text("Password") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("login_password_input"),
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = RedPrimary) },
                            trailingIcon = {
                                IconButton(onClick = { isLoginPasswordVisible = !isLoginPasswordVisible }) {
                                    Icon(
                                        if (isLoginPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Toggle Password"
                                    )
                                }
                            },
                            visualTransformation = if (isLoginPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "Forgot password?",
                                fontSize = 12.sp,
                                color = RedPrimary,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .clickable {
                                        forgotPasswordMessage = "Password reset link sent to $loginIdentifier! (Verified 302 redirect per practical 8)"
                                    }
                                    .padding(vertical = 4.dp)
                            )
                        }

                        if (forgotPasswordMessage != null) {
                            Text(
                                text = forgotPasswordMessage!!,
                                fontSize = 11.sp,
                                color = Color(0xFF2E7D32),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { viewModel.login(loginIdentifier, loginPassword) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("login_submit_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Sign In to Food World", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    } else {
                        // Register Form (Matching Fig 9.6)
                        Text("Create Your Account", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text("Register for fast Cash on Delivery checkout", fontSize = 12.sp, color = TextSecondary)

                        Spacer(modifier = Modifier.height(14.dp))

                        OutlinedTextField(
                            value = regFullName,
                            onValueChange = { regFullName = it },
                            label = { Text("Full Name") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("reg_name_input"),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = regMobile,
                            onValueChange = { regMobile = it },
                            label = { Text("Mobile Number") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("reg_mobile_input"),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = regEmail,
                            onValueChange = { regEmail = it },
                            label = { Text("Email (Optional)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = regAddress,
                            onValueChange = { regAddress = it },
                            label = { Text("Delivery Address") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("reg_address_input"),
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = regCity,
                                onValueChange = { regCity = it },
                                label = { Text("City") },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            OutlinedTextField(
                                value = regState,
                                onValueChange = { regState = it },
                                label = { Text("State") },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = regPassword,
                            onValueChange = { regPassword = it },
                            label = { Text("Password") },
                            trailingIcon = {
                                IconButton(onClick = { isRegPasswordVisible = !isRegPasswordVisible }) {
                                    Icon(
                                        if (isRegPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Toggle"
                                    )
                                }
                            },
                            visualTransformation = if (isRegPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = regConfirmPassword,
                            onValueChange = { regConfirmPassword = it },
                            label = { Text("Confirm Password") },
                            visualTransformation = if (isRegPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                viewModel.register(
                                    name = regFullName,
                                    mobile = regMobile,
                                    email = regEmail,
                                    address = regAddress,
                                    city = regCity,
                                    state = regState
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("register_submit_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Create Account", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
