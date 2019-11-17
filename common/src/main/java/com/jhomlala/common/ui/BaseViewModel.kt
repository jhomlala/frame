package com.jhomlala.common.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseViewModel : ViewModel(), KoinComponent {
    private val context: Context by inject()
    private val viewModelJob = SupervisorJob()
    protected val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancelChildren()
        EventBus.unregister(this.javaClass.simpleName)
    }

    fun getString(resourceId: Int): String{
        return context.getString(resourceId)
    }
}