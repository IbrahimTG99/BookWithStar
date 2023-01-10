package com.devsinc.bws.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsinc.bws.model.Customer
import com.devsinc.bws.model.LoginParams
import com.devsinc.bws.repository.AuthRepository
import com.devsinc.bws.repository.Resource
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<Customer>?>(null)
    val loginFlow = _loginFlow.asStateFlow()

    init {
        if (repository.customer != null) {
            _loginFlow.value = Resource.Success(repository.customer!!)
        }
    }

    fun login(username: String, password: String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        var token = ""
        // wait for token to be generated
        Firebase.messaging.token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("AuthViewModel", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }
            token = task.result.toString()
        }
        // wait for token to be generated
        while (token == "") {
            withContext(Dispatchers.IO) {
                Thread.sleep(100)
            }
        }
        val loginParams = LoginParams(username, password, token, android.os.Build.MODEL)
        _loginFlow.value = repository.login(loginParams)
    }

    fun getCustomer() = repository.customer
}