package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
import com.example.data.ApplicationItem
import com.example.data.StudentProfile
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
fun ApplicationFormScreen(
    application: ApplicationItem,
    studentProfile: StudentProfile,
    onSubmit: (purpose: String, docs: List<String>) -> Unit,
    onBack: () -> Unit
) {
    var currentStep by remember { mutableIntStateOf(1) }
    var purposeText by remember { mutableStateOf("Education Loan and Bank Account Verification") }
    var remarksText by remember { mutableStateOf("Urgent clearance requested for bank subsidy deadline.") }

    val attachedDocs = remember { mutableStateListOf<String>() }
    // Pre-select first document
    remember {
        if (application.requiredDocuments.isNotEmpty()) {
            attachedDocs.add(application.requiredDocuments[0])
        }
        true
    }

    var agreedToDeclaration by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Apply: ${application.title}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = CollegeNavy
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (currentStep > 1) {
                                currentStep -= 1
                            } else {
                                onBack()
                            }
                        },
                        modifier = Modifier.testTag("app_form_back_button")
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
        },
        bottomBar = {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CollegeBorder))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (currentStep > 1) {
                        OutlinedButton(
                            onClick = { currentStep -= 1 },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = ButtonDefaults.outlinedButtonBorder().copy(
                                brush = androidx.compose.ui.graphics.SolidColor(CollegeBorder)
                            )
                        ) {
                            Text("Previous", color = TextPrimary)
                        }
                    }

                    Button(
                        onClick = {
                            if (currentStep < 3) {
                                currentStep += 1
                            } else {
                                onSubmit(purposeText, attachedDocs.toList())
                            }
                        },
                        enabled = if (currentStep == 3) agreedToDeclaration else true,
                        modifier = Modifier
                            .weight(if (currentStep == 1) 2f else 1f)
                            .height(48.dp)
                            .testTag("app_form_next_button"),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CollegeNavy,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = if (currentStep == 3) "Submit Application" else "Next Step",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
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
            // Step Progress Indicator
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(10.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CollegeBorder))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StepItem(number = 1, title = "Details", isCurrent = currentStep == 1, isCompleted = currentStep > 1)
                    Box(modifier = Modifier.width(28.dp).height(1.dp).background(CollegeBorder))
                    StepItem(number = 2, title = "Documents", isCurrent = currentStep == 2, isCompleted = currentStep > 2)
                    Box(modifier = Modifier.width(28.dp).height(1.dp).background(CollegeBorder))
                    StepItem(number = 3, title = "Review", isCurrent = currentStep == 3, isCompleted = false)
                }
            }

            when (currentStep) {
                1 -> {
                    // Step 1: Student Info & Purpose
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CollegeBorder))
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Applicant Information",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )

                            // Read-only student info summary
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFF8FAFC))
                                    .border(1.dp, CollegeBorder, RoundedCornerShape(8.dp))
                                    .padding(12.dp)
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = studentProfile.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = CollegeNavy
                                    )
                                    Text(
                                        text = "ID: ${studentProfile.studentId} • Roll: ${studentProfile.rollNo}",
                                        fontSize = 12.sp,
                                        color = TextSecondary
                                    )
                                    Text(
                                        text = "${studentProfile.course} (${studentProfile.year})",
                                        fontSize = 12.sp,
                                        color = TextSecondary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Purpose of Application *",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )

                            OutlinedTextField(
                                value = purposeText,
                                onValueChange = { purposeText = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("app_form_purpose_input"),
                                shape = RoundedCornerShape(8.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = CollegeNavy,
                                    unfocusedBorderColor = CollegeBorder
                                ),
                                placeholder = { Text("e.g. Bank Account opening, Visa, Scholarship") }
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Additional Remarks (Optional)",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )

                            OutlinedTextField(
                                value = remarksText,
                                onValueChange = { remarksText = it },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 2,
                                shape = RoundedCornerShape(8.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = CollegeNavy,
                                    unfocusedBorderColor = CollegeBorder
                                ),
                                placeholder = { Text("Any urgent timeline or specific notes for the office clerk...") }
                            )
                        }
                    }
                }

                2 -> {
                    // Step 2: Upload Documents
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CollegeBorder))
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Text(
                                text = "Required Document Attachments",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Tap each document to attach verification files from your student records.",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )

                            application.requiredDocuments.forEach { doc ->
                                val isAttached = attachedDocs.contains(doc)
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            if (isAttached) {
                                                attachedDocs.remove(doc)
                                            } else {
                                                attachedDocs.add(doc)
                                            }
                                        }
                                        .testTag("doc_item_$doc"),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isAttached) Color(0xFFF0FDF4) else Color(0xFFF8FAFC)
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    border = CardDefaults.outlinedCardBorder().copy(
                                        brush = androidx.compose.ui.graphics.SolidColor(
                                            if (isAttached) Color(0xFF86EFAC) else CollegeBorder
                                        )
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(32.dp)
                                                .clip(CircleShape)
                                                .background(if (isAttached) Color(0xFF16A34A) else Color(0xFFE2E8F0)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = if (isAttached) Icons.Default.Check else Icons.Default.AttachFile,
                                                contentDescription = null,
                                                tint = if (isAttached) Color.White else TextSecondary,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(12.dp))

                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = doc,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = TextPrimary
                                            )
                                            Text(
                                                text = if (isAttached) "Document verified & attached" else "Tap to attach simulated copy",
                                                fontSize = 11.sp,
                                                color = if (isAttached) Color(0xFF15803D) else TextTertiary
                                            )
                                        }

                                        Text(
                                            text = if (isAttached) "Attached" else "Attach",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isAttached) Color(0xFF15803D) else CollegeNavy
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                3 -> {
                    // Step 3: Review & Submit
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CollegeBorder))
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Review Application",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Application Type:", fontSize = 13.sp, color = TextSecondary)
                                Text(application.title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Student:", fontSize = 13.sp, color = TextSecondary)
                                Text("${studentProfile.name} (${studentProfile.studentId})", fontSize = 13.sp, color = TextPrimary)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Purpose:", fontSize = 13.sp, color = TextSecondary)
                                Text(purposeText, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Attached Documents:", fontSize = 13.sp, color = TextSecondary)
                                Text("${attachedDocs.size} files", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF15803D))
                            }

                            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(CollegeBorder))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { agreedToDeclaration = !agreedToDeclaration }
                            ) {
                                Checkbox(
                                    checked = agreedToDeclaration,
                                    onCheckedChange = { agreedToDeclaration = it },
                                    colors = CheckboxDefaults.colors(checkedColor = CollegeNavy)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "I confirm that all submitted details and uploaded records are genuine.",
                                    fontSize = 12.sp,
                                    color = TextSecondary,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StepItem(
    number: Int,
    title: String,
    isCurrent: Boolean,
    isCompleted: Boolean
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(
                    when {
                        isCompleted -> Color(0xFF16A34A)
                        isCurrent -> CollegeNavy
                        else -> Color(0xFFE2E8F0)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            } else {
                Text(
                    text = number.toString(),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isCurrent) Color.White else TextTertiary
                )
            }
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
            color = if (isCurrent) CollegeNavy else TextTertiary
        )
    }
}
