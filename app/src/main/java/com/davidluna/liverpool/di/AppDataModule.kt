package com.davidluna.liverpool.di

import com.davidluna.liverpool.data.ProductSearchDataSource
import com.davidluna.liverpool.data.ProductSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {

    @Binds
    abstract fun bindProductSearchRepository(productSearchService: ProductSearchDataSource): ProductSearchRepository

}