package com.mac.domain.repository

import com.mac.domain.domainmodel.Result
import com.mac.domain.domainmodel.NoteTransaction

interface ITransactionRepository {
    suspend fun getTransactions():Result<Exception, List<NoteTransaction>>

    suspend fun deleteTransactions(): Result<Exception, Unit>

    suspend fun updateTransactions(transaction: NoteTransaction):Result<Exception, Unit>
}