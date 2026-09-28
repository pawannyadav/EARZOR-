package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.ApplicationStatus
import com.example.ui.components.ErazorTopAppBar
import com.example.ui.screens.ApplicationDetailsScreen
import com.example.ui.screens.ApplicationFormScreen
import com.example.ui.screens.ApplicationStatusScreen
import com.example.ui.screens.ApplicationsScreen
import com.example.ui.screens.HelpSupportScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.JoinQueueScreen
import com.example.ui.screens.MyQueueScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.QueueConfirmationScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.CollegeBackground
import com.example.ui.theme.CollegeNavy
import com.example.ui.theme.CollegeNavyTint
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.ErazorViewModel
import com.example.viewmodel.MainTab
import com.example.viewmodel.SubScreen

@Composable
fun MainAppScreen(
    viewModel: ErazorViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.toastMessage) {
        uiState.toastMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearToast()
        }
    }

    // Hardware/gesture BackHandler handling
    BackHandler(enabled = uiState.currentSubScreen != SubScreen.NONE || uiState.currentTab != MainTab.HOME) {
        if (uiState.currentSubScreen != SubScreen.NONE) {
            viewModel.navigateBack()
        } else {
            viewModel.selectTab(MainTab.HOME)
        }
    }

    val unreadNotifsCount = uiState.notifications.count { !it.isRead }

    // When showing full-screen modal flows that have their own TopAppBar / back navigation:
    when (uiState.currentSubScreen) {
        SubScreen.JOIN_QUEUE -> {
            JoinQueueScreen(
                departments = uiState.departments,
                services = uiState.services,
                selectedDepartment = uiState.selectedDepartment,
                selectedService = uiState.selectedService,
                onSelectDepartment = { viewModel.selectDepartment(it) },
                onSelectService = { viewModel.selectService(it) },
                onConfirmJoin = { viewModel.confirmJoinQueue() },
                onBack = { viewModel.navigateBack() }
            )
            return
        }
        SubScreen.QUEUE_CONFIRMATION -> {
            uiState.activeQueue?.let { token ->
                QueueConfirmationScreen(
                    token = token,
                    onViewQueue = { viewModel.selectTab(MainTab.MY_QUEUE) },
                    onCancelQueue = { viewModel.cancelActiveQueue() },
                    onClose = { viewModel.navigateBack() }
                )
            } ?: run {
                viewModel.navigateBack()
            }
            return
        }
        SubScreen.APPLICATION_DETAILS -> {
            uiState.selectedApplication?.let { app ->
                ApplicationDetailsScreen(
                    application = app,
                    onStartApplication = { viewModel.openApplicationForm(app) },
                    onViewStatus = { viewModel.viewApplicationStatus(app) },
                    onBack = { viewModel.navigateBack() }
                )
            } ?: run {
                viewModel.navigateBack()
            }
            return
        }
        SubScreen.APPLICATION_FORM -> {
            uiState.selectedApplication?.let { app ->
                ApplicationFormScreen(
                    application = app,
                    studentProfile = uiState.studentProfile,
                    onSubmit = { purpose, docs ->
                        viewModel.submitApplication(app.id, purpose, docs)
                    },
                    onBack = { viewModel.navigateBack() }
                )
            } ?: run {
                viewModel.navigateBack()
            }
            return
        }
        SubScreen.APPLICATION_STATUS_VIEW -> {
            val appToDisplay = uiState.selectedApplication
                ?: uiState.applications.firstOrNull { it.status != ApplicationStatus.NOT_APPLIED }
                ?: uiState.applications.first()

            ApplicationStatusScreen(
                application = appToDisplay,
                onAdvanceStatusDemo = { appId -> viewModel.advanceApplicationStatusDemo(appId) },
                onBack = { viewModel.navigateBack() }
            )
            return
        }
        SubScreen.SETTINGS -> {
            SettingsScreen(
                onBack = { viewModel.navigateBack() },
                onLogoutClick = {
                    viewModel.navigateBack()
                    viewModel.selectTab(MainTab.HOME)
                }
            )
            return
        }
        SubScreen.NOTIFICATIONS -> {
            NotificationsScreen(
                notifications = uiState.notifications,
                onMarkAllRead = { viewModel.markAllNotificationsRead() },
                onBack = { viewModel.navigateBack() }
            )
            return
        }
        SubScreen.HELP_SUPPORT -> {
            HelpSupportScreen(
                onBack = { viewModel.navigateBack() }
            )
            return
        }
        SubScreen.NONE -> {
            // Main Scaffold with Bottom Navigation
        }
    }

    Scaffold(
        topBar = {
            ErazorTopAppBar(
                title = "ERAZOR",
                subtitle = when (uiState.currentTab) {
                    MainTab.HOME -> "Skip the line. Manage college services."
                    MainTab.MY_QUEUE -> "Live Tokens & Queue History"
                    MainTab.APPLICATIONS -> "College Document Applications"
                    MainTab.PROFILE -> "Student Service Account"
                },
                unreadNotificationCount = unreadNotifsCount,
                onNotificationClick = { viewModel.navigateToSubScreen(SubScreen.NOTIFICATIONS) }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("main_bottom_nav_bar")
            ) {
                // Tab 1: Home
                NavigationBarItem(
                    selected = uiState.currentTab == MainTab.HOME,
                    onClick = { viewModel.selectTab(MainTab.HOME) },
                    icon = {
                        Icon(
                            imageVector = if (uiState.currentTab == MainTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = {
                        Text(
                            text = "Home",
                            fontSize = 11.sp,
                            fontWeight = if (uiState.currentTab == MainTab.HOME) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CollegeNavy,
                        selectedTextColor = CollegeNavy,
                        indicatorColor = CollegeNavyTint,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    ),
                    modifier = Modifier.testTag("nav_tab_home")
                )

                // Tab 2: My Queue
                NavigationBarItem(
                    selected = uiState.currentTab == MainTab.MY_QUEUE,
                    onClick = { viewModel.selectTab(MainTab.MY_QUEUE) },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (uiState.activeQueue != null) {
                                    Badge(
                                        containerColor = Color(0xFF16A34A),
                                        contentColor = Color.White
                                    ) {
                                        Text("1", fontSize = 9.sp)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (uiState.currentTab == MainTab.MY_QUEUE) Icons.Filled.ConfirmationNumber else Icons.Outlined.ConfirmationNumber,
                                contentDescription = "My Queue"
                            )
                        }
                    },
                    label = {
                        Text(
                            text = "My Queue",
                            fontSize = 11.sp,
                            fontWeight = if (uiState.currentTab == MainTab.MY_QUEUE) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CollegeNavy,
                        selectedTextColor = CollegeNavy,
                        indicatorColor = CollegeNavyTint,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    ),
                    modifier = Modifier.testTag("nav_tab_my_queue")
                )

                // Tab 3: Applications
                NavigationBarItem(
                    selected = uiState.currentTab == MainTab.APPLICATIONS,
                    onClick = { viewModel.selectTab(MainTab.APPLICATIONS) },
                    icon = {
                        Icon(
                            imageVector = if (uiState.currentTab == MainTab.APPLICATIONS) Icons.Filled.Assignment else Icons.Outlined.Assignment,
                            contentDescription = "Applications"
                        )
                    },
                    label = {
                        Text(
                            text = "Applications",
                            fontSize = 11.sp,
                            fontWeight = if (uiState.currentTab == MainTab.APPLICATIONS) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CollegeNavy,
                        selectedTextColor = CollegeNavy,
                        indicatorColor = CollegeNavyTint,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    ),
                    modifier = Modifier.testTag("nav_tab_applications")
                )

                // Tab 4: Profile
                NavigationBarItem(
                    selected = uiState.currentTab == MainTab.PROFILE,
                    onClick = { viewModel.selectTab(MainTab.PROFILE) },
                    icon = {
                        Icon(
                            imageVector = if (uiState.currentTab == MainTab.PROFILE) Icons.Filled.Person else Icons.Outlined.Person,
                            contentDescription = "Profile"
                        )
                    },
                    label = {
                        Text(
                            text = "Profile",
                            fontSize = 11.sp,
                            fontWeight = if (uiState.currentTab == MainTab.PROFILE) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CollegeNavy,
                        selectedTextColor = CollegeNavy,
                        indicatorColor = CollegeNavyTint,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    ),
                    modifier = Modifier.testTag("nav_tab_profile")
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = CollegeBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.currentTab) {
                MainTab.HOME -> {
                    HomeScreen(
                        departments = uiState.departments,
                        activeQueue = uiState.activeQueue,
                        onJoinQueueClick = { dept -> viewModel.startJoinQueueFlow(dept) },
                        onViewActiveQueueClick = { viewModel.selectTab(MainTab.MY_QUEUE) },
                        onServiceClick = { dept -> viewModel.startJoinQueueFlow(dept) },
                        onApplicationsClick = { viewModel.selectTab(MainTab.APPLICATIONS) }
                    )
                }

                MainTab.MY_QUEUE -> {
                    MyQueueScreen(
                        activeQueue = uiState.activeQueue,
                        queueHistory = uiState.queueHistory,
                        onViewDetails = {
                            viewModel.navigateToSubScreen(SubScreen.QUEUE_CONFIRMATION)
                        },
                        onCancelQueue = { viewModel.cancelActiveQueue() },
                        onAdvanceQueueDemo = { viewModel.advanceQueueDemo() },
                        onJoinQueueClick = { viewModel.startJoinQueueFlow(null) }
                    )
                }

                MainTab.APPLICATIONS -> {
                    ApplicationsScreen(
                        applications = uiState.applications,
                        onSelectApplication = { app -> viewModel.openApplicationDetails(app) },
                        onViewStatus = { app -> viewModel.viewApplicationStatus(app) }
                    )
                }

                MainTab.PROFILE -> {
                    ProfileScreen(
                        profile = uiState.studentProfile,
                        onNavigateToApplications = { viewModel.selectTab(MainTab.APPLICATIONS) },
                        onNavigateToQueueHistory = { viewModel.selectTab(MainTab.MY_QUEUE) },
                        onNavigateToNotifications = { viewModel.navigateToSubScreen(SubScreen.NOTIFICATIONS) },
                        onNavigateToSettings = { viewModel.navigateToSubScreen(SubScreen.SETTINGS) },
                        onNavigateToHelp = { viewModel.navigateToSubScreen(SubScreen.HELP_SUPPORT) }
                    )
                }
            }
        }
    }
}
