package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StudentProfile
import com.example.ui.theme.CollegeBackground
import com.example.ui.theme.CollegeBorder
import com.example.ui.theme.CollegeNavy
import com.example.ui.theme.CollegeNavyDark
import com.example.ui.theme.CollegeNavyTint
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

@Composable
fun ProfileScreen(
    profile: StudentProfile,
    onNavigateToApplications: () -> Unit,
    onNavigateToQueueHistory: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToHelp: () -> Unit
) {
    var showPersonalInfoDialog by remember { mutableStateOf(false) }
    var showAcademicInfoDialog by remember { mutableStateOf(false) }

    if (showPersonalInfoDialog) {
        AlertDialog(
            onDismissRequest = { showPersonalInfoDialog = false },
            title = {
                Text("Personal Information", fontWeight = FontWeight.Bold, color = CollegeNavy)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    ProfileModalRow("Full Name", profile.name)
                    ProfileModalRow("Email", profile.email)
                    ProfileModalRow("Phone Number", profile.phone)
                    ProfileModalRow("Blood Group", profile.bloodGroup)
                    ProfileModalRow("Residential Status", "Hostel Block C, Room 304")
                }
            },
            confirmButton = {
                TextButton(onClick = { showPersonalInfoDialog = false }) {
                    Text("Close", color = CollegeNavy, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    if (showAcademicInfoDialog) {
        AlertDialog(
            onDismissRequest = { showAcademicInfoDialog = false },
            title = {
                Text("Academic Information", fontWeight = FontWeight.Bold, color = CollegeNavy)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    ProfileModalRow("Student ID", profile.studentId)
                    ProfileModalRow("Roll Number", profile.rollNo)
                    ProfileModalRow("Degree Course", profile.course)
                    ProfileModalRow("Academic Year", profile.year)
                    ProfileModalRow("Department", profile.department)
                    ProfileModalRow("Current Semester", profile.semester)
                    ProfileModalRow("Cumulative GPA", profile.cgpa)
                    ProfileModalRow("Faculty Advisor", profile.facultyAdvisor)
                }
            },
            confirmButton = {
                TextButton(onClick = { showAcademicInfoDialog = false }) {
                    Text("Close", color = CollegeNavy, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CollegeBackground)
            .testTag("profile_scroll_list"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Student Info Header Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("student_profile_header_card"),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CollegeBorder))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(CollegeNavy)
                            .border(3.dp, CollegeNavyTint, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "RS",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = profile.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = CollegeNavyDark
                    )

                    Text(
                        text = "Student ID: ${profile.studentId}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextSecondary,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Course / Year / Dept Badge Group
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF8FAFC))
                            .border(1.dp, CollegeBorder, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Course:", fontSize = 12.sp, color = TextSecondary)
                                Text(profile.course, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Year:", fontSize = 12.sp, color = TextSecondary)
                                Text(profile.year, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Department:", fontSize = 12.sp, color = TextSecondary)
                                Text(profile.department, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                            }
                        }
                    }
                }
            }
        }

        // Profile Sections List
        item {
            Text(
                text = "Student Portal Sections",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CollegeBorder))
            ) {
                Column {
                    ProfileMenuRow(
                        icon = Icons.Default.Person,
                        title = "Personal Information",
                        subtitle = "Email, contact number & emergency details",
                        onClick = { showPersonalInfoDialog = true },
                        testTag = "profile_menu_personal_info"
                    )

                    DividerLine()

                    ProfileMenuRow(
                        icon = Icons.Default.School,
                        title = "Academic Information",
                        subtitle = "CGPA, semester records & faculty advisor",
                        onClick = { showAcademicInfoDialog = true },
                        testTag = "profile_menu_academic_info"
                    )

                    DividerLine()

                    ProfileMenuRow(
                        icon = Icons.Default.Assignment,
                        title = "My Applications",
                        subtitle = "Certificates, bonafide & document requests",
                        onClick = onNavigateToApplications,
                        testTag = "profile_menu_applications"
                    )

                    DividerLine()

                    ProfileMenuRow(
                        icon = Icons.Default.History,
                        title = "Queue History",
                        subtitle = "Completed & past digital token logs",
                        onClick = onNavigateToQueueHistory,
                        testTag = "profile_menu_queue_history"
                    )

                    DividerLine()

                    ProfileMenuRow(
                        icon = Icons.Default.Notifications,
                        title = "Notifications",
                        subtitle = "Queue alerts and status updates",
                        onClick = onNavigateToNotifications,
                        testTag = "profile_menu_notifications"
                    )

                    DividerLine()

                    ProfileMenuRow(
                        icon = Icons.Default.Settings,
                        title = "Settings",
                        subtitle = "Account security, preferences & privacy",
                        onClick = onNavigateToSettings,
                        testTag = "profile_menu_settings"
                    )

                    DividerLine()

                    ProfileMenuRow(
                        icon = Icons.Default.HelpOutline,
                        title = "Help & Support",
                        subtitle = "Campus desk hours, FAQs & contact directory",
                        onClick = onNavigateToHelp,
                        testTag = "profile_menu_help"
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ProfileMenuRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    testTag: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .testTag(testTag),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(CollegeNavyTint),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = CollegeNavy,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = TextSecondary
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Open",
            tint = TextTertiary,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
private fun DividerLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(horizontal = 16.dp)
            .background(CollegeBorder)
    )
}

@Composable
private fun ProfileModalRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 12.sp, color = TextSecondary)
        Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
    }
}
