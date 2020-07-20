package com.mac.data.note.registered

import com.mac.data.toNote
import com.mac.data.toNoteListFromRegistered
import com.mac.data.toRegisteredRoomNote
import com.mac.domain.domainmodel.Note
import com.mac.domain.domainmodel.Result
import com.mac.domain.repository.ILocalNoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * This datasource is used by the RegisteredNoteRepository
 */
class RoomLocalCacheImpl(private val noteDao: RegisteredNoteDao) : ILocalNoteRepository {
    override suspend fun deleteAll(): Result<Exception, Unit> = withContext(Dispatchers.IO) {
        noteDao.deleteAll()

        Result.build { Unit }
    }

    override suspend fun updateAll(list: List<Note>): Result<Exception, Unit> = withContext(Dispatchers.IO) {
        list.forEach {
            noteDao.insertOrUpdateNote(it.toRegisteredRoomNote)
        }

        Result.build { Unit }
    }

    override suspend fun updateNote(note: Note): Result<Exception, Unit> = withContext(Dispatchers.IO) {
        noteDao.insertOrUpdateNote(note.toRegisteredRoomNote)

        Result.build { Unit }
    }

    override suspend fun getNote(id: String): Result<Exception, Note?> = withContext(Dispatchers.IO) {
        Result.build { noteDao.getNoteById(id).toNote }
    }


    override suspend fun getNotes(): Result<Exception, List<Note>> = withContext(Dispatchers.IO) {
        Result.build { noteDao.getNotes().toNoteListFromRegistered() }
    }


    override suspend fun deleteNote(note: Note): Result<Exception, Unit> = withContext(Dispatchers.IO) {
        noteDao.deleteNote(note.toRegisteredRoomNote)
        Result.build { Unit }
    }
}


