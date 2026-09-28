package com.example.data

enum class QueueStatus(val label: String) {
    WAITING("WAITING"),
    SERVING("SERVING"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED")
}

data class Department(
    val id: String,
    val name: String,
    val description: String,
    val iconType: String,
    val totalWaiting: Int,
    val openHours: String = "9:00 AM - 4:30 PM",
    val counters: String = "Counters 1-4"
)

data class CollegeService(
    val id: String,
    val departmentId: String,
    val departmentName: String,
    val name: String,
    val description: String,
    val estimatedMinutes: Int,
    val averageServiceMinutes: Int,
    val waitingCount: Int
)

data class QueueToken(
    val id: String,
    val tokenNumber: String,
    val departmentId: String,
    val departmentName: String,
    val serviceId: String,
    val serviceName: String,
    val status: QueueStatus,
    val peopleAhead: Int,
    val estimatedWaitMinutes: Int,
    val counterNumber: String = "Counter 4",
    val issuedAt: String = "10:30 AM",
    val completedAt: String? = null
)

enum class ApplicationStatus(val label: String) {
    NOT_APPLIED("Not Applied"),
    SUBMITTED("Submitted"),
    DOCS_VERIFIED("Documents Verified"),
    UNDER_REVIEW("UNDER REVIEW"),
    READY_FOR_COLLECTION("Ready for Collection")
}

data class ApplicationItem(
    val id: String,
    val appId: String = "",
    val title: String,
    val description: String,
    val requiredDocuments: List<String>,
    val status: ApplicationStatus = ApplicationStatus.NOT_APPLIED,
    val appliedDate: String = "",
    val purpose: String = "",
    val collectionLocation: String = "Administration Office (Window B)",
    val remarks: String = ""
)

data class StudentProfile(
    val name: String = "Rahul Sharma",
    val studentId: String = "STU2026-1024",
    val rollNo: String = "2022-CS-084",
    val course: String = "B.Tech Computer Science",
    val year: String = "4th Year",
    val department: String = "Computer Science",
    val email: String = "rahul.sharma@college.edu",
    val phone: String = "+91 98765 43210",
    val semester: String = "7th Semester",
    val cgpa: String = "8.6 / 10.0",
    val bloodGroup: String = "B+",
    val facultyAdvisor: String = "Dr. S. K. Roy (HOD CSE)"
)

data class CollegeNotification(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val isRead: Boolean = false,
    val tokenReference: String? = null
)
