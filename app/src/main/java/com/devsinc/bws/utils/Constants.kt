package com.devsinc.bws.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

object Constants {

    const val BASE_URL = "https://bookingapi.bookwithstar.com/api/"

    fun openKeyboard(textInputLayout: TextInputEditText, context: Context): Boolean {
        val imm = ContextCompat.getSystemService(context, InputMethodManager::class.java)
        imm?.showSoftInput(textInputLayout, InputMethodManager.SHOW_IMPLICIT)
        return true
    }

}