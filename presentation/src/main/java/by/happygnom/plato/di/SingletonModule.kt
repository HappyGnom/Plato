package by.happygnom.plato.di

import android.content.Context
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
}
