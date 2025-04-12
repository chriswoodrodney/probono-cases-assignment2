package com.chrisy.probonocases.data


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cases",
    foreignKeys = [
        ForeignKey(
            entity = Lawyer::class,
            parentColumns = ["id"],
            childColumns = ["lawyerId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = Judge::class,
            parentColumns = ["id"],
            childColumns = ["judgeId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Case(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val description: String,
    val clientName: String,
    val status: CaseStatus,
    val courtDate: Long? = null,
    val lawyerId: Long? = null,
    val legalReference: String? = null,
    val judgeId: Long? = null,
    val dateCreated: Long,
    val lastUpdated: Long
)

