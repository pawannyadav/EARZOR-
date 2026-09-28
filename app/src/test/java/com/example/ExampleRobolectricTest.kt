package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.ApplicationStatus
import com.example.data.MockData
import com.example.data.QueueStatus
import com.example.viewmodel.ErazorViewModel
import com.example.viewmodel.MainTab
import com.example.viewmodel.SubScreen
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context verifies ERAZOR app name`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("ERAZOR", appName)
    }

    @Test
    fun `test initial active queue state matches prompt token A-024`() {
        val viewModel = ErazorViewModel()
        val state = viewModel.uiState.value

        assertNotNull(state.activeQueue)
        assertEquals("A-024", state.activeQueue?.tokenNumber)
        assertEquals(QueueStatus.WAITING, state.activeQueue?.status)
        assertEquals(6, state.activeQueue?.peopleAhead)
        assertEquals(18, state.activeQueue?.estimatedWaitMinutes)
        assertEquals("Administration", state.activeQueue?.departmentName)
    }

    @Test
    fun `test queue status transition WAITING to SERVING to COMPLETED`() {
        val viewModel = ErazorViewModel()

        // 1. Initial state: WAITING (6 ahead)
        assertEquals(QueueStatus.WAITING, viewModel.uiState.value.activeQueue?.status)
        assertEquals(6, viewModel.uiState.value.activeQueue?.peopleAhead)

        // 2. First advance: moves to 3 ahead
        viewModel.advanceQueueDemo()
        assertEquals(QueueStatus.WAITING, viewModel.uiState.value.activeQueue?.status)
        assertEquals(3, viewModel.uiState.value.activeQueue?.peopleAhead)

        // 3. Second advance: transitions to SERVING
        viewModel.advanceQueueDemo()
        assertEquals(QueueStatus.SERVING, viewModel.uiState.value.activeQueue?.status)
        assertEquals(0, viewModel.uiState.value.activeQueue?.peopleAhead)

        // 4. Third advance: transitions to COMPLETED and adds to history
        val prevHistoryCount = viewModel.uiState.value.queueHistory.size
        viewModel.advanceQueueDemo()
        assertEquals(null, viewModel.uiState.value.activeQueue)
        assertEquals(prevHistoryCount + 1, viewModel.uiState.value.queueHistory.size)
        assertEquals("A-024", viewModel.uiState.value.queueHistory.first().tokenNumber)
        assertEquals(QueueStatus.COMPLETED, viewModel.uiState.value.queueHistory.first().status)
    }

    @Test
    fun `test join queue flow creates new token and updates notifications`() {
        val viewModel = ErazorViewModel()
        val accountsDept = MockData.departments.first { it.id == "dept_accounts" }
        val feeService = MockData.services.first { it.id == "srv_acc_fee" }

        viewModel.selectDepartment(accountsDept)
        viewModel.selectService(feeService)
        viewModel.confirmJoinQueue()

        val state = viewModel.uiState.value
        assertNotNull(state.activeQueue)
        assertTrue(state.activeQueue!!.tokenNumber.startsWith("ACC-"))
        assertEquals("Accounts", state.activeQueue!!.departmentName)
        assertEquals(SubScreen.QUEUE_CONFIRMATION, state.currentSubScreen)
    }

    @Test
    fun `test submit application moves to under review with APP-1024 code`() {
        val viewModel = ErazorViewModel()
        viewModel.submitApplication("app_bonafide", "Passport Verification", listOf("Student ID", "Fee Receipt"))

        val state = viewModel.uiState.value
        val bonafideApp = state.applications.first { it.id == "app_bonafide" }
        assertEquals("APP-1024", bonafideApp.appId)
        assertEquals(ApplicationStatus.UNDER_REVIEW, bonafideApp.status)
        assertEquals(SubScreen.APPLICATION_STATUS_VIEW, state.currentSubScreen)
    }
}
