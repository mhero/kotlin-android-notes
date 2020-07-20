package com.mac.domain.interactor

import com.mac.domain.servicelocator.NoteServiceLocator
import com.mac.domain.domainmodel.Note
import com.mac.domain.domainmodel.Result

class AnonymousNoteSource {
    suspend fun getNotes(locator: NoteServiceLocator):
            Result<Exception, List<Note>> = locator.localAnon.getNotes()

    suspend fun getNoteById(id: String, locator: NoteServiceLocator):
            Result<Exception, Note?> = locator.localAnon.getNote(id)

    suspend fun updateNote(note: Note, locator: NoteServiceLocator):
            Result<Exception, Unit> = locator.localAnon.updateNote(note)


    suspend fun deleteNote(note: Note, locator: NoteServiceLocator):
            Result<Exception, Unit> = locator.localAnon.deleteNote(note)

}