package com.learn.universities.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.universities.model.data.UniversityListData
import com.learn.universities.model.repository.APIService
import com.learn.universities.model.repository.MainRepository
import com.learn.universities.model.repository.Repository
import com.learn.universities.model.repository.RetrofitUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListViewModel(private val repository: MainRepository) : ViewModel() {



    private val _universities = MutableStateFlow(emptyList<UniversityListData>())
    val universities: StateFlow<List<UniversityListData>> = _universities
   // private val getApi: APIService = RetrofitUtil.getInstance().create(APIService::class.java)

    //example test
    private val _message = MutableStateFlow(0)
    val message: MutableStateFlow<Int> get() = _message

    fun getPosts() {
        try {
            viewModelScope.launch {
                _message.value = 4
                _universities.value =repository.downloadData()


            }
        }catch (e:Exception) { }

        }

    fun getPosts(parameterName: String) {
        try{
            viewModelScope.launch {

                Log.i("parameterName",parameterName)
                _universities.value = repository.downloadDataWithParameter(parameterName)

        }
    }catch (e:Exception) { }
    }
    }
