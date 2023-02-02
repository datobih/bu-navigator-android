package com.example.pointtopointroutingapp.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pointtopointroutingapp.Constants
import com.example.pointtopointroutingapp.repository.MainRepository

class MainViewModel(
    savedStateHandle: SavedStateHandle,
    private val mainRepository: MainRepository
) : ViewModel() {

    companion object{
    val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {


        override fun <T : ViewModel> create(
            modelClass: Class<T>,
            extras: CreationExtras
        ): T {
            val savedStateHandle = extras.createSavedStateHandle()


            return MainViewModel(
                savedStateHandle, Constants.mainRepository!!
            ) as T
        }


    }

    }


    fun isUserFirstTime():Boolean{
        return mainRepository.isUserFirstTime()
    }

    fun onBoardingDone(){
        mainRepository.onBoardingDone()
    }

}