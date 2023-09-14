package com.lolgame.guesgame.data.storage
import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedStorage @Inject constructor(@ApplicationContext context: Context) {
    companion object{
        const val CURRENT_COUNT_OF_LIVES = "CURRENT_COUNT_OF_LIVES"
        const val HIDDEN_NUMBER = "HIDDEN_NUMBER"
        const val INPUT_NUMBER = "INPUT_NUMBER"
    }
    private var shared = context.getSharedPreferences("MAIN_PREF", Context.MODE_PRIVATE)

    suspend fun getCurrentLivesCount(): Int {
        return shared.getInt(CURRENT_COUNT_OF_LIVES, 5)
    }
    suspend fun saveCurrentLivesCount(curentLivesValue: Int) {
        shared.edit().putInt(CURRENT_COUNT_OF_LIVES, curentLivesValue).apply()
    }
    suspend fun getHiddenNumber(): Int {
        return shared.getInt(HIDDEN_NUMBER, 0)
    }

    suspend fun clearCurrentLivesCount(): Int {
        Log.d("hueta1",  shared.getInt(CURRENT_COUNT_OF_LIVES, 5).toString())
        shared.edit().putInt(CURRENT_COUNT_OF_LIVES, 5).commit()
        Log.d("hueta2",  shared.getInt(CURRENT_COUNT_OF_LIVES, 5).toString())
        return shared.getInt(CURRENT_COUNT_OF_LIVES, 0)


    }

    suspend fun clearHiddenNumber(): Int {
        shared.edit().putInt(HIDDEN_NUMBER, 0).commit()
        return shared.getInt(HIDDEN_NUMBER, 0)

    }

    suspend fun saveHiddenNumber(hiddenNumber: Int) {
        shared.edit().putInt(HIDDEN_NUMBER, hiddenNumber).apply()
    }

    suspend fun getInputNumber(): Int {
        return shared.getInt(INPUT_NUMBER, 0)
    }
    suspend fun saveInputNumber(inputNumber: Int) {
        shared.edit().putInt(INPUT_NUMBER, inputNumber).apply()
    }


}