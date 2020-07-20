package com.mac.fire.login.buildlogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.FirebaseApp
import com.mac.data.auth.FirebaseAuthRepositoryImpl
import com.mac.domain.DispatcherProvider
import com.mac.domain.servicelocator.UserServiceLocator
import com.mac.domain.interactor.AuthSource
import com.mac.domain.repository.IAuthRepository
import com.mac.fire.login.LoginActivity
import com.mac.fire.login.LoginLogic

class LoginInjector(application: Application) : AndroidViewModel(application) {
    init {
        FirebaseApp.initializeApp(application)
    }

    //For user management
    private val auth: IAuthRepository by lazy {
        //by using lazy, I don't load this resource until I need it
        FirebaseAuthRepositoryImpl()
    }


    fun buildLoginLogic(view: LoginActivity): LoginLogic = LoginLogic(
            DispatcherProvider,
            UserServiceLocator(auth),
            view,
            AuthSource()
    ).also { view.setObserver(it) }
}