package com.example.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MovieBean(
    var response: Boolean? = false,
    var search: List<SearchBean>? = listOf(),
    var totalResults: Int? = 0
) : Parcelable

@Parcelize
data class SearchBean(
    var imdbID: String? = "",
    var poster: String? = "",
    var title: String? = "",
    var type: String? = "",
    var year: String? = ""
) : Parcelable