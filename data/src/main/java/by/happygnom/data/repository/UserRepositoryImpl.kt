package by.happygnom.data.repository

import android.text.format.DateUtils
import by.happygnom.data.dao.RouteInteractionsDao
import by.happygnom.data.dao.UserDao
import by.happygnom.data.model.RouteBookmarkEntity
import by.happygnom.data.model.RouteLikeEntity
import by.happygnom.data.model.RouteSentEntity
import by.happygnom.data.model.api_specific.ApiUser
import by.happygnom.data.network.UserGateway
import by.happygnom.domain.data_interface.repository.UserRepository
import by.happygnom.domain.model.User

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val routeInteractionsDao: RouteInteractionsDao,
    private val userGateway: UserGateway
) : UserRepository {

    override suspend fun getUser(firebaseUid: String, forceUpdate: Boolean): User? {
        return userDao.ensureIsNotEmpty(firebaseUid, forceUpdate).getUserById(firebaseUid)?.toDomain()
    }

    private suspend fun UserDao.ensureIsNotEmpty(id: String, forceUpdate: Boolean = false) = apply {
        if (this.count() == 0L || forceUpdate) {
            setCurrentUser(id)
        }
    }

    private suspend fun setCurrentUser(id: String) {
        val apiUser = userGateway.getUserById(id)
        val userEntity = apiUser.toEntity()

        val likes = apiUser.likedIds?.map { RouteLikeEntity(it) } ?: emptyList()
        routeInteractionsDao.nukeLike()
        routeInteractionsDao.setLikes(likes)

        val bookmarks = apiUser.bookmarkIds?.map { RouteBookmarkEntity(it) } ?: emptyList()
        routeInteractionsDao.nukeBookmark()
        routeInteractionsDao.setBookmarks(bookmarks)

        val sends = apiUser.sentIds?.map { RouteSentEntity(it) } ?: emptyList()
        routeInteractionsDao.nukeSent()
        routeInteractionsDao.setSends(sends)

        userDao.insert(userEntity)
    }

    override suspend fun registerUser(user: User) {
        return userGateway.registerUser(
            ApiUser(
                user.id,
                user.name,
                user.surname,
                user.nickname,
                user.pictureUrl,
                user.sex,
                user.startDate.time / DateUtils.SECOND_IN_MILLIS,
                user.photo
            )
        )
    }

    override suspend fun updateUser(user: User) {
        return userGateway.updateUser(
            ApiUser(
                user.id,
                user.name,
                user.surname,
                user.nickname,
                user.pictureUrl,
                user.sex,
                user.startDate.time / DateUtils.SECOND_IN_MILLIS,
                user.photo
            )
        )
    }
}
