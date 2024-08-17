package com.davidluna.liverpool.framework.services

import com.davidluna.liverpool.framework.model.RemoteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductSearchService {

    @GET("appclienteservices/services/v3/plp")
    suspend fun fetchDefault(
        @Query("search-string", encoded = true) searchString: String,
        @Query("page-number") page: Int,
    ): RemoteResponse

    @GET("appclienteservices/services/v3/plp")
    suspend fun fetchSorted(
        @Query("search-string") searchString: String,
        @Query("page-number") page: Int,
        @Query("minSortPrice") minSortPrice: Int,
    ): RemoteResponse

}
