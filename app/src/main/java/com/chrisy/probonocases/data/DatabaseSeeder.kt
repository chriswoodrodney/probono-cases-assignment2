package com.chrisy.probonocases.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseSeeder(
    private val database: AppDatabase
) {
    suspend fun populateSampleData() = withContext(Dispatchers.IO) {
        try {
            val lawyerDao = database.lawyerDao()
            val judgeDao = database.judgeDao()
            val caseDao = database.caseDao()

            val sampleLawyers = listOf(
                Lawyer(id = 0, name = "John Smith", specialization = "Family Law", contactInfo = "john.smith@email.com"),
                Lawyer(id = 0, name = "Sarah Johnson", specialization = "Criminal Defense", contactInfo = "sarah.j@email.com"),
                Lawyer(id = 0, name = "Michael Williams", specialization = "Corporate Law", contactInfo = "m.williams@email.com")
            )

            val sampleJudges = listOf(
                Judge(id = 0, name = "Judge Robert Brown", court = "Supreme Court", contactInfo = "r.brown@courts.gov"),
                Judge(id = 0, name = "Judge Elizabeth Davis", court = "District Court", contactInfo = "e.davis@courts.gov")
            )

            sampleLawyers.forEach { lawyerDao.insert(it) }
            sampleJudges.forEach { judgeDao.insert(it) }

            val now = System.currentTimeMillis()

            val sampleCases = listOf(
                Case(
                    id = 0,
                    title = "Smith v. Johnson",
                    description = "Custody dispute involving two children",
                    clientName = "James Smith",
                    status = CaseStatus.PENDING,
                    courtDate = now + 7 * 24 * 60 * 60 * 1000,
                    lawyerId = 1,
                    judgeId = 1,
                    dateCreated = now,
                    lastUpdated = now
                ),
                Case(
                    id = 0,
                    title = "State v. Williams",
                    description = "Criminal defense case for petty theft",
                    clientName = "Thomas Williams",
                    status = CaseStatus.WON,
                    courtDate = now - 14 * 24 * 60 * 60 * 1000,
                    lawyerId = 2,
                    judgeId = 2,
                    dateCreated = now,
                    lastUpdated = now
                )
            )

            sampleCases.forEach { caseDao.insertCase(it) }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
