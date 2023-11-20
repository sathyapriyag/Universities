package com.learn.universities.model.repository

import com.learn.universities.model.data.UniversityListData

interface MainRepository {
    open suspend fun downloadData(): List<UniversityListData>

    open suspend fun downloadDataWithParameter(parameterName: String): List<UniversityListData>


}