package br.com.dio.app.repositories.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.app.repositories.data.model.Owner
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.domain.ListUserInfosUseCase
import br.com.dio.app.repositories.domain.ListUserRepositoriesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class MainViewModel(
    private val listUserRepositoriesUseCase: ListUserRepositoriesUseCase
) : ViewModel() {

    private val _repos = MutableLiveData<State>()
    val repos: LiveData<State> = _repos

//    private val _owner = MutableLiveData<State2>()
//    val owner: LiveData<State2> = _owner

    fun getRepoList(user: String){
        viewModelScope.launch {
            listUserRepositoriesUseCase(user)
                .onStart {
                    _repos.postValue(State.Loading)
                }.catch {
                    _repos.postValue(State.Error(it))
                }
                .collect {
                    _repos.postValue(State.Success(it))
                }
        }
    }

//    fun getUserInfos(user: String){
//        viewModelScope.launch {
//            listUserInfosUseCase(user)
//                .onStart {
//                    _owner.postValue(State2.Loading)
//                }.catch {
//                    _owner.postValue(State2.Error(it))
//                }
//                .collect {
//                    _owner.postValue(State2.Success(it))
//                }
//        }
//    }

    sealed class State {
        object Loading : State()
        data class Success(val list: List<Repo>) : State()
        data class Error(val error: Throwable) : State()
    }

//    sealed class State2 {
//        object Loading : State2()
//        data class Success(val owner: Owner) : State2()
//        data class Error(val error: Throwable) : State2()
//    }
}