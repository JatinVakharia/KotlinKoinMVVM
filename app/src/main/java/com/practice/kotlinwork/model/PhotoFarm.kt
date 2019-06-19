package com.practice.kotlinwork.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class PhotoFarm : Serializable {
    @SerializedName("farm")
    @Expose
    var farm : Int = 0

    @SerializedName("server")
    @Expose
    var server : String? = null

    @SerializedName("id")
    @Expose
    var id : String? = null

    @SerializedName("secret")
    @Expose
    var secret : String? = null

    @SerializedName("title")
    @Expose
    var title : String? = null

    var size : Char = 'm'
}