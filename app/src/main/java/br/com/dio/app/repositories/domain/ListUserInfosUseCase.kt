package br.com.dio.app.repositories.domain

import br.com.dio.app.repositories.core.UseCase
import br.com.dio.app.repositories.data.model.Owner
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.data.repositories.RepoRepository
import kotlinx.coroutines.flow.Flow

class ListUserInfosUseCase(
    private val repository: RepoRepository
) : UseCase<String, Owner>() {

    override suspend fun execute(param: String): Flow<Owner> {
        return repository.listUserInfos(param)
    }
}