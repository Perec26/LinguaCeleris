package com.linguaceleris.network.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.linguaceleris.network.FirestoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideFirestoreService(): FirestoreService = FirestoreService(Firebase.firestore)
}
