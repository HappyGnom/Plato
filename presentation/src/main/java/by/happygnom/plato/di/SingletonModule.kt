package by.happygnom.plato.di

import android.content.Context
import androidx.room.Room
import by.happygnom.data.database.RoutesDatabase
import by.happygnom.data.database.UsersDatabase
import by.happygnom.data.network.*
import by.happygnom.data.repository.CommentsRepositoryImpl
import by.happygnom.data.repository.RoutesRepositoryImpl
import by.happygnom.data.repository.TagsRepositoryImpl
import by.happygnom.data.repository.UserRepositoryImpl
import by.happygnom.domain.data_interface.repository.CommentsRepository
import by.happygnom.domain.data_interface.repository.RoutesRepository
import by.happygnom.domain.data_interface.repository.TagsRepository
import by.happygnom.domain.data_interface.repository.UserRepository
import by.happygnom.domain.model.User
import by.happygnom.domain.usecase.GetUserUseCase
import coil.ImageLoader
import coil.request.ImageRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    fun provideCoilImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader(context)
    }

    @Provides
    fun provideCoilImageRequestBuilder(@ApplicationContext context: Context): ImageRequest.Builder {
        return ImageRequest.Builder(context)
    }

    @Provides
    fun provideKtorClient(): HttpClient {
        return getKtorHttpClient {
            runBlocking {
                Firebase.auth.currentUser?.getIdToken(false)?.await()?.token
            }
        }
    }

    @Provides
    fun provideRoutesDatabase(@ApplicationContext context: Context): RoutesDatabase {
        return Room.databaseBuilder(
            context,
            RoutesDatabase::class.java,
            "RoutesDb"
        ).build()
    }

    @Provides
    fun provideUsersDatabase(@ApplicationContext context: Context): UsersDatabase {
        return Room.databaseBuilder(
            context,
            UsersDatabase::class.java,
            "UsersDB"
        ).build()
    }

    @Provides
    fun provideRoutesRepository(
        @ApplicationContext context: Context,
        routesDatabase: RoutesDatabase,
        ktorClient: HttpClient
    ): RoutesRepository {
        val routesGateway = RoutesGateway(ktorClient)

        return RoutesRepositoryImpl(
            routesGateway,
            routesDatabase.routesDao,
            routesDatabase.routeInteractionsDao
        )
    }

    @Provides
    fun provideTagsRepository(
        @ApplicationContext context: Context,
        routesDatabase: RoutesDatabase,
        ktorClient: HttpClient
    ): TagsRepository {
        val tagsGateway = TagsGateway(ktorClient)

        return TagsRepositoryImpl(tagsGateway, routesDatabase.tagsDao)
    }

    @Provides
    fun provideCommentsRepository(
        @ApplicationContext context: Context,
        ktorClient: HttpClient
    ): CommentsRepository {
        val commentsGateway = CommentsGateway(ktorClient)

        return CommentsRepositoryImpl(commentsGateway)
    }

    @Provides
    fun provideUserRepository(
        @ApplicationContext context: Context,
        routesDatabase: RoutesDatabase,
        usersDatabase: UsersDatabase,
        ktorClient: HttpClient
    ): UserRepository {
        val userGateway = UserGateway(ktorClient)

        return UserRepositoryImpl(
            usersDatabase.userDao,
            routesDatabase.routeInteractionsDao,
            userGateway
        )
    }
}
