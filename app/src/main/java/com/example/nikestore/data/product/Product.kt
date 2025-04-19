package com.example.nikestore.data.product

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorites")
@Parcelize
data class Product(
    var discount: Int,
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var image: String,
    var previous_price: Int,
    var price: Int,
    var status: Int,
    var title: String,
) : Parcelable{
    var isFavorite: Boolean=false
}

const val SORT_NEWEST=0
const val SORT_POPULAR=1
const val SORT_DESC=2
const val SORT_ASC=3