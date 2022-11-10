package com.example.data.serverModels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class ServerMovie(
    @SerializedName("Response")
    var response: Boolean? = false,
    @SerializedName("Search")
    var search: List<ServerSearch>? = listOf(),
    @SerializedName("totalResults")
    var totalResults: Int? = 0
) : Parcelable

@Parcelize
data class ServerSearch(
    @SerializedName("imdbID")
    var imdbID: String? = "",
    @SerializedName("Poster")
    var poster: String? = "",
    @SerializedName("Title")
    var title: String? = "",
    @SerializedName("Type")
    var type: String? = "",
    @SerializedName("Year")
    var year: String? = ""
) : Parcelable