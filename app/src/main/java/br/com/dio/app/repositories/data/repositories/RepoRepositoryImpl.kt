package br.com.dio.app.repositories.data.repositories


import br.com.dio.app.repositories.core.RemoteException
import br.com.dio.app.repositories.data.model.Owner
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.data.services.GitHubService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class RepoRepositoryImpl(
    private val service: GitHubService
) : RepoRepository{
    override suspend fun listRepositories(user: String) = flow {
        try {
            val repoList = service.listRepositories(user)
            emit(repoList)
        }catch (ex: HttpException){
            throw RemoteException(ex.message() ?: "Não foi possível a busca no momento.")
        }

    }

    override suspend fun listUserInfos(user: String) = flow {
        try {
            val owner = service.listUserInfos(user)
            emit(owner)
        }catch (ex: HttpException){
            throw RemoteException(ex.message() ?: "Não foi possível a busca no momento.")
        }
    }
}