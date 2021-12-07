package by.happygnom.data.repository

import by.happygnom.data.dao.RoutesDao
import by.happygnom.data.dao.UserDao
import by.happygnom.data.network.RoutesGateway
import by.happygnom.data.network.UserGateway
import by.happygnom.domain.data_interface.repository.RoutesRepository
import by.happygnom.domain.data_interface.repository.UserRepository
import by.happygnom.domain.model.Route
import by.happygnom.domain.model.User

class UserRepositoryImpl(
    private val userGateway: UserGateway,
    private val userDao: UserDao
) : UserRepository {

    //    override suspend fun getAllUsers(forceUpdate: Boolean): List<User> {
//        return userDao.ensureIsNotEmpty(forceUpdate).getAll().map { it }
//    }
//
//    override suspend fun getUserByIdToken(idToken: String, forceUpdate: Boolean): User {
//        return userDao.ensureIsNotEmpty(forceUpdate).getByIdToken(idToken)?.toDomain()
//    }
//
//    private suspend fun UserDao.ensureIsNotEmpty(forceUpdate: Boolean = false) = apply {
//        if (this.count() == 0L || forceUpdate) {
//            updateAllUsers()
//        }
//    }
    override suspend fun getAllUsers(forceUpdate: Boolean): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByIdToken(idToken: String, forceUpdate: Boolean): User? {
        TODO("Not yet implemented")
    }
}