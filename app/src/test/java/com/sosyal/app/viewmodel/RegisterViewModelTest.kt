package com.sosyal.app.viewmodel

import com.sosyal.app.domain.model.UserCredential
import com.sosyal.app.domain.use_case.auth.RegisterAccountUseCase
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.ui.screen.register.RegisterEvent
import com.sosyal.app.ui.screen.register.RegisterViewModel
import com.sosyal.app.util.ApiResult
import com.sosyal.app.util.TestCoroutineRule
import com.sosyal.app.util.userCredential
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var registerAccountUseCase: RegisterAccountUseCase

    private lateinit var registerViewModel: RegisterViewModel

    private lateinit var onEvent: (RegisterEvent) -> Unit

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(registerAccountUseCase)
        onEvent = registerViewModel::onEvent
    }

    @Test
    fun `Register account should be success`() {
        testCoroutineRule.runTest {
            val apiResult = flowOf(ApiResult.Success(userCredential))

            doReturn(apiResult).`when`(registerAccountUseCase)(
                name = anyString(),
                email = anyString(),
                username = anyString(),
                password = anyString()
            )

            onEvent(RegisterEvent.Register)

            val isSuccess = when (registerViewModel.registerState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be success", true, isSuccess)
        }
    }

    @Test
    fun `Register account should be fail`() {
        testCoroutineRule.runTest {
            val apiResult = flowOf(ApiResult.Error<UserCredential>())

            doReturn(apiResult).`when`(registerAccountUseCase)(
                name = anyString(),
                email = anyString(),
                username = anyString(),
                password = anyString()
            )

            onEvent(RegisterEvent.Register)

            val isSuccess = when (registerViewModel.registerState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }
}