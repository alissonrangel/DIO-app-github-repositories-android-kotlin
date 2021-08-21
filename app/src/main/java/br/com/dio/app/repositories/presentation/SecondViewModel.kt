package br.com.dio.app.repositories.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.app.repositories.data.model.Owner
import br.com.dio.app.repositories.domain.ListUserInfosUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SecondViewModel(
    private val listUserInfosUseCase: ListUserInfosUseCase
): ViewModel() {

    private val _owner = MutableLiveData<State>()
    val owner: LiveData<State> = _owner

    fun getUserInfos(user: String){
        viewModelScope.launch {
            listUserInfosUseCase(user)
                .onStart {
                    _owner.postValue(State.Loading)
                }.catch {
                    _owner.postValue(State.Error(it))
                }
                .collect {
                    _owner.postValue(State.Success(it))
                }
        }
    }
    sealed class State {
        object Loading : State()
        data class Success(val owner: Owner) : State()
        data class Error(val error: Throwable) : State()
    }
}