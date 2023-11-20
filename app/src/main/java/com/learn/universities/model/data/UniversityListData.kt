package com.learn.universities.model.data


import com.google.gson.annotations.SerializedName

data class UniversityListData(
   // @SerializedName("")
   // val InsideList: List<InsideList>,
    val name:String,
    val country:String
    //val web_pages:InsideList
)
data class InsideList(
    @SerializedName("")
    val web_pages:String)
/*
[
    {
        "state-province": null,
        "country": "United States",
        "domains": [
            "marywood.edu"
        ],
        "web_pages": [
            "http://www.marywood.edu"
        ],
        "alpha_two_code": "US",
        "name": "Marywood University"
    },
    {
        "state-province": null,
        "country": "United States",
        "domains": [
            "lindenwood.edu"
        ],
        "web_pages": [
            "http://www.lindenwood.edu/"
        ],
        "alpha_two_code": "US",
        "name": "Lindenwood University"
    },
    {
        "state-province": null,
        "country": "United States",
        "domains": [
            "sullivan.edu"
        ]
        */