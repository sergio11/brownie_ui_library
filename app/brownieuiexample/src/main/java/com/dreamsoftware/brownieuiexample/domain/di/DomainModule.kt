package com.dreamsoftware.brownieuiexample.domain.di

import com.dreamsoftware.brownieuiexample.domain.usecase.impl.SignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    @ViewModelScoped
    fun provideSignInUseCase(): SignInUseCase = SignInUseCase()
}