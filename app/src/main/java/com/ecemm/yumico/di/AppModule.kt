package com.ecemm.yumico.di
import android.content.Context
import androidx.room.Room
import com.ecemm.yumico.data.datasource.FavoriDataSource
import com.ecemm.yumico.data.datasource.RatingYemekDataSource
import com.ecemm.yumico.data.datasource.YemeklerDataSource
import com.ecemm.yumico.data.repo.FavoriRepository
import com.ecemm.yumico.data.repo.RatingYemekRepository
import com.ecemm.yumico.data.repo.YemeklerRepository
import com.ecemm.yumico.data.repository.AuthRepository
import com.ecemm.yumico.retrofit.ApiUtils
import com.ecemm.yumico.retrofit.YemeklerDao
import com.ecemm.yumico.room.Database
import com.ecemm.yumico.room.FavoriDao
import com.ecemm.yumico.room.RatingYemekDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return  FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth) : AuthRepository {
        return AuthRepository(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideYemeklerDao() : YemeklerDao{
        return ApiUtils.getYemeklerDao()
    }

    @Provides
    @Singleton
    fun provideYemeklerDataSource(yemeklerDao:YemeklerDao) : YemeklerDataSource{
        return YemeklerDataSource(yemeklerDao)
    }

    @Provides
    @Singleton
    fun provideYemeklerRepository(yemeklerDataSource:YemeklerDataSource) : YemeklerRepository {
        return YemeklerRepository(yemeklerDataSource)
    }

    @Provides
    @Singleton
    fun provideFavoriDao(@ApplicationContext context: Context) : FavoriDao {
        val db = Room.databaseBuilder(context, Database::class.java, "yumico.sqlite")
            .createFromAsset("yumico.sqlite")
            .fallbackToDestructiveMigration()// Eski schema uyuşmazsa veriyi silip yeniden oluşturur
            .build()
        return db.getFavoriDao()
    }



    @Provides
    @Singleton
    fun provideFavoriDataSource(favoriDao: FavoriDao) : FavoriDataSource {
        return FavoriDataSource(favoriDao)
    }

    @Provides
    @Singleton
    fun provideFavoriRepository(favoriDataSource:FavoriDataSource) : FavoriRepository {
        return FavoriRepository(favoriDataSource)
    }

    @Provides
    @Singleton
    fun provideRatingYemekDao(@ApplicationContext context: Context) : RatingYemekDao {
        val db = Room.databaseBuilder(context, Database::class.java, "yumico.sqlite")
            .createFromAsset("yumico.sqlite")
            .fallbackToDestructiveMigration()// Eski schema uyuşmazsa veriyi silip yeniden oluşturur
            .build()
        return db.getRatingYemekDao()
    }


    @Provides
    @Singleton
    fun provideRatingYemekDataSource(ratingYemekDao: RatingYemekDao) : RatingYemekDataSource {
        return RatingYemekDataSource(ratingYemekDao)
    }

    @Provides
    @Singleton
    fun provideRatingYemekRepository(ratingYemekDataSource:RatingYemekDataSource) : RatingYemekRepository {
        return RatingYemekRepository(ratingYemekDataSource)
    }
}