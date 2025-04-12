package com.chrisy.probonocases.data

class LawyerJudgeRepository(
    private val lawyerDao: LawyerDao,
    private val judgeDao: JudgeDao
) {
    val allLawyers = lawyerDao.getAllLawyers()
    val allJudges = judgeDao.getAllJudges()

    // Repository methods for Lawyer
    suspend fun insertLawyer(lawyer: Lawyer) = lawyerDao.insert(lawyer)

    suspend fun updateLawyer(lawyer: Lawyer) = lawyerDao.update(lawyer)

    suspend fun deleteLawyer(lawyer: Lawyer) = lawyerDao.delete(lawyer)

    // Changed parameter type from Int to Long
    fun getLawyerById(id: Long) = lawyerDao.getLawyerById(id)

    // Repository methods for Judge
    suspend fun insertJudge(judge: Judge) = judgeDao.insert(judge)

    suspend fun updateJudge(judge: Judge) = judgeDao.update(judge)

    suspend fun deleteJudge(judge: Judge) = judgeDao.delete(judge)

    // Changed parameter type from Int to Long
    fun getJudgeById(id: Long) = judgeDao.getJudgeById(id)
}