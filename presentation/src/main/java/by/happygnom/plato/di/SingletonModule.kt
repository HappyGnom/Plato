package by.happygnom.plato.di

import android.content.Context
import androidx.room.Room
import by.happygnom.data.database.RoutesDatabase
import by.happygnom.data.network.CommentsGateway
import by.happygnom.data.network.RoutesGateway
import by.happygnom.data.network.UserGateway
import by.happygnom.data.network.ktorHttpClient
import by.happygnom.data.repository.CommentsRepositoryImpl
import by.happygnom.data.repository.RoutesRepositoryImpl
import by.happygnom.data.repository.UserRepositoryImpl
import by.happygnom.domain.data_interface.repository.CommentsRepository
import by.happygnom.domain.data_interface.repository.RoutesRepository
import by.happygnom.domain.data_interface.repository.UserRepository
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

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
    fun provideRoutesRepository(@ApplicationContext context: Context): RoutesRepository {
        val routesGateway = RoutesGateway(ktorHttpClient)

        val routesDatabase = Room.databaseBuilder(
            context,
            RoutesDatabase::class.java,
            "RoutesDb"
        ).build()

        return RoutesRepositoryImpl(routesGateway, routesDatabase.routesDao)
    }

    @Provides
    fun provideCommentsRepository(@ApplicationContext context: Context): CommentsRepository {
        val commentsGateway = CommentsGateway(ktorHttpClient)

        return CommentsRepositoryImpl(commentsGateway)
    }

    @Provides
    fun provideUserRepository(@ApplicationContext context: Context): UserRepository {
        val userGateway = UserGateway(ktorHttpClient)

        return UserRepositoryImpl(userGateway)
    }
}
