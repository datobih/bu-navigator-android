package com.example.bunavigator.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.bunavigator.Constants
import com.example.bunavigator.main
import com.example.bunavigator.models.Destination
import com.example.bunavigator.repository.MainRepository

class MainViewModel(
    val savedStateHandle: SavedStateHandle,
    private val mainRepository: MainRepository
) : ViewModel() {
    val destinationSavedMutableLiveData:MutableLiveData<Boolean?> = MutableLiveData()
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

    fun getDestinations():List<Destination>{
        return mainRepository.getDestinations()
    }

    fun addDestination(destination: Destination){
        mainRepository.addDestination(destination)
    }
}