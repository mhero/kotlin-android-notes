package com.mac.domain.interactor

import com.mac.domain.servicelocator.NoteServiceLocator
import com.mac.domain.domainmodel.*
import com.mac.domain.repository.IRemoteNoteRepository
import com.mac.domain.repository.ITransactionRepository


class RegisteredNoteSource {
    suspend fun getNotes(locator: NoteServiceLocator): Result<Exception, List<Note>> {

        when (val transactionResult = locator.transactionReg.getTransactions()) {
            is Result.Value -> {
                //if items exist in transaction cache:
                if (transactionResult.value.isNotEmpty()) synchronizeTransactionCache(
                        transactionResult.value,
                        locator.remoteReg,
                        locator.transactionReg
                )
            }

            is Result.Error -> {
                //For now we'll just continue to ask remote for the latest data
            }
        }

        return locator.remoteReg.getNotes()
    }

    private suspend fun synchronizeTransactionCache(
            transactions: List<NoteTransaction>,
            remoteReg: IRemoteNoteRepository,
            transactionReg: ITransactionRepository) {

        //if synchronization was successful, delete items from the transaction cache
        when (remoteReg.synchronizeTransactions(transactions)) {
            is Result.Value -> transactionReg.deleteTransactions()
            is Result.Error -> {
                //"Again, not necessarily a fatal error"
            }
        }
    }

    suspend fun getNoteById(id: String,
                            locator: NoteServiceLocator):
            Result<Exception, Note?> = locator.remoteReg.getNote(id)

    suspend fun updateNote(note: Note,
                           locator: NoteServiceLocator): Result<Exception, Unit> {
        val remoteResult = locator.remoteReg.updateNote(note)

        if (remoteResult is Result.Value) return remoteResult
        else return locator.transactionReg.updateTransactions(
                note.toTransaction(TransactionType.UPDATE)
        )
    }

    suspend fun deleteNote(note: Note,
                           locator: NoteServiceLocator): Result<Exception, Unit> {
        val remoteResult = locator.remoteReg.deleteNote(note)

        return if (remoteResult is Result.Value) remoteResult
        else locator.transactionReg.updateTransactions(
                note.toTransaction(TransactionType.DELETE)
        )
    }
}
