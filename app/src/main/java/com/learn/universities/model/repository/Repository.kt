package com.learn.universities.model.repository

import com.learn.universities.model.data.UniversityListData

class Repository(private val apiService: APIService) : MainRepository {

    @Override
    override suspend fun downloadData(): List<UniversityListData> {
            return apiService.getUniversities()
        }

    override suspend fun downloadDataWithParameter(parameterName: String): List<UniversityListData> {
        return apiService.getData(parameterName)
    }

}