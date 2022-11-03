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