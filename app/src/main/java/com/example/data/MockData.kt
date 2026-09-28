package com.example.data

object MockData {

    val departments = listOf(
        Department(
            id = "dept_admin",
            name = "Administration",
            description = "General administrative services & verification",
            iconType = "business",
            totalWaiting = 7,
            openHours = "9:00 AM - 4:30 PM",
            counters = "Counters 3 & 4"
        ),
        Department(
            id = "dept_accounts",
            name = "Accounts",
            description = "Fees, payments & account services",
            iconType = "account_balance",
            totalWaiting = 12,
            openHours = "9:30 AM - 4:00 PM",
            counters = "Counters 1 & 2"
        ),
        Department(
            id = "dept_library",
            name = "Library",
            description = "Library support & book services",
            iconType = "menu_book",
            totalWaiting = 4,
            openHours = "8:30 AM - 6:00 PM",
            counters = "Circulation Desk"
        ),
        Department(
            id = "dept_lab",
            name = "Laboratory",
            description = "Lab access, equipment & consumables",
            iconType = "science",
            totalWaiting = 3,
            openHours = "9:00 AM - 5:00 PM",
            counters = "Central Store & Lab Office"
        ),
        Department(
            id = "dept_certificates",
            name = "Certificate / Documents",
            description = "Degree, marksheet, bonafide & verification",
            iconType = "description",
            totalWaiting = 8,
            openHours = "10:00 AM - 3:30 PM",
            counters = "Counter 5"
        ),
        Department(
            id = "dept_student_sec",
            name = "Student Section",
            description = "Admissions, identity cards & general queries",
            iconType = "school",
            totalWaiting = 5,
            openHours = "9:00 AM - 4:30 PM",
            counters = "Counter 6"
        )
    )

    val services = listOf(
        // Administration
        CollegeService(
            id = "srv_admin_cert",
            departmentId = "dept_admin",
            departmentName = "Administration",
            name = "Certificate Verification",
            description = "Verification of college certificates, bona fide attestations and official signatures",
            estimatedMinutes = 25,
            averageServiceMinutes = 3,
            waitingCount = 8
        ),
        CollegeService(
            id = "srv_admin_nodues",
            departmentId = "dept_admin",
            departmentName = "Administration",
            name = "No Dues Clearance",
            description = "Inter-departmental clearance stamp for semester registration or graduation",
            estimatedMinutes = 15,
            averageServiceMinutes = 2,
            waitingCount = 4
        ),
        CollegeService(
            id = "srv_admin_general",
            departmentId = "dept_admin",
            departmentName = "Administration",
            name = "General Administrative Enquiries",
            description = "Timetable clarifications, official letter submissions and notices",
            estimatedMinutes = 12,
            averageServiceMinutes = 2,
            waitingCount = 3
        ),

        // Accounts
        CollegeService(
            id = "srv_acc_fee",
            departmentId = "dept_accounts",
            departmentName = "Accounts",
            name = "Fee Payment & Challan Clearance",
            description = "Tuition fees, hostel charges and payment receipt counter endorsement",
            estimatedMinutes = 30,
            averageServiceMinutes = 4,
            waitingCount = 12
        ),
        CollegeService(
            id = "srv_acc_fine",
            departmentId = "dept_accounts",
            departmentName = "Accounts",
            name = "Fine Waiver & Assessment",
            description = "Late submission fine reconciliation and installment payment requests",
            estimatedMinutes = 18,
            averageServiceMinutes = 3,
            waitingCount = 6
        ),
        CollegeService(
            id = "srv_acc_refund",
            departmentId = "dept_accounts",
            departmentName = "Accounts",
            name = "Caution Money & Security Deposit Refund",
            description = "Security deposit verification and bank account RTGS processing",
            estimatedMinutes = 22,
            averageServiceMinutes = 4,
            waitingCount = 5
        ),

        // Library
        CollegeService(
            id = "srv_lib_return",
            departmentId = "dept_library",
            departmentName = "Library",
            name = "Book Return & Overdue Clearance",
            description = "Return borrowed textbooks, clear overdue fines and re-issue cards",
            estimatedMinutes = 10,
            averageServiceMinutes = 2,
            waitingCount = 4
        ),
        CollegeService(
            id = "srv_lib_card",
            departmentId = "dept_library",
            departmentName = "Library",
            name = "Library Card & Digital Access",
            description = "Activate remote library portal access and RFID smart student card",
            estimatedMinutes = 15,
            averageServiceMinutes = 3,
            waitingCount = 3
        ),

        // Laboratory
        CollegeService(
            id = "srv_lab_issue",
            departmentId = "dept_lab",
            departmentName = "Laboratory",
            name = "Hardware & Component Issue",
            description = "Issuance of microcontrollers, lab toolkits and testing devices",
            estimatedMinutes = 12,
            averageServiceMinutes = 3,
            waitingCount = 3
        ),
        CollegeService(
            id = "srv_lab_project",
            departmentId = "dept_lab",
            departmentName = "Laboratory",
            name = "Project Lab Access Permission",
            description = "After-hours laboratory access and bench space allocation slip",
            estimatedMinutes = 10,
            averageServiceMinutes = 2,
            waitingCount = 2
        ),

        // Certificate / Documents
        CollegeService(
            id = "srv_doc_transcript",
            departmentId = "dept_certificates",
            departmentName = "Certificate / Documents",
            name = "Degree Transcript Verification",
            description = "Official semester transcript verification and sealed envelope attestation",
            estimatedMinutes = 24,
            averageServiceMinutes = 4,
            waitingCount = 8
        ),
        CollegeService(
            id = "srv_doc_provisional",
            departmentId = "dept_certificates",
            departmentName = "Certificate / Documents",
            name = "Provisional Certificate Issue",
            description = "Collection and signature verification for provisional degree certificates",
            estimatedMinutes = 18,
            averageServiceMinutes = 3,
            waitingCount = 5
        ),

        // Student Section
        CollegeService(
            id = "srv_stu_bus",
            departmentId = "dept_student_sec",
            departmentName = "Student Section",
            name = "Bus / Train Concession Endorsement",
            description = "Subsidized public transport concession pass form verification",
            estimatedMinutes = 15,
            averageServiceMinutes = 3,
            waitingCount = 5
        ),
        CollegeService(
            id = "srv_stu_id",
            departmentId = "dept_student_sec",
            departmentName = "Student Section",
            name = "Student ID Card Replacement",
            description = "Application submission and verification for lost student identity cards",
            estimatedMinutes = 20,
            averageServiceMinutes = 4,
            waitingCount = 4
        )
    )

    fun initialActiveQueue(): QueueToken = QueueToken(
        id = "token_024",
        tokenNumber = "A-024",
        departmentId = "dept_admin",
        departmentName = "Administration",
        serviceId = "srv_admin_cert",
        serviceName = "Certificate Verification",
        status = QueueStatus.WAITING,
        peopleAhead = 6,
        estimatedWaitMinutes = 18,
        counterNumber = "Counter 4",
        issuedAt = "10:35 AM"
    )

    val initialHistory = listOf(
        QueueToken(
            id = "token_019",
            tokenNumber = "A-019",
            departmentId = "dept_accounts",
            departmentName = "Accounts",
            serviceId = "srv_acc_fee",
            serviceName = "Fee Payment & Challan Clearance",
            status = QueueStatus.COMPLETED,
            peopleAhead = 0,
            estimatedWaitMinutes = 0,
            counterNumber = "Counter 1",
            issuedAt = "02:00 PM",
            completedAt = "Yesterday, 2:18 PM"
        ),
        QueueToken(
            id = "token_031",
            tokenNumber = "L-031",
            departmentId = "dept_library",
            departmentName = "Library",
            serviceId = "srv_lib_return",
            serviceName = "Book Return & Clearance",
            status = QueueStatus.COMPLETED,
            peopleAhead = 0,
            estimatedWaitMinutes = 0,
            counterNumber = "Circulation Desk",
            issuedAt = "11:15 AM",
            completedAt = "22 Sep 2026, 11:28 AM"
        ),
        QueueToken(
            id = "token_008",
            tokenNumber = "S-008",
            departmentId = "dept_student_sec",
            departmentName = "Student Section",
            serviceId = "srv_stu_bus",
            serviceName = "Bus Pass Endorsement",
            status = QueueStatus.CANCELLED,
            peopleAhead = 0,
            estimatedWaitMinutes = 0,
            counterNumber = "Counter 6",
            issuedAt = "03:40 PM",
            completedAt = "15 Sep 2026, 3:45 PM (Cancelled by user)"
        )
    )

    val initialApplications = listOf(
        ApplicationItem(
            id = "app_bonafide",
            appId = "APP-1024",
            title = "Bonafide Certificate",
            description = "Request an official bonafide certificate for passport, bank account, visa, or educational loan verification.",
            requiredDocuments = listOf(
                "College Student ID Card copy",
                "Current semester fee payment receipt",
                "Completed Bonafide Request Form"
            ),
            status = ApplicationStatus.UNDER_REVIEW,
            appliedDate = "25 Sep 2026",
            purpose = "Bank Account opening & Education Loan subsidy",
            collectionLocation = "Administration Office (Counter 3)",
            remarks = "Documents verified by Academic Clerk. Pending final sign-off by Registrar."
        ),
        ApplicationItem(
            id = "app_character",
            title = "Character Certificate",
            description = "Official verification of student conduct, academic standing, and disciplinary clearance.",
            requiredDocuments = listOf(
                "Student ID Card",
                "Departmental No-Dues Form",
                "Recommendation letter from Faculty Advisor"
            ),
            status = ApplicationStatus.NOT_APPLIED
        ),
        ApplicationItem(
            id = "app_transfer",
            title = "Transfer Certificate",
            description = "Formal certificate required for college transfer, course migration, or post-graduate transition.",
            requiredDocuments = listOf(
                "All Semester Original Marksheets",
                "Central Library Clearance Slip",
                "Hostel Warden Clearance (if applicable)"
            ),
            status = ApplicationStatus.NOT_APPLIED
        ),
        ApplicationItem(
            id = "app_doc_verif",
            title = "Document Verification",
            description = "Official attestation of grade cards, provisional marksheets, and transcripts for higher studies.",
            requiredDocuments = listOf(
                "Original Marksheet photocopies (2 sets)",
                "Verification Fee Challan Receipt",
                "Attestation Request Form"
            ),
            status = ApplicationStatus.NOT_APPLIED
        ),
        ApplicationItem(
            id = "app_scholarship",
            title = "Scholarship Application",
            description = "Submission and institutional verification for State, Central, and Merit-cum-Means scholarships.",
            requiredDocuments = listOf(
                "Income Certificate (Current Financial Year)",
                "Previous Year Marksheets (Minimum 75%)",
                "National Scholarship Portal (NSP) printout"
            ),
            status = ApplicationStatus.NOT_APPLIED
        ),
        ApplicationItem(
            id = "app_id_request",
            title = "Student ID Request",
            description = "Reissue of damaged, lost, or RFID-faulty campus smart identity card.",
            requiredDocuments = listOf(
                "Affidavit / Police General Diary receipt",
                "Replacement fee payment challan",
                "Recent passport-sized photo"
            ),
            status = ApplicationStatus.NOT_APPLIED
        )
    )

    val initialNotifications = listOf(
        CollegeNotification(
            id = "notif_1",
            title = "Token A-024 in Queue",
            message = "Your token A-024 for Certificate Verification is waiting. 6 people are ahead of you.",
            timestamp = "10 mins ago",
            isRead = false,
            tokenReference = "A-024"
        ),
        CollegeNotification(
            id = "notif_2",
            title = "Bonafide Application Update",
            message = "Your Bonafide Certificate application (APP-1024) documents have been verified and is under review.",
            timestamp = "2 hours ago",
            isRead = false
        ),
        CollegeNotification(
            id = "notif_3",
            title = "Queue Update: 3 People Ahead",
            message = "The queue at Administration Counter 4 is moving fast. 3 people are now ahead of you.",
            timestamp = "Yesterday",
            isRead = true,
            tokenReference = "A-024"
        ),
        CollegeNotification(
            id = "notif_4",
            title = "Notice: Accounts Counter Timings",
            message = "Accounts Fee counter will close at 3:30 PM on Friday for weekly audit reconciliation.",
            timestamp = "23 Sep 2026",
            isRead = true
        )
    )
}
