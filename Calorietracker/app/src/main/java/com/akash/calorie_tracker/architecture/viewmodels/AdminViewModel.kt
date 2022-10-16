package com.akash.calorie_tracker.architecture.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.akash.calorie_tracker.ITEMS_PER_PAGE
import com.akash.calorie_tracker.domain.models.*
import com.akash.calorie_tracker.domain.repositories.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    val adminRepository: AdminRepository
):ViewModel() {

    val handler = CoroutineExceptionHandler {
            context, exception ->
        if(exception is SocketTimeoutException)
            _reportResponse.value = ResponseState<Reports>(Status.TIMEOUT,"Timeout","Please check your internet connection",null)
        println("Caught $exception")
    }

    fun logout(){
        adminRepository.logout()
    }

    /**
     * Stream of immutable states representative of the UI.
     */
    val foodWithUserItems: Flow<PagingData<FoodWithUserInfo>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { adminRepository.dataSource() }
    )
        .flow
        // cachedIn allows paging to remain active in the viewModel scope, so even if the UI
        // showing the paged data goes through lifecycle changes, pagination remains cached and
        // the UI does not have to start paging from the beginning when it resumes.
        .cachedIn(viewModelScope)


    private val _reportResponse: MutableLiveData<ResponseState<Reports>> = MutableLiveData()
    var reportResponse: LiveData<ResponseState<Reports>> = _reportResponse

    fun fetchReports():LiveData<ResponseState<Reports>>{
        viewModelScope.launch(handler) {
            _reportResponse.value = adminRepository.getReports()
        }
        return reportResponse
    }

}