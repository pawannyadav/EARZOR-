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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CollegeBackground
import com.example.ui.theme.CollegeBorder
import com.example.ui.theme.CollegeNavy
import com.example.ui.theme.CollegeNavyDark
import com.example.ui.theme.CollegeNavyTint
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onLogoutClick: () -> Unit
) {
    var pushNotifEnabled by remember { mutableStateOf(true) }
    var selectedLanguage by remember { mutableStateOf("English (Campus Standard)") }
    var activeModalTitle by remember { mutableStateOf<String?>(null) }
    var activeModalContent by remember { mutableStateOf<String?>(null) }
    var showLogoutConfirm by remember { mutableStateOf(false) }

    if (activeModalTitle != null) {
        AlertDialog(
            onDismissRequest = { activeModalTitle = null },
            title = {
                Text(text = activeModalTitle!!, fontWeight = FontWeight.Bold, color = CollegeNavy)
            },
            text = {
                Text(text = activeModalContent ?: "", fontSize = 14.sp, color = TextSecondary, lineHeight = 20.sp)
            },
            confirmButton = {
                TextButton(onClick = { activeModalTitle = null }) {
                    Text("OK", color = CollegeNavy, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    if (showLogoutConfirm) {
        AlertDialog(
            onDismissRequest = { showLogoutConfirm = false },
            title = {
                Text("Log Out of ERAZOR?", fontWeight = FontWeight.Bold, color = TextPrimary)
            },
            text = {
                Text(
                    "You will need your Student Roll Number and College Password to log back into your portal.",
                    fontSize = 14.sp,
                    color = TextSecondary
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutConfirm = false
                        onLogoutClick()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("confirm_logout_button")
                ) {
                    Text("Log Out")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutConfirm = false }) {
                    Text("Cancel", color = CollegeNavy)
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = CollegeNavy
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("settings_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = CollegeNavy
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CollegeBackground)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Section 1: Account
            SettingsSectionHeader(title = "Account")

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(10.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CollegeBorder))
            ) {
                Column {
                    SettingsActionRow(
                        title = "Edit Profile",
                        subtitle = "Update emergency contact and communication address",
                        onClick = {
                            activeModalTitle = "Edit Profile"
                            activeModalContent = "Profile modifications require verification with the Academic Office registrar. Please submit Form #12 at Counter 6."
                        }
                    )
                    SettingsDivider()
                    SettingsActionRow(
                        title = "Change Password",
                        subtitle = "Update campus student portal credentials",
                        onClick = {
                            activeModalTitle = "Change Password"
                            activeModalContent = "A password reset link will be dispatched to rahul.sharma@college.edu via the university central identity server."
                        }
                    )
                }
            }

            // Section 2: Preferences
            SettingsSectionHeader(title = "Preferences")

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(10.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CollegeBorder))
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Queue Push Notifications", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                            Text("Alerts when 3 people ahead and token called", fontSize = 12.sp, color = TextSecondary)
                        }
                        Switch(
                            checked = pushNotifEnabled,
                            onCheckedChange = { pushNotifEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = CollegeNavy
                            )
                        )
                    }

                    SettingsDivider()

                    SettingsActionRow(
                        title = "Language",
                        subtitle = selectedLanguage,
                        onClick = {
                            activeModalTitle = "Select Language"
                            activeModalContent = "ERAZOR currently supports English (Official College Language) and Hindi for campus service notices."
                        }
                    )
                }
            }

            // Section 3: Application
            SettingsSectionHeader(title = "Application")

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(10.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CollegeBorder))
            ) {
                Column {
                    SettingsActionRow(
                        title = "Privacy Policy",
                        subtitle = "Student data protection under university rules",
                        onClick = {
                            activeModalTitle = "University Privacy Policy"
                            activeModalContent = "ERAZOR protects student identity records under Institutional IT Guidelines 2026. Data is strictly processed for campus queue optimization and document verification."
                        }
                    )
                    SettingsDivider()
                    SettingsActionRow(
                        title = "Terms & Conditions",
                        subtitle = "Campus service usage rules & token etiquette",
                        onClick = {
                            activeModalTitle = "Campus Service Terms"
                            activeModalContent = "Students must present their official Student ID card at the assigned counter when their token is called. Tokens expire after 3 calls if unattended."
                        }
                    )
                }
            }

            // Section 4: Support
            SettingsSectionHeader(title = "Support")

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(10.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CollegeBorder))
            ) {
                Column {
                    SettingsActionRow(
                        title = "Help Center",
                        subtitle = "Frequently asked questions regarding queues & tokens",
                        onClick = {
                            activeModalTitle = "Help Center & FAQs"
                            activeModalContent = "1. How do I join a queue?\nSelect department, choose service, and tap Join Queue.\n\n2. What if I miss my turn?\nReport to the counter clerk within 10 minutes to be prioritized in the next slot."
                        }
                    )
                    SettingsDivider()
                    SettingsActionRow(
                        title = "Contact College",
                        subtitle = "Administration Office: helpdesk@college.edu • (011) 2766-9000",
                        onClick = {
                            activeModalTitle = "Contact College Helpdesk"
                            activeModalContent = "Campus Central Helpdesk:\nPhone: (011) 2766-9000\nEmail: helpdesk@college.edu\nWorking Hours: Mon - Fri, 9:00 AM - 4:30 PM"
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Log Out Button at the bottom
            OutlinedButton(
                onClick = { showLogoutConfirm = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("settings_logout_button"),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFDC2626)
                ),
                border = ButtonDefaults.outlinedButtonBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFFECACA))
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                    tint = Color(0xFFDC2626),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Log Out",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = CollegeNavyDark,
        modifier = Modifier.padding(start = 2.dp)
    )
}

@Composable
fun SettingsActionRow(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Text(text = subtitle, fontSize = 12.sp, color = TextSecondary, modifier = Modifier.padding(top = 2.dp))
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = TextTertiary,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun SettingsDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(horizontal = 16.dp)
            .background(CollegeBorder)
    )
}
