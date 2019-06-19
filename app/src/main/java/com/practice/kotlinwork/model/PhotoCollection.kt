package com.practice.kotlinwork.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class PhotoCollection : Serializable{
    @SerializedName("photo")
    @Expose
    var allPhotos : List<PhotoFarm>? = null

    @SerializedName("total")
    @Expose
    var total : String? = null

    @SerializedName("pages")
    @Expose
    var pages : Int = 0


}