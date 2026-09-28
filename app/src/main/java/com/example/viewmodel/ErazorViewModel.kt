package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.ApplicationItem
import com.example.data.ApplicationStatus
import com.example.data.CollegeNotification
import com.example.data.CollegeService
import com.example.data.Department
import com.example.data.MockData
import com.example.data.QueueStatus
import com.example.data.QueueToken
import com.example.data.StudentProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class MainTab {
    HOME,
    MY_QUEUE,
    APPLICATIONS,
    PROFILE
}

enum class SubScreen {
    NONE,
    JOIN_QUEUE,
    QUEUE_CONFIRMATION,
    APPLICATION_DETAILS,
    APPLICATION_FORM,
    APPLICATION_STATUS_VIEW,
    SETTINGS,
    NOTIFICATIONS,
    HELP_SUPPORT
}

data class ErazorUiState(
    val currentTab: MainTab = MainTab.HOME,
    val currentSubScreen: SubScreen = SubScreen.NONE,
    val activeQueue: QueueToken? = MockData.initialActiveQueue(),
    val queueHistory: List<QueueToken> = MockData.initialHistory,
    val departments: List<Department> = MockData.departments,
    val services: List<CollegeService> = MockData.services,
    val applications: List<ApplicationItem> = MockData.initialApplications,
    val notifications: List<CollegeNotification> = MockData.initialNotifications,
    val studentProfile: StudentProfile = StudentProfile(),
    val selectedDepartment: Department? = null,
    val selectedService: CollegeService? = null,
    val selectedApplication: ApplicationItem? = null,
    val showCancelDialog: Boolean = false,
    val showLogoutDialog: Boolean = false,
    val toastMessage: String? = null
)

class ErazorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ErazorUiState())
    val uiState: StateFlow<ErazorUiState> = _uiState.asStateFlow()

    fun selectTab(tab: MainTab) {
        _uiState.update { it.copy(currentTab = tab, currentSubScreen = SubScreen.NONE) }
    }

    fun navigateToSubScreen(subScreen: SubScreen) {
        _uiState.update { it.copy(currentSubScreen = subScreen) }
    }

    fun navigateBack() {
        _uiState.update { state ->
            when (state.currentSubScreen) {
                SubScreen.APPLICATION_FORM -> state.copy(currentSubScreen = SubScreen.APPLICATION_DETAILS)
                SubScreen.QUEUE_CONFIRMATION -> state.copy(currentSubScreen = SubScreen.NONE)
                SubScreen.JOIN_QUEUE -> {
                    if (state.selectedService != null) {
                        state.copy(selectedService = null)
                    } else if (state.selectedDepartment != null) {
                        state.copy(selectedDepartment = null)
                    } else {
                        state.copy(currentSubScreen = SubScreen.NONE)
                    }
                }
                else -> state.copy(currentSubScreen = SubScreen.NONE)
            }
        }
    }

    fun startJoinQueueFlow(department: Department? = null) {
        val targetDept = department ?: _uiState.value.departments.firstOrNull { it.id == "dept_admin" }
        _uiState.update {
            it.copy(
                selectedDepartment = targetDept,
                selectedService = null,
                currentSubScreen = SubScreen.JOIN_QUEUE
            )
        }
    }

    fun selectDepartment(department: Department) {
        _uiState.update {
            it.copy(
                selectedDepartment = department,
                selectedService = null
            )
        }
    }

    fun selectService(service: CollegeService) {
        _uiState.update { it.copy(selectedService = service) }
    }

    fun confirmJoinQueue() {
        val state = _uiState.value
        val dept = state.selectedDepartment ?: state.departments.first()
        val srv = state.selectedService ?: state.services.first { it.departmentId == dept.id }

        // Generate token
        val prefix = when (dept.id) {
            "dept_admin" -> "A"
            "dept_accounts" -> "ACC"
            "dept_library" -> "L"
            "dept_lab" -> "LAB"
            "dept_certificates" -> "C"
            else -> "S"
        }
        val tokenNum = if (dept.id == "dept_admin" && srv.name.contains("Certificate")) "A-024" else "$prefix-${(10..99).random()}"

        val newToken = QueueToken(
            id = "token_${System.currentTimeMillis()}",
            tokenNumber = tokenNum,
            departmentId = dept.id,
            departmentName = dept.name,
            serviceId = srv.id,
            serviceName = srv.name,
            status = QueueStatus.WAITING,
            peopleAhead = if (tokenNum == "A-024") 6 else srv.waitingCount,
            estimatedWaitMinutes = if (tokenNum == "A-024") 18 else srv.estimatedMinutes,
            counterNumber = dept.counters.split("&").first().trim(),
            issuedAt = "Just now"
        )

        val newNotif = CollegeNotification(
            id = "notif_${System.currentTimeMillis()}",
            title = "Token $tokenNum Issued",
            message = "You have joined the queue for ${srv.name}. Token: $tokenNum.",
            timestamp = "Just now",
            isRead = false,
            tokenReference = tokenNum
        )

        _uiState.update {
            it.copy(
                activeQueue = newToken,
                currentSubScreen = SubScreen.QUEUE_CONFIRMATION,
                notifications = listOf(newNotif) + it.notifications,
                toastMessage = "Joined queue! Token $tokenNum"
            )
        }
    }

    fun showCancelQueueDialog(show: Boolean) {
        _uiState.update { it.copy(showCancelDialog = show) }
    }

    fun cancelActiveQueue() {
        val current = _uiState.value.activeQueue ?: return
        val cancelled = current.copy(
            status = QueueStatus.CANCELLED,
            completedAt = "Cancelled by user"
        )
        val notif = CollegeNotification(
            id = "notif_${System.currentTimeMillis()}",
            title = "Token ${current.tokenNumber} Cancelled",
            message = "Your queue token ${current.tokenNumber} has been cancelled.",
            timestamp = "Just now",
            isRead = false
        )

        _uiState.update {
            it.copy(
                activeQueue = null,
                queueHistory = listOf(cancelled) + it.queueHistory,
                notifications = listOf(notif) + it.notifications,
                showCancelDialog = false,
                currentSubScreen = SubScreen.NONE,
                toastMessage = "Queue token ${current.tokenNumber} cancelled"
            )
        }
    }

    /**
     * Demo feature: Demonstrates WAITING -> SERVING -> COMPLETED state transitions
     * as requested in the design specification.
     */
    fun advanceQueueDemo() {
        val current = _uiState.value.activeQueue ?: return
        when (current.status) {
            QueueStatus.WAITING -> {
                if (current.peopleAhead > 3) {
                    val updated = current.copy(peopleAhead = 3, estimatedWaitMinutes = 9)
                    val notif = CollegeNotification(
                        id = "notif_${System.currentTimeMillis()}",
                        title = "Queue Update",
                        message = "3 people are now ahead of you for token ${current.tokenNumber}.",
                        timestamp = "Just now",
                        isRead = false,
                        tokenReference = current.tokenNumber
                    )
                    _uiState.update {
                        it.copy(
                            activeQueue = updated,
                            notifications = listOf(notif) + it.notifications,
                            toastMessage = "Queue moving: 3 people ahead"
                        )
                    }
                } else {
                    // Transition to SERVING
                    val updated = current.copy(
                        status = QueueStatus.SERVING,
                        peopleAhead = 0,
                        estimatedWaitMinutes = 0,
                        counterNumber = "${current.counterNumber} (Now Calling!)"
                    )
                    val notif = CollegeNotification(
                        id = "notif_${System.currentTimeMillis()}",
                        title = "Now Serving: ${current.tokenNumber}",
                        message = "Your token ${current.tokenNumber} is now being served at ${current.counterNumber}. Please proceed to the counter.",
                        timestamp = "Just now",
                        isRead = false,
                        tokenReference = current.tokenNumber
                    )
                    _uiState.update {
                        it.copy(
                            activeQueue = updated,
                            notifications = listOf(notif) + it.notifications,
                            toastMessage = "Your turn! Token ${current.tokenNumber} is being served!"
                        )
                    }
                }
            }
            QueueStatus.SERVING -> {
                // Transition to COMPLETED
                val completed = current.copy(
                    status = QueueStatus.COMPLETED,
                    completedAt = "Today, just now"
                )
                val notif = CollegeNotification(
                    id = "notif_${System.currentTimeMillis()}",
                    title = "Service Completed",
                    message = "Your service for token ${current.tokenNumber} is marked completed. Thank you!",
                    timestamp = "Just now",
                    isRead = false,
                    tokenReference = current.tokenNumber
                )
                _uiState.update {
                    it.copy(
                        activeQueue = null,
                        queueHistory = listOf(completed) + it.queueHistory,
                        notifications = listOf(notif) + it.notifications,
                        toastMessage = "Token ${current.tokenNumber} completed!"
                    )
                }
            }
            else -> {
                // Reset or re-join sample queue
                _uiState.update { it.copy(activeQueue = MockData.initialActiveQueue()) }
            }
        }
    }

    fun openApplicationDetails(app: ApplicationItem) {
        _uiState.update {
            it.copy(
                selectedApplication = app,
                currentSubScreen = SubScreen.APPLICATION_DETAILS
            )
        }
    }

    fun openApplicationForm(app: ApplicationItem) {
        _uiState.update {
            it.copy(
                selectedApplication = app,
                currentSubScreen = SubScreen.APPLICATION_FORM
            )
        }
    }

    fun viewApplicationStatus(app: ApplicationItem) {
        _uiState.update {
            it.copy(
                selectedApplication = app,
                currentSubScreen = SubScreen.APPLICATION_STATUS_VIEW
            )
        }
    }

    fun submitApplication(appId: String, purpose: String, docs: List<String>) {
        val appCode = if (appId == "app_bonafide") "APP-1024" else "APP-${(1025..1999).random()}"
        val updatedApps = _uiState.value.applications.map { item ->
            if (item.id == appId) {
                item.copy(
                    appId = appCode,
                    status = ApplicationStatus.UNDER_REVIEW,
                    appliedDate = "Today",
                    purpose = purpose.ifBlank { "Official academic & administrative verification" },
                    remarks = "Documents uploaded. Submitted for verification by Department Clerk."
                )
            } else {
                item
            }
        }

        val targetApp = updatedApps.firstOrNull { it.id == appId }

        val notif = CollegeNotification(
            id = "notif_${System.currentTimeMillis()}",
            title = "Application Submitted ($appCode)",
            message = "Your ${targetApp?.title ?: "Application"} has been submitted successfully with ID $appCode.",
            timestamp = "Just now",
            isRead = false
        )

        _uiState.update {
            it.copy(
                applications = updatedApps,
                selectedApplication = targetApp,
                notifications = listOf(notif) + it.notifications,
                currentSubScreen = SubScreen.APPLICATION_STATUS_VIEW,
                toastMessage = "Application $appCode submitted successfully!"
            )
        }
    }

    fun advanceApplicationStatusDemo(appId: String) {
        val updatedApps = _uiState.value.applications.map { item ->
            if (item.id == appId) {
                val nextStatus = when (item.status) {
                    ApplicationStatus.NOT_APPLIED -> ApplicationStatus.SUBMITTED
                    ApplicationStatus.SUBMITTED -> ApplicationStatus.DOCS_VERIFIED
                    ApplicationStatus.DOCS_VERIFIED -> ApplicationStatus.UNDER_REVIEW
                    ApplicationStatus.UNDER_REVIEW -> ApplicationStatus.READY_FOR_COLLECTION
                    ApplicationStatus.READY_FOR_COLLECTION -> ApplicationStatus.UNDER_REVIEW
                }
                item.copy(status = nextStatus)
            } else {
                item
            }
        }
        val target = updatedApps.firstOrNull { it.id == appId }
        _uiState.update {
            it.copy(
                applications = updatedApps,
                selectedApplication = target,
                toastMessage = "Status updated: ${target?.status?.label}"
            )
        }
    }

    fun markAllNotificationsRead() {
        val updated = _uiState.value.notifications.map { it.copy(isRead = true) }
        _uiState.update { it.copy(notifications = updated, toastMessage = "All notifications marked as read") }
    }

    fun clearToast() {
        _uiState.update { it.copy(toastMessage = null) }
    }

    fun showLogoutDialog(show: Boolean) {
        _uiState.update { it.copy(showLogoutDialog = show) }
    }
}
