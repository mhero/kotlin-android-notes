package com.mac.fire.notedetail.buildlogic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.FirebaseApp
import com.mac.data.auth.FirebaseAuthRepositoryImpl
import com.mac.data.note.anonymous.AnonymousNoteDao
import com.mac.data.note.anonymous.AnonymousNoteDatabase
import com.mac.data.note.anonymous.RoomLocalAnonymousRepositoryImpl
import com.mac.data.note.public.FirestoreRemoteNoteImpl
import com.mac.data.note.registered.*
import com.mac.data.transaction.RoomRegisteredTransactionDatabase
import com.mac.data.transaction.RoomTransactionRepositoryImpl
import com.mac.domain.DispatcherProvider
import com.mac.domain.servicelocator.NoteServiceLocator
import com.mac.domain.servicelocator.UserServiceLocator
import com.mac.domain.interactor.AnonymousNoteSource
import com.mac.domain.interactor.AuthSource
import com.mac.domain.interactor.PublicNoteSource
import com.mac.domain.interactor.RegisteredNoteSource
import com.mac.domain.repository.*
import com.mac.fire.notedetail.*

/**
 *
 */
class NoteDetailInjector(application: Application) : AndroidViewModel(application) {
    init {
        FirebaseApp.initializeApp(application)
    }

    private val anonNoteDao: AnonymousNoteDao by lazy {
        AnonymousNoteDatabase.getInstance(getApplication()).roomNoteDao()
    }

    private val regNoteDao: RegisteredNoteDao by lazy {
        RegisteredNoteDatabase.getInstance(getApplication()).roomNoteDao()
    }

    private val transactionDao: RegisteredTransactionDao by lazy {
        RoomRegisteredTransactionDatabase.getInstance(getApplication()).roomTransactionDao()
    }

    //For non-registered user persistence
    private val localAnon: ILocalNoteRepository by lazy {
        RoomLocalAnonymousRepositoryImpl(anonNoteDao)
    }

    //For registered user remote persistence (Source of Truth)
    private val remotePrivate: IRemoteNoteRepository by lazy {
        FirestorePrivateRemoteNoteImpl()
    }

    //For registered user remote persistence (Source of Truth)
    private val remotePublic: IPublicNoteRepository by lazy {
        FirestoreRemoteNoteImpl
    }

    //For registered user local persistience (cache)
    private val cacheReg: ILocalNoteRepository by lazy {
        RoomLocalCacheImpl(regNoteDao)
    }

    //For registered user remote persistence (Source of Truth)
    private val remoteRepo: IRemoteNoteRepository by lazy {
        RegisteredNoteRepositoryImpl(remotePrivate, cacheReg)
    }


    //For registered user local persistience (cache)
    private val transactionReg: ITransactionRepository by lazy {
        RoomTransactionRepositoryImpl(transactionDao)
    }

    //For user management
    private val auth: IAuthRepository by lazy {
        FirebaseAuthRepositoryImpl()
    }

    private lateinit var logic: NoteDetailLogic

    fun buildNoteDetailLogic(view: NoteDetailView,
                             id: String,
                             isPrivate: Boolean): NoteDetailLogic {
        logic = NoteDetailLogic(
                DispatcherProvider,
                NoteServiceLocator(localAnon, remoteRepo, transactionReg, remotePublic),
                UserServiceLocator(auth),
                ViewModelProviders.of(view)
                        .get(NoteDetailViewModel::class.java),
                view,
                AnonymousNoteSource(),
                RegisteredNoteSource(),
                PublicNoteSource(),
                AuthSource(),
                id,
                isPrivate
        )

        view.setObserver(logic)

        return logic
    }
}