package com.mac.domain.servicelocator

import com.mac.domain.repository.ILocalNoteRepository
import com.mac.domain.repository.IPublicNoteRepository
import com.mac.domain.repository.IRemoteNoteRepository
import com.mac.domain.repository.ITransactionRepository

class NoteServiceLocator(val localAnon: ILocalNoteRepository,
                         val remoteReg: IRemoteNoteRepository,
                         val transactionReg: ITransactionRepository,
                         val remotePublic: IPublicNoteRepository)