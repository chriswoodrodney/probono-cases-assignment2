package com.chrisy.probonocases.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lawyers")
data class Lawyer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val specialization: String,
    val contactInfo: String
)
