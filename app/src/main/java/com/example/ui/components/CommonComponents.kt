package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ApplicationStatus
import com.example.data.QueueStatus
import com.example.ui.theme.CollegeBorder
import com.example.ui.theme.CollegeNavy
import com.example.ui.theme.CollegeNavyTint
import com.example.ui.theme.StatusCancelledBg
import com.example.ui.theme.StatusCancelledBorder
import com.example.ui.theme.StatusCancelledText
import com.example.ui.theme.StatusCompletedBg
import com.example.ui.theme.StatusCompletedBorder
import com.example.ui.theme.StatusCompletedText
import com.example.ui.theme.StatusReviewBg
import com.example.ui.theme.StatusReviewBorder
import com.example.ui.theme.StatusReviewText
import com.example.ui.theme.StatusServingBg
import com.example.ui.theme.StatusServingBorder
import com.example.ui.theme.StatusServingText
import com.example.ui.theme.StatusWaitingBg
import com.example.ui.theme.StatusWaitingBorder
import com.example.ui.theme.StatusWaitingText
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErazorTopAppBar(
    title: String = "ERAZOR",
    subtitle: String? = "College Queue & Services",
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    unreadNotificationCount: Int = 0,
    onNotificationClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(CollegeNavy),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "E",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = CollegeNavy,
                        letterSpacing = 1.sp
                    )
                }
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.testTag("top_bar_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = CollegeNavy
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = onNotificationClick,
                modifier = Modifier.testTag("top_bar_notifications_button")
            ) {
                BadgedBox(
                    badge = {
                        if (unreadNotificationCount > 0) {
                            Badge(
                                containerColor = Color(0xFFDC2626),
                                contentColor = Color.White
                            ) {
                                Text(unreadNotificationCount.toString(), fontSize = 10.sp)
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (unreadNotificationCount > 0) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = CollegeNavy
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = TextPrimary
        )
    )
}

@Composable
fun QueueStatusBadge(status: QueueStatus) {
    val (bgColor, textColor, borderColor) = when (status) {
        QueueStatus.WAITING -> Triple(StatusWaitingBg, StatusWaitingText, StatusWaitingBorder)
        QueueStatus.SERVING -> Triple(StatusServingBg, StatusServingText, StatusServingBorder)
        QueueStatus.COMPLETED -> Triple(StatusCompletedBg, StatusCompletedText, StatusCompletedBorder)
        QueueStatus.CANCELLED -> Triple(StatusCancelledBg, StatusCancelledText, StatusCancelledBorder)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(6.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = status.label,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
fun ApplicationStatusBadge(status: ApplicationStatus) {
    val (bgColor, textColor, borderColor) = when (status) {
        ApplicationStatus.NOT_APPLIED -> Triple(Color(0xFFF1F5F9), Color(0xFF64748B), CollegeBorder)
        ApplicationStatus.SUBMITTED -> Triple(Color(0xFFFEF3C7), Color(0xFFB45309), Color(0xFFFDE68A))
        ApplicationStatus.DOCS_VERIFIED -> Triple(Color(0xFFE0F2FE), Color(0xFF0369A1), Color(0xFFBAE6FD))
        ApplicationStatus.UNDER_REVIEW -> Triple(StatusReviewBg, StatusReviewText, StatusReviewBorder)
        ApplicationStatus.READY_FOR_COLLECTION -> Triple(StatusServingBg, StatusServingText, StatusServingBorder)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(6.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = status.label,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

fun getDepartmentIcon(iconType: String): ImageVector {
    return when (iconType) {
        "account_balance" -> Icons.Default.AccountBalance
        "business" -> Icons.Default.Business
        "menu_book" -> Icons.Default.MenuBook
        "science" -> Icons.Default.Science
        "description" -> Icons.Default.Description
        "school" -> Icons.Default.School
        else -> Icons.Default.Business
    }
}

/**
 * Queue Progress visualizer as specified in Screen 3:
 * You ●
 * People ahead ● ● ● ● ● ●
 * Now serving ●
 */
@Composable
fun QueueProgressIndicator(
    peopleAhead: Int,
    isServing: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
        shape = RoundedCornerShape(12.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CollegeBorder))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Live Queue Flow",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary,
                letterSpacing = 0.5.sp
            )

            // Step 1: Now Serving (Desk)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(if (isServing) Color(0xFF16A34A) else Color(0xFF0284C7)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isServing) "Now Serving: YOUR TOKEN" else "Now Serving (Active at Counter)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isServing) Color(0xFF15803D) else TextPrimary
                    )
                    Text(
                        text = if (isServing) "Please approach Counter 4 with your ID" else "Officer attending student token at counter",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            }

            // Step 2: People ahead dots
            if (!isServing) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(16.dp)
                    ) {
                        val dotsCount = peopleAhead.coerceIn(1, 6)
                        for (i in 0 until dotsCount) {
                            Box(
                                modifier = Modifier
                                    .padding(vertical = 2.dp)
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF59E0B))
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "People Ahead of You: $peopleAhead",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFB45309)
                        )
                        Text(
                            text = "Estimated service pace: ~3 min per student",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }
            }

            // Step 3: You
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(CollegeNavy),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "You (Your Position in Line)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = CollegeNavy
                    )
                    Text(
                        text = if (isServing) "Status: Serving" else "Position: #${peopleAhead + 1} in line",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}

/**
 * 4-step vertical timeline for Applications:
 * Submitted (✓)
 * Documents Verified (✓)
 * Under Review (●)
 * Ready for Collection (○)
 */
@Composable
fun ApplicationTimeline(
    status: ApplicationStatus,
    modifier: Modifier = Modifier
) {
    val steps = listOf(
        Pair("Submitted", "Application form & initial details registered"),
        Pair("Documents Verified", "Student ID & payment receipts validated"),
        Pair("Under Review", "Academic office & authority verification"),
        Pair("Ready for Collection", "Signed original certificate available at counter")
    )

    val currentStepIndex = when (status) {
        ApplicationStatus.NOT_APPLIED -> -1
        ApplicationStatus.SUBMITTED -> 0
        ApplicationStatus.DOCS_VERIFIED -> 1
        ApplicationStatus.UNDER_REVIEW -> 2
        ApplicationStatus.READY_FOR_COLLECTION -> 3
    }

    Column(modifier = modifier.fillMaxWidth()) {
        steps.forEachIndexed { index, (stepTitle, stepSubtitle) ->
            val isCompleted = index < currentStepIndex || (index == currentStepIndex && status == ApplicationStatus.READY_FOR_COLLECTION)
            val isCurrent = index == currentStepIndex && status != ApplicationStatus.READY_FOR_COLLECTION
            val isPending = index > currentStepIndex

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(28.dp)
                ) {
                    // Node
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isCompleted -> Color(0xFF16A34A)
                                    isCurrent -> CollegeNavy
                                    else -> Color.White
                                }
                            )
                            .border(
                                2.dp,
                                when {
                                    isCompleted -> Color(0xFF16A34A)
                                    isCurrent -> CollegeNavy
                                    else -> Color(0xFFCBD5E1)
                                },
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            isCompleted -> {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Completed",
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                            isCurrent -> {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                )
                            }
                            else -> {
                                Text(
                                    text = (index + 1).toString(),
                                    fontSize = 11.sp,
                                    color = Color(0xFF94A3B8),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    // Vertical connecting line
                    if (index < steps.size - 1) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(36.dp)
                                .background(
                                    if (isCompleted) Color(0xFF16A34A) else Color(0xFFE2E8F0)
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier
                        .padding(bottom = if (index < steps.size - 1) 16.dp else 4.dp)
                        .weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stepTitle,
                            fontSize = 14.sp,
                            fontWeight = if (isCurrent || isCompleted) FontWeight.Bold else FontWeight.Medium,
                            color = when {
                                isCurrent -> CollegeNavy
                                isCompleted -> Color(0xFF15803D)
                                else -> TextTertiary
                            }
                        )
                        if (isCompleted) {
                            Text(
                                text = "Done",
                                fontSize = 11.sp,
                                color = Color(0xFF16A34A),
                                fontWeight = FontWeight.SemiBold
                            )
                        } else if (isCurrent) {
                            Text(
                                text = "In Progress",
                                fontSize = 11.sp,
                                color = CollegeNavy,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Text(
                        text = stepSubtitle,
                        fontSize = 12.sp,
                        color = TextSecondary,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}
