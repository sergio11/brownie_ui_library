package com.dreamsoftware.brownieuiexample.ui.di

import android.content.Context
import com.dreamsoftware.brownieuiexample.ui.feature.example.SignInScreenSimpleErrorMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UiModule {

    @Provides
    @ViewModelScoped
    fun provideSignInScreenErrorMapper(@ApplicationContext context: Context) = SignInScreenSimpleErrorMapper(context)
}