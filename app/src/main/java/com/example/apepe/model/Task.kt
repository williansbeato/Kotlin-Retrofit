package com.example.apepe.model

import androidx.room.ColumnInfo

import com.google.gson.annotations.SerializedName


data class Task (
//    @ColumnInfo(name = "title")
    var title:String,
//    @ColumnInfo(name = "description")
    var description:String,
//    @ColumnInfo(name = "status")
    var status:Boolean
) {
    var id: Long? = null

}