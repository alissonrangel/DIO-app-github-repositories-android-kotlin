package br.com.dio.app.repositories.data.repositories

import br.com.dio.app.repositories.data.model.Owner
import br.com.dio.app.repositories.data.model.Repo
import kotlinx.coroutines.flow.Flow


interface RepoRepository {

    suspend fun listRepositories(user: String): Flow<List<Repo>>
    suspend fun listUserInfos(user: String): Flow<Owner>
}