package com.lolgame.guesgame.ui.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lolgame.guesgame.data.network.ApiService
import com.lolgame.guesgame.data.network.RetrofitClient
import com.lolgame.guesgame.domain.usecases.ClearCurrentLevelCountUseCase
import com.lolgame.guesgame.domain.usecases.ClearHiddenNumberUseCase
import com.lolgame.guesgame.domain.usecases.GetCurrentLivesCountUseCase
import com.lolgame.guesgame.domain.usecases.GetHiddenNumberUseCase
import com.lolgame.guesgame.domain.usecases.GetInputNumberUseCase
import com.lolgame.guesgame.domain.usecases.SaveCurrentLivesCountUseCase
import com.lolgame.guesgame.domain.usecases.SaveHiddenNumberUseCase
import com.lolgame.guesgame.domain.usecases.SaveInputNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getInputNumberUseCase: GetInputNumberUseCase,
    val getHiddenNumberUseCase: GetHiddenNumberUseCase,
    private val getCurrentLivesCountUseCase: GetCurrentLivesCountUseCase,
    private val saveCurrentLivesCountUseCase: SaveCurrentLivesCountUseCase,
    private val saveInputNumberUseCase: SaveInputNumberUseCase,
    private val saveHiddenNumberUseCase: SaveHiddenNumberUseCase,
    private val clearCurrentLevelCountUseCase: ClearCurrentLevelCountUseCase,
    private val clearHiddenNumberUseCase: ClearHiddenNumberUseCase
) : ViewModel() {

    private val _apiResponse: MutableLiveData<Int?> = MutableLiveData()
    val apiResponse: LiveData<Int?> = _apiResponse

    private val _lives: MutableLiveData<Int?> = MutableLiveData()
    val lives: LiveData<Int?> = _lives


    init {
        viewModelScope.launch {
            _apiResponse.value = getHiddenNumberUseCase()
            _lives.postValue(getCurrentLivesCountUseCase())
        }
    }


    suspend fun getInteger() {
        val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
        val call = apiService.getInteger()

        call.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    if (response.body() != null) _apiResponse.value = response.body()
                    saveNumber()
                    Log.i("RetroWin", _apiResponse.value.toString())
                    // Do something with the response data
                } else {
                    Log.i("RetroWrong", "Error: ${response.code()}")
                    // Handle the error
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.i("RetroWin", "Throwable, connection problem: ${t.message}")
                // Handle the network failure
            }
        })
    }

    private fun saveNumber() {
        viewModelScope.launch {
            _apiResponse.value?.let { saveHiddenNumberUseCase(it) }
        }
        Log.d("Shared", getHiddenNumberUseCase.toString())

    }

    fun updateLivesCounter() {
        Log.d("DataLives", _lives.value.toString())
        val currentLives = _lives.value
        if (currentLives != null && currentLives > 0) {
            // Decrease the lives counter
            val newCounterOfLives = currentLives - 1
            _lives.postValue(newCounterOfLives)
            viewModelScope.launch{
               saveCurrentLivesCountUseCase(newCounterOfLives)
           }
        }
    }

     fun resetLives(){
         viewModelScope.launch{
             clearCurrentLevelCountUseCase()
         }
    }

    fun isTextValid(answerMeaning: String): Boolean {
        return when {
            answerMeaning.isBlank() -> false
            answerMeaning.contains(' ') -> false
            answerMeaning.toIntOrNull() == null -> false
            answerMeaning.toInt() in 1..100 -> true
            else -> {
                false
            }
        }
    }

    fun checkForWin(inputNumber: String): Boolean {
        viewModelScope.launch {
            saveInputNumberUseCase(inputNumber = inputNumber.toInt())
        }
        return getInputNumberUseCase.toString() == _apiResponse.value.toString()
    }

    suspend fun checkWhatsWrong(inputNumber: Int): String {
        saveInputNumberUseCase(inputNumber)
        return when {
            getInputNumberUseCase() > (getHiddenNumberUseCase()) -> "Your number was bigger that hidden number"
            getInputNumberUseCase() < (getHiddenNumberUseCase()) -> "Your number was less than hidden number"
            else -> "wow"
        }
    }

    fun clearData() {
        viewModelScope.launch {
            clearCurrentLevelCountUseCase
            clearHiddenNumberUseCase
            _lives.postValue(getCurrentLivesCountUseCase())
        }
    }

    fun rememberState() {
        viewModelScope.launch {
            _lives.value?.let { saveCurrentLivesCountUseCase(it) }
        }

    }


}