package com.chrisy.probonocases.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "judges")
data class Judge(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val court: String,
    val contactInfo: String
)
