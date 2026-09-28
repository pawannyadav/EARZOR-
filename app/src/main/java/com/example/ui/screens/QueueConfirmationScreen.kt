package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.NotificationsActive
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.QueueStatus
import com.example.data.QueueToken
import com.example.ui.components.QueueProgressIndicator
import com.example.ui.components.QueueStatusBadge
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
fun QueueConfirmationScreen(
    token: QueueToken,
    onViewQueue: () -> Unit,
    onCancelQueue: () -> Unit,
    onClose: () -> Unit
) {
    var showCancelDialog by remember { mutableStateOf(false) }

    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = {
                Text(
                    text = "Cancel Queue Token?",
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to cancel token ${token.tokenNumber}? You will lose your current position (#${token.peopleAhead + 1}) in the line.",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showCancelDialog = false
                        onCancelQueue()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("confirm_cancel_queue_button")
                ) {
                    Text("Yes, Cancel Token")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCancelDialog = false }) {
                    Text("Keep Position", color = CollegeNavy)
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Queue Confirmation",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = CollegeNavy
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.testTag("queue_confirmation_close_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Main Digital Token Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("digital_token_main_card"),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CollegeBorder))
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header label
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ConfirmationNumber,
                            contentDescription = null,
                            tint = CollegeNavy,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "CAMPUS SERVICE TOKEN",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = CollegeNavy,
                            letterSpacing = 1.2.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Your Token",
                        fontSize = 14.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Large Digital Token Number: e.g. A-024
                    Text(
                        text = token.tokenNumber,
                        fontSize = 54.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = CollegeNavyDark,
                        letterSpacing = 2.sp,
                        modifier = Modifier.testTag("token_number_display")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Status Badge: WAITING
                    QueueStatusBadge(status = token.status)

                    Spacer(modifier = Modifier.height(20.dp))

                    // Divider
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(CollegeBorder)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Token Information List
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TokenDetailRow(
                            label = "Status",
                            value = token.status.name,
                            isHighlighted = true
                        )

                        TokenDetailRow(
                            label = "People ahead",
                            value = "${token.peopleAhead}",
                            isHighlighted = true
                        )

                        TokenDetailRow(
                            label = "Estimated wait",
                            value = "${token.estimatedWaitMinutes} minutes"
                        )

                        TokenDetailRow(
                            label = "Service",
                            value = token.serviceName
                        )

                        TokenDetailRow(
                            label = "Department",
                            value = token.departmentName
                        )

                        TokenDetailRow(
                            label = "Assigned Counter",
                            value = token.counterNumber
                        )
                    }
                }
            }

            // Live Queue Progress Visualizer (You ●, People ahead ● ● ● ● ● ●, Now serving ●)
            QueueProgressIndicator(
                peopleAhead = token.peopleAhead,
                isServing = token.status == QueueStatus.SERVING,
                modifier = Modifier.testTag("queue_progress_indicator")
            )

            // Alert note
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFFEF3C7))
                    .border(1.dp, Color(0xFFFDE68A), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.NotificationsActive,
                        contentDescription = null,
                        tint = Color(0xFFB45309),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "You will receive an in-app alert when 3 people are ahead of you and when your token is called.",
                        fontSize = 12.sp,
                        color = Color(0xFF92400E)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Action Buttons: "View Queue" & "Cancel Queue"
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onViewQueue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("view_queue_button"),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CollegeNavy,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "View Queue",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedButton(
                    onClick = { showCancelDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("cancel_queue_button"),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFDC2626)
                    ),
                    border = ButtonDefaults.outlinedButtonBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFFECACA))
                    )
                ) {
                    Text(
                        text = "Cancel Queue",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TokenDetailRow(
    label: String,
    value: String,
    isHighlighted: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = TextSecondary
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Medium,
            color = if (isHighlighted) CollegeNavy else TextPrimary
        )
    }
}
