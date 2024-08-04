package com.example.peer_pulse.di

import android.content.Context
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.example.peer_pulse.data.AuthRepositoryImpl
import com.example.peer_pulse.data.CollegeRepositoryImpl
import com.example.peer_pulse.data.CommunityRepositoryImpl
import com.example.peer_pulse.data.PagesRepositoryImpl
import com.example.peer_pulse.data.PostsRepositoryImpl
import com.example.peer_pulse.data.UserRepositoryImpl
import com.example.peer_pulse.data.room.PostDao
import com.example.peer_pulse.data.room.PostsDatabase
import com.example.peer_pulse.domain.repository.AuthRepository
import com.example.peer_pulse.domain.repository.CommunityRepository
import com.example.peer_pulse.domain.repository.PagesRepository
import com.example.peer_pulse.domain.repository.PostsRepository
import com.example.peer_pulse.domain.repository.UserRepository
import com.example.peer_pulse.domain.repository.collegeRepository
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
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore() : FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ) : AuthRepository {
        return AuthRepositoryImpl(auth, firestore)
    }

    @Provides
    @Singleton
    fun provideCollegeRepository(
        firestore: FirebaseFirestore
    ) : collegeRepository {
        return CollegeRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        firestore: FirebaseFirestore
    ) : UserRepository {
        return UserRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PostsDatabase {
        return PostsDatabase.getDatabase(context)
    }

    @Provides
    fun providePostDao(database: PostsDatabase): PostDao {
        return database.postDao()
    }


    @Provides
    @Singleton
    fun providePostsRepository(
        firestore: FirebaseFirestore,
        databse:PostsDatabase
    ) : PostsRepository {
        return PostsRepositoryImpl(firestore,databse)
    }

    @Provides
    @Singleton
    fun providePagesRepository(
        firestore: FirebaseFirestore
    ) : PagesRepository {
        return PagesRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideCommunityRepository(
        firestore: FirebaseFirestore
    ) : CommunityRepository {
        return CommunityRepositoryImpl(firestore)
    }


}