package com.lolgame.guesgame.data.repo

import com.lolgame.guesgame.data.storage.SharedStorage
import com.lolgame.guesgame.domain.irepo.ISharedRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedStorageRepo @Inject constructor(private  val saveScorePrefs:SharedStorage)  : ISharedRepo {
    override suspend fun getCurrentLivesCount(): Int {
        return saveScorePrefs.getCurrentLivesCount()
    }

    override suspend fun saveCurrentLivesCount(currentLivesValue: Int) {
        saveScorePrefs.saveCurrentLivesCount(currentLivesValue)
    }

    override suspend fun getHiddenNumber(): Int {
        return saveScorePrefs.getHiddenNumber()
    }

    override suspend fun clearCurrentLivesCount(): Int {
        return saveScorePrefs.clearCurrentLivesCount()

    }

    override suspend fun clearHiddenNumber(): Int {
        return saveScorePrefs.clearHiddenNumber()

    }

    override suspend fun saveHiddenNumber(hiddenNumber: Int) {
        saveScorePrefs.saveHiddenNumber(hiddenNumber)    }

    override suspend fun getInputNumber(): Int {
        return saveScorePrefs.getInputNumber()
    }

    override suspend fun saveInputNumber(inputNumber: Int) {
        saveScorePrefs.saveInputNumber(inputNumber)
    }
}