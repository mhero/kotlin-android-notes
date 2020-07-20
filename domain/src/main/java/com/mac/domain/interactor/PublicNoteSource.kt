package com.mac.domain.interactor

import com.mac.domain.servicelocator.NoteServiceLocator
import com.mac.domain.domainmodel.Note
import com.mac.domain.domainmodel.Result


class PublicNoteSource {
    suspend fun getNotes(locator: NoteServiceLocator): Result<Exception, List<Note>> = locator
            .remotePublic
            .getNotes()


    suspend fun getNoteById(id: String,
                            locator: NoteServiceLocator): Result<Exception, Note?> = locator
            .remotePublic
            .getNote(id)

    suspend fun updateNote(note: Note,
                           locator: NoteServiceLocator): Result<Exception, Unit> = locator
            .remotePublic
            .updateNote(note)

    suspend fun deleteNote(note: Note,
                           locator: NoteServiceLocator): Result<Exception, Unit> = locator
            .remotePublic
            .deleteNote(note)
}
