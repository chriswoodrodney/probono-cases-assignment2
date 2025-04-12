package com.chrisy.probonocases.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromCaseStatus(status: CaseStatus): String {
        return status.name
    }

    @TypeConverter
    fun toCaseStatus(value: String): CaseStatus {
        return CaseStatus.valueOf(value)
    }
}