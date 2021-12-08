package by.happygnom.data.repository

import android.text.format.DateUtils
import by.happygnom.data.dao.RoutesDao
import by.happygnom.data.dao.UserDao
import by.happygnom.data.model.UserEntity
import by.happygnom.data.network.UserGateway
import by.happygnom.domain.data_interface.repository.UserRepository
import by.happygnom.domain.model.User

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userGateway: UserGateway
) : UserRepository {

    override suspend fun getUser(id: String): User? {
        return userDao.ensureIsNotEmpty(id).getUserById(id)?.toDomain()
    }

    private suspend fun UserDao.ensureIsNotEmpty(id: String) = apply {
        if (this.count() == 0L) {
            setCurrentUser(id)
        }
    }

    private suspend fun setCurrentUser(id: String) {
        val user = userGateway.getUserById(id)
        userDao.insert(user)
    }

    override suspend fun registerUser(user: User) {
        return userGateway.registerUser(
            UserEntity(
                user.id,
                user.name,
                user.surname,
                user.nickname,
                user.pictureUrl,
                user.sex,
                user.startDate.time / DateUtils.SECOND_IN_MILLIS,
//                user.liked,
//                user.sent, user.favorite
            )
        )
    }
}