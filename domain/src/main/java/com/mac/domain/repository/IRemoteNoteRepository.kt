package com.mac.domain.repository

import com.mac.domain.domainmodel.Note
import com.mac.domain.domainmodel.NoteTransaction
import com.mac.domain.domainmodel.Result
import kotlinx.coroutines.channels.Channel

interface IRemoteNoteRepository {
    suspend fun synchronizeTransactions(transactions: List<NoteTransaction>): Result<Exception, Unit>

    suspend fun getNotes():Result<Exception, List<Note>>

    suspend fun getNote(id: String): Result<Exception, Note?>

    suspend fun deleteNote(note: Note): Result<Exception, Unit>

    suspend fun updateNote(note: Note):Result<Exception, Unit>
}