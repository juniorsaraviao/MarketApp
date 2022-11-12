package com.mitocode.marketapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

// Local entities

@Entity(tableName = "table_category")
data class DbCategory(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "uuid")
    @NotNull
    val uuid: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "cover")
    val cover: String
)


@Entity(tableName = "table_purchased_product")
data class DbPurchasedProduct(
    @PrimaryKey(autoGenerate = true)
    val purchase_id: Int = 0,
    @ColumnInfo(name = "uuid")
    @NotNull
    val uuid: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "amount")
    val amount: Int,
    @ColumnInfo(name = "total")
    val total: Double
)