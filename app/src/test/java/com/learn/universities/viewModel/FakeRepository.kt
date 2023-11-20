package com.learn.universities.viewModel

import com.learn.universities.model.data.UniversityListData
import com.learn.universities.model.repository.MainRepository

class FakeRepository: MainRepository {
    override suspend fun downloadData(): List<UniversityListData> {
        return  mutableListOf(UniversityListData("Marywood University", "United States"))

    }

    override suspend fun downloadDataWithParameter(parameterName: String): List<UniversityListData> {
        TODO("Not yet implemented")
    }

}