package com.lolgame.guesgame.domain.irepo

interface ISharedRepo {
    suspend fun getCurrentLivesCount(): Int
    suspend fun saveCurrentLivesCount(currentLivesValue: Int)
    suspend fun getHiddenNumber(): Int
    suspend fun clearCurrentLivesCount(): Int
    suspend fun clearHiddenNumber(): Int
    suspend fun saveHiddenNumber(hiddenNumber: Int)
    suspend fun getInputNumber(): Int
    suspend fun saveInputNumber(inputNumber: Int)


}