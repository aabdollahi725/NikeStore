package com.example.nikestore.data.product

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorites")
@Parcelize
data class Product(
    var discount: Int,
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var image: String,
    @ColumnInfo(name = "previous_price")
    @SerializedName("previous_price")
    var previousPrice: Int,
    var price: Int,
    var status: Int,
    var title: String,
    var isFavorite: Boolean
) : Parcelable

const val SORT_NEWEST=0
const val SORT_POPULAR=1
const val SORT_DESC=2
const val SORT_ASC=3