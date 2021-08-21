package br.com.dio.app.repositories.presentation.di

import br.com.dio.app.repositories.domain.ListUserRepositoriesUseCase
import br.com.dio.app.repositories.presentation.MainViewModel
import br.com.dio.app.repositories.presentation.SecondViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {

    fun load(){
        loadKoinModules(viewModelModule())
    }

    private fun viewModelModule(): Module {
        return module {
            viewModel {
                MainViewModel(get(), get())
            }
//            viewModel {
//                SecondViewModel(get())
//            }
        }
    }

//    private fun viewModelModule2(): Module {
//        return module {
//            viewModel {
//                SecondViewModel(get())
//            }
//        }
//    }
}