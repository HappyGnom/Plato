package by.happygnom.plato.di

import android.content.Context
import by.happygnom.data.network.RoutesGateway
import by.happygnom.data.network.ktorHttpClient
import by.happygnom.data.repository.RoutesRepositoryImpl
import by.happygnom.domain.data_interface.repository.RoutesRepository
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
        return RoutesRepositoryImpl(RoutesGateway(ktorHttpClient))
    }
}
