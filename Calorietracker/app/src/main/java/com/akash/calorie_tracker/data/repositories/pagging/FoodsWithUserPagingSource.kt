/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.akash.calorie_tracker.data.repositories.pagging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.akash.calorie_tracker.data.repositories.datasource.AdminDataSource
import com.akash.calorie_tracker.domain.models.FoodWithUserInfo
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import kotlin.math.max

// The initial key used for loading.
// This is the article id of the first article that will be loaded
private const val STARTING_KEY = 0


/**
 * A [PagingSource] that loads articles for paging. The [Int] is the paging key or query that is used to fetch more
 * data, and the [Article] specifies that the [PagingSource] fetches an [Article] [List].
 */



class FoodsWithUserPagingSource(
    val adminDataSource: AdminDataSource
) : PagingSource<Int, FoodWithUserInfo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FoodWithUserInfo> {
        val currentPage = params.key ?: 0
        return try {
            val nextPageNumber = params.key ?: 0
            val response = adminDataSource.getFoodListPaging( params.loadSize,nextPageNumber)
            Log.d("paging_test", "load: " + response.body() + " " + response.body().isNullOrEmpty())
            val endOfPaginationReached = response.body().isNullOrEmpty()

            if (!response.body().isNullOrEmpty()) {
                LoadResult.Page(
                    data = response.body()!!,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            Log.d("paging_test", "load: " + e)
            LoadResult.Error(e)
        }
    }

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    @OptIn(ExperimentalPagingApi::class)
    override fun getRefreshKey(state: PagingState<Int, FoodWithUserInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    /**
     * Makes sure the paging key is never less than [STARTING_KEY]
     */
    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}
