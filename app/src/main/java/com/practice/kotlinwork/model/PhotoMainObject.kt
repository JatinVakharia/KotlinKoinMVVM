package com.practice.kotlinwork.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class PhotoMainObject : Serializable{
    @SerializedName("photos")
    @Expose
    var photos : PhotoCollection? = null
}