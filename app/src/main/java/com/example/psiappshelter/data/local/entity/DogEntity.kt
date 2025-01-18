package com.example.psiappshelter.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dogs")
data class DogEntity(
    val name: String = "",
    val age: Int = 0,
    val breed: String = "",
    @ColumnInfo(defaultValue = "0")
    val isFav: Boolean = false,
    val profileImageUrl: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}
