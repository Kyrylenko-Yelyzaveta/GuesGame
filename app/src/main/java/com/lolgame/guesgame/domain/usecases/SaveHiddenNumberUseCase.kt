package com.lolgame.guesgame.domain.usecases

import com.lolgame.guesgame.domain.irepo.ISharedRepo
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


@ViewModelScoped
class SaveHiddenNumberUseCase @Inject constructor(private  val saveScoreRepo: ISharedRepo) {
    suspend operator fun invoke(hiddenNumber:Int){
        return  saveScoreRepo.saveHiddenNumber(hiddenNumber)
    }
}