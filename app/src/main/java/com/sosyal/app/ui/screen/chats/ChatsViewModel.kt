package com.sosyal.app.ui.screen.chats

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosyal.app.domain.model.Chat
import com.sosyal.app.domain.use_case.chat.GetChatsUseCase
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.util.ApiResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ChatsViewModel(
    private val getChatsUseCase: GetChatsUseCase
) : ViewModel() {
    var chatsState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    val chats = mutableStateListOf<Chat>()

    private fun getChats() {
        chatsState = UIState.Loading

        viewModelScope.launch {
            getChatsUseCase().catch {
                chatsState = UIState.Error(it.message)
            }.collect {
                chatsState = when (it) {
                    is ApiResult.Success -> {
                        if (it.data != null) {
                            chats.addAll(it.data)
                        }

                        UIState.Success(null)
                    }

                    is ApiResult.Error -> UIState.Error(it.message)
                }
            }
        }
    }
}