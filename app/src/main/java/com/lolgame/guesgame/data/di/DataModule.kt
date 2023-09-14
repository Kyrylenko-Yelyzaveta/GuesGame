package com.lolgame.guesgame.data.di

import com.lolgame.guesgame.data.repo.SharedStorageRepo
import com.lolgame.guesgame.domain.irepo.ISharedRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {
    @Binds
    fun bindSharedRepo(sharedRepo: SharedStorageRepo): ISharedRepo
}
