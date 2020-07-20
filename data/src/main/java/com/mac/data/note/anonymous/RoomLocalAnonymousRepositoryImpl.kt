package com.mac.data.note.anonymous

import com.mac.data.toAnonymousRoomNote
import com.mac.data.toNote
import com.mac.data.toNoteListFromAnonymous
import com.mac.domain.domainmodel.Note
import com.mac.domain.domainmodel.Result
import com.mac.domain.error.SpaceNotesError
import com.mac.domain.repository.ILocalNoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomLocalAnonymousRepositoryImpl(private val noteDao: AnonymousNoteDao) : ILocalNoteRepository {
    //Not to be used
    override suspend fun deleteAll(): Result<Exception, Unit> = Result.build { throw SpaceNotesError.LocalIOException }

    //Not to be used
    override suspend fun updateAll(list: List<Note>): Result<Exception, Unit> = Result.build { throw SpaceNotesError.LocalIOException }

    override suspend fun updateNote(note: Note): Result<Exception, Unit> = withContext(Dispatchers.IO) {
        val updated = noteDao.insertOrUpdateNote(note.toAnonymousRoomNote)

        when {
            //TODO verify that if nothing is updated, the resulting value will be 0
            updated == 0L -> Result.build { throw SpaceNotesError.LocalIOException }
            else -> Result.build { Unit }
        }
    }

    override suspend fun getNote(id: String): Result<Exception, Note?> = withContext(Dispatchers.IO) { Result.build { noteDao.getNoteById(id).toNote } }


    override suspend fun getNotes(): Result<Exception, List<Note>> = withContext(Dispatchers.IO) { Result.build { noteDao.getNotes().toNoteListFromAnonymous() } }


    override suspend fun deleteNote(note: Note): Result<Exception, Unit> = withContext(Dispatchers.IO) {
        noteDao.deleteNote(note.toAnonymousRoomNote)
        Result.build { Unit }
    }
}