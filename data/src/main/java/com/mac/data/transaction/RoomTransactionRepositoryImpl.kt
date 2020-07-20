package com.mac.data.transaction

import com.mac.data.note.registered.RegisteredTransactionDao
import com.mac.data.toNoteTransactionListFromRegistered
import com.mac.data.toRegisteredRoomTransaction
import com.mac.domain.domainmodel.NoteTransaction
import com.mac.domain.domainmodel.Result
import com.mac.domain.repository.ITransactionRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.runBlocking

class RoomTransactionRepositoryImpl(val transactionDao: RegisteredTransactionDao) : ITransactionRepository {
    override suspend fun getTransactions():
            Result<Exception, List<NoteTransaction>> = runBlocking(IO) {
        Result.build {
            transactionDao.getTransactions().toNoteTransactionListFromRegistered()
        }
    }

    override suspend fun deleteTransactions(): Result<Exception, Unit> = runBlocking(IO) {
        Result.build {
            transactionDao.deleteAll()
        }
    }

    override suspend fun updateTransactions(transaction: NoteTransaction):
            Result<Exception, Unit> = runBlocking(IO) {
        Result.build {
            transactionDao.insertOrUpdateTransaction(
                    transaction.toRegisteredRoomTransaction
            ).toUnit()
        }
    }

    private fun Long.toUnit(): Unit = Unit
}